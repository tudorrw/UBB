import React, { useEffect, useRef, useState } from "react";
import {
  Animated,
  Dimensions,
  PanResponder,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  KeyboardAvoidingView,
  View,
  ScrollView,
  StyleSheet,
  TextInput,
  Keyboard,
  ActivityIndicator,
  Platform,
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { collection, addDoc, query, where, orderBy, getDocs, serverTimestamp } from 'firebase/firestore';
import { firestore, FIREBASE_AUTH } from '@/config/firebase';

const { height: SCREEN_HEIGHT } = Dimensions.get("window");
const INITIAL_HEIGHT = SCREEN_HEIGHT * 0.60; // 60% hidden, 40% visible
const MAX_HEIGHT = SCREEN_HEIGHT * 0.85; // 85% visible, 15% hidden

interface Props {
  visible: boolean;
  onClose(): void;
  title: string | null;
  text: string | null;
  backendUrl: string;
  language: string;
}

export const SlidingPanelNowPlaying = ({ visible, onClose, title, text, backendUrl, language }: Props) => {
  const [mounted, setMounted] = useState(visible);
  const [panelState, setPanelState] = useState<'initial' | 'max'>("initial");
  const translateY = useRef(new Animated.Value(SCREEN_HEIGHT)).current;
  const lastTranslateY = useRef(SCREEN_HEIGHT);
  const [messages, setMessages] = useState<{role: 'user' | 'system', text: string}[]>([]);
  const [input, setInput] = useState("");
  const scrollViewRef = useRef<KeyboardAwareScrollView>(null);
  const [keyboardVisible, setKeyboardVisible] = useState(false);
  const [keyboardHeight, setKeyboardHeight] = useState(0);
  const inputRef = useRef<TextInput>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [messagesLoaded, setMessagesLoaded] = useState(false);
  
  // Calculate visible panel height based on state
  const getPanelHeight = () => {
    return panelState === 'initial' ? INITIAL_HEIGHT : MAX_HEIGHT;
  };

  useEffect(() => {
    if (visible) {
      setMounted(true);
      setPanelState('initial');
      // Don't clear messages on mount - let loadMessages handle it
      Animated.timing(translateY, {
        toValue: SCREEN_HEIGHT - INITIAL_HEIGHT,
        duration: 300, // Reduced animation time for snappier feel
        useNativeDriver: true,
      }).start(() => {
        lastTranslateY.current = SCREEN_HEIGHT - INITIAL_HEIGHT;
      });
    } else if (mounted) {
      Animated.timing(translateY, {
        toValue: SCREEN_HEIGHT,
        duration: 300, // Reduced animation time
        useNativeDriver: true,
      }).start(() => {
        setTimeout(() => {
          setMounted(false);
          lastTranslateY.current = SCREEN_HEIGHT;
          onClose();
        }, 0);
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [visible]);

  // Scroll to bottom on message change with debounce
  useEffect(() => {
    const scrollTimer = setTimeout(() => {
      if (scrollViewRef.current && messages.length > 0) {
        scrollViewRef.current.scrollToEnd({ animated: true });
      }
    }, 100);
    
    return () => clearTimeout(scrollTimer);
  }, [messages, keyboardVisible]);

  // Keyboard listeners to handle scrolling up when keyboard appears
  useEffect(() => {
    // For iOS - triggered before keyboard shows
    const keyboardWillShowListener = Keyboard.addListener('keyboardWillShow', (event) => {
        setKeyboardVisible(true);
        setKeyboardHeight(event.endCoordinates.height);

        // Ensure we're at max height when keyboard appears
        if (panelState !== 'max') {
          setPanelState('max');
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - MAX_HEIGHT,
            duration: 250, // Adjusted timing for iOS animation sync
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
          });
        }
        
        // Ensure messages are scrolled to bottom with iOS keyboard animation
        setTimeout(() => {
          if (scrollViewRef.current) {
            scrollViewRef.current.scrollToEnd({ animated: true });
          }
        }, 100);
      }
    );
    
    // For iOS - triggered before keyboard hides
    const keyboardWillHideListener = Keyboard.addListener('keyboardWillHide', () => {
      // This ensures the input stays in place during iOS keyboard animation
      setTimeout(() => {
        setKeyboardVisible(false);
        setKeyboardHeight(0);
      }, 50); // Small delay to match keyboard animation
    });

    // For Android - triggered when keyboard is fully visible
    const keyboardDidShowListener = Keyboard.addListener(
      'keyboardDidShow',
      (event) => {
        if (Platform.OS === 'android') {
          setKeyboardVisible(true);
          setKeyboardHeight(event.endCoordinates.height);
          setPanelState('max');
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - MAX_HEIGHT,
            duration: 100,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
            // Ensure messages are scrolled to bottom after keyboard fully shows
            if (scrollViewRef.current) {
              setTimeout(() => {
                scrollViewRef.current?.scrollToEnd({ animated: true });
              }, 50);
            }
          });
        }
      }
    );

    // For Android - triggered when keyboard is fully hidden
    const keyboardDidHideListener = Keyboard.addListener('keyboardDidHide', () => {
      if (Platform.OS === 'android') {
        setKeyboardVisible(false);
        setKeyboardHeight(0);
      }
    });

    return () => {
      keyboardWillShowListener.remove();
      keyboardWillHideListener.remove();
      keyboardDidShowListener.remove();
      keyboardDidHideListener.remove();
    };
  }, [panelState]);

  // Load messages when panel opens
  useEffect(() => {
    if (visible && title) {
      loadMessages();
    }
  }, [visible, title]);

  const loadMessages = async () => {
    const user = FIREBASE_AUTH.currentUser;
    if (!user || !title) return;

    try {
      setMessagesLoaded(false); // Indicate loading state
      
      const messagesRef = collection(firestore, 'chat_messages');
      const q = query(
        messagesRef,
        where('placeName', '==', title),
        where('userId', '==', user.uid),
        orderBy('timestamp', 'asc')
      );

      const querySnapshot = await getDocs(q);
      const loadedMessages = querySnapshot.docs.map(doc => ({
        role: doc.data().role as 'user' | 'system',
        text: doc.data().text
      }));

      // Add initial system message if it exists and no messages are loaded
      if (text && loadedMessages.length === 0) {
        loadedMessages.push({ role: 'system', text });
        await saveMessage('system', text); // Save the first generated text
      }

      setMessages(loadedMessages);
      setMessagesLoaded(true);
      
      // Scroll to bottom after messages load with a delay for rendering
      setTimeout(() => {
        if (scrollViewRef.current) {
          scrollViewRef.current.scrollToEnd({ animated: false });
        }
      }, 100);
    } catch (error) {
      console.error('Error loading messages:', error);
      setMessagesLoaded(true);
    }
  };

  // Helper to save a message to Firestore
  const saveMessage = async (role: 'user' | 'system', textToSave: string) => {
    const user = FIREBASE_AUTH.currentUser;
    if (!user || !title) return;
    const messagesRef = collection(firestore, 'chat_messages');
    await addDoc(messagesRef, {
      placeName: title,
      userId: user.uid,
      role,
      text: textToSave,
      timestamp: serverTimestamp()
    });
  };

  const handleSend = async () => {
    if (!input.trim() || isLoading) return;

    const user = FIREBASE_AUTH.currentUser;
    if (!user || !title) return;

    // Keep a reference to the input value before clearing
    const userMessage = input.trim();
    setIsLoading(true);
    setInput(""); // Clear input before dismissing keyboard
    
    // Use a small delay for iOS to finish text input operations before keyboard dismissal
    setTimeout(() => {
      Keyboard.dismiss(); // Dismiss keyboard on send with slight delay
    }, 50);

    // Add user message to local state
    setMessages(prev => [...prev, { role: 'user', text: userMessage }]);
    
    // Ensure scroll to bottom happens after message is added
    setTimeout(() => {
      if (scrollViewRef.current) {
        scrollViewRef.current.scrollToEnd({ animated: true });
      }
    }, 100);

    try {
      // Save user message to Firestore
      await saveMessage('user', userMessage);

      // Prepare chat history for the backend
      const chatHistory = messages.map(msg => ({
        role: msg.role,
        text: msg.text
      }));

      // Call the backend endpoint
      const response = await fetch(backendUrl + '/api/llm_chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          chat_history: chatHistory,
          message: userMessage,
          language: language
        }),
      });

      if (!response.ok) throw new Error('Failed to get response from LLM');
      const data = await response.json();
      
      // Add system response to local state
      setMessages(prev => [...prev, { role: 'system', text: data.answer }]);

      // Scroll again after system response
      setTimeout(() => {
        if (scrollViewRef.current) {
          scrollViewRef.current.scrollToEnd({ animated: true });
        }
      }, 100);

      // Save system response to Firestore
      await saveMessage('system', data.answer);
    } catch (error) {
      console.error('Error in chat:', error);
    } finally {
      setIsLoading(false);
    }
  };

  // Keyboard listeners to handle scrolling up when keyboard appears
  useEffect(() => {
    // For iOS - triggered before keyboard shows
    const keyboardWillShowListener = Keyboard.addListener('keyboardWillShow', (event) => {
        setKeyboardVisible(true);
        setKeyboardHeight(event.endCoordinates.height);

        // Ensure we're at max height when keyboard appears
        if (panelState !== 'max') {
          setPanelState('max');
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - MAX_HEIGHT,
            duration: 250,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
          });
        }
        
        // Ensure messages are scrolled to bottom with iOS keyboard animation
        setTimeout(() => {
          if (scrollViewRef.current) {
            scrollViewRef.current.scrollToEnd();
          }
        }, 100);
      }
    );
    
    // For iOS - triggered before keyboard hides
    const keyboardWillHideListener = Keyboard.addListener('keyboardWillHide', () => {
      setTimeout(() => {
        setKeyboardVisible(false);
        setKeyboardHeight(0);
      }, 50);
    });

    // For Android - triggered when keyboard is fully visible
    const keyboardDidShowListener = Keyboard.addListener(
      'keyboardDidShow',
      (event) => {
        if (Platform.OS === 'android') {
          setKeyboardVisible(true);
          setKeyboardHeight(event.endCoordinates.height);
          setPanelState('max');
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - MAX_HEIGHT,
            duration: 100,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
            if (scrollViewRef.current) {
              setTimeout(() => {
                scrollViewRef.current?.scrollToEnd();
              }, 50);
            }
          });
        }
      }
    );

    // For Android - triggered when keyboard is fully hidden
    const keyboardDidHideListener = Keyboard.addListener('keyboardDidHide', () => {
      if (Platform.OS === 'android') {
        setKeyboardVisible(false);
        setKeyboardHeight(0);
      }
    });

    return () => {
      keyboardWillShowListener.remove();
      keyboardWillHideListener.remove();
      keyboardDidShowListener.remove();
      keyboardDidHideListener.remove();
    };
  }, [panelState]);

  // Handle input field focus to expand panel if needed
  const handleInputFocus = () => {
    if (panelState !== 'max') {
      setPanelState('max');
      Animated.timing(translateY, {
        toValue: SCREEN_HEIGHT - MAX_HEIGHT,
        duration: 200, // Faster animation
        useNativeDriver: true,
      }).start(() => {
        lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
      });
    }
  };

  // Expand panel to max height without focusing the input
  const expandPanel = () => {
    setPanelState('max');
    Animated.timing(translateY, {
      toValue: SCREEN_HEIGHT - MAX_HEIGHT,
      duration: 300,
      useNativeDriver: true,
    }).start(() => {
      lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
    });
  };

  // PanResponder for drag
  const panResponder = useRef(
    PanResponder.create({
      onMoveShouldSetPanResponder: (_, gesture) => {
        // More responsive drag detection
        return Math.abs(gesture.dy) > 4;
      },
      onPanResponderMove: (_, gesture) => {
        let newY = lastTranslateY.current + gesture.dy;
        // Clamp so the panel never goes above the max (15% from top)
        if (newY < SCREEN_HEIGHT - MAX_HEIGHT) newY = SCREEN_HEIGHT - MAX_HEIGHT;
        if (newY > SCREEN_HEIGHT) newY = SCREEN_HEIGHT;
        translateY.setValue(newY);
      },
      onPanResponderRelease: (_, gesture) => {
        // Dismiss keyboard when dragging
        Keyboard.dismiss();
        
        let newY = lastTranslateY.current + gesture.dy;
        // Snap logic: if dragged up past halfway between initial and max, snap to max
        const halfway = (SCREEN_HEIGHT - INITIAL_HEIGHT + SCREEN_HEIGHT - MAX_HEIGHT) / 2;
        if (gesture.dy > 80 || newY > SCREEN_HEIGHT - INITIAL_HEIGHT / 2) {
          // Close if dragged down enough
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT,
            duration: 300, // Faster animation
            useNativeDriver: true,
          }).start(() => {
            setTimeout(() => {
              setMounted(false);
              lastTranslateY.current = SCREEN_HEIGHT;
              onClose();
            }, 0);
          });
        } else if (panelState === 'initial' && newY <= halfway) {
          // Snap to max height (most visible)
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - MAX_HEIGHT,
            duration: 300,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - MAX_HEIGHT;
            setPanelState('max');
          });
        } else if (panelState === 'max' && newY > halfway) {
          // Snap back to initial height
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT - INITIAL_HEIGHT,
            duration: 300,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = SCREEN_HEIGHT - INITIAL_HEIGHT;
            setPanelState('initial');
          });
        } else {
          // Snap to current state
          const toValue = panelState === 'max' ? (SCREEN_HEIGHT - MAX_HEIGHT) : (SCREEN_HEIGHT - INITIAL_HEIGHT);
          Animated.timing(translateY, {
            toValue,
            duration: 300,
            useNativeDriver: true,
          }).start(() => {
            lastTranslateY.current = toValue;
          });
        }
      },
    })
  ).current;

  if (!mounted) return null;

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      style={StyleSheet.absoluteFill}
      pointerEvents="box-none"
      keyboardVerticalOffset={Platform.OS === "ios" ? 0 : 20}
    >
      {/* Overlay */}
      <TouchableWithoutFeedback
        onPress={() => {
          Keyboard.dismiss();
          Animated.timing(translateY, {
            toValue: SCREEN_HEIGHT,
            duration: 300,
            useNativeDriver: true,
          }).start(() => {
            setTimeout(() => {
              setMounted(false);
              lastTranslateY.current = SCREEN_HEIGHT;
              onClose();
            }, 0);
          });
        }}
      >
        <View style={styles.overlay} />
      </TouchableWithoutFeedback>
      
      {/* Sliding Panel */}
      <Animated.View
        style={[
          styles.panel,
          {
            transform: [{ translateY }],
            height: SCREEN_HEIGHT, // Full height for sliding
          },
        ]}
      >
        {/* Fixed-height container for content */}
        <View style={{ 
          height: getPanelHeight(),
          display: 'flex',
          flexDirection: 'column'
        }}>
          {/* Handle area for drag gesture */}
          <View 
            {...panResponder.panHandlers}
          >
            {/* Grip handle */}
            <TouchableOpacity 
              onPress={() => {
                Keyboard.dismiss();
                Animated.timing(translateY, {
                  toValue: SCREEN_HEIGHT,
                  duration: 300,
                  useNativeDriver: true,
                }).start(() => {
                  setTimeout(() => {
                    setMounted(false);
                    lastTranslateY.current = SCREEN_HEIGHT;
                    onClose();
                  }, 0);
                });
              }} 
              hitSlop={10}
              style={{ marginHorizontal: 24 }}
            >
              <View style={styles.gripContainer}>
                <View style={styles.grip} />
                <Ionicons name="chevron-down" size={32} color="#222" />
              </View>
            </TouchableOpacity>
            
            {/* Title */}
            <Text style={styles.title}>{title}</Text>
          </View>
          
          {/* Chat content */}
          <View style={{ 
            flex: 1, 
            display: 'flex', 
            flexDirection: 'column',
            paddingHorizontal: 24,
            justifyContent: 'space-between'
          }}>
            {/* Loading indicator for initial load */}
            {!messagesLoaded && (
              <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#d32f2f" />
              </View>
            )}
            
            {/* Messages scroll area - always visible */}
            <KeyboardAwareScrollView
              ref={scrollViewRef}
              showsVerticalScrollIndicator={false}
              scrollEnabled={true}
              style={{ flex: 1 }}
              contentContainerStyle={{ 
                paddingVertical: 8,
                paddingBottom: panelState === 'max' ? 25 : 8 // More padding for iOS
              }}
              keyboardShouldPersistTaps="handled"
              enableOnAndroid={true}
              extraScrollHeight={120} // Extra scroll height for iOS
              extraHeight={Platform.OS === 'ios' ? 100 : 80} // Extra height for iOS
              enableAutomaticScroll={true}
              keyboardOpeningTime={200} // Match iOS keyboard animation timing
              viewIsInsideTabBar={true} // Important for iOS
            >
              {messages.map((msg, idx) => (
                <TouchableWithoutFeedback key={idx}>
                  <View
                    style={[
                      styles.bubble,
                      msg.role === 'user' ? styles.userBubble : styles.systemBubble,
                      msg.role === 'user' ? { alignSelf: 'flex-end' } : { alignSelf: 'flex-start' },
                    ]}
                  >
                    <Text style={msg.role === 'user' ? styles.userText : styles.systemText}>{msg.text}</Text>
                  </View>
                </TouchableWithoutFeedback>
              ))}
            </KeyboardAwareScrollView>
            
            {/* Input area - ONLY VISIBLE AT MAX HEIGHT */}
            {panelState === 'max' && (
              <View style={[
                styles.inputBar,
                keyboardVisible && styles.inputBarWithKeyboard, {bottom: keyboardHeight + 8}
              ]}>
                <TextInput
                  ref={inputRef}
                  style={styles.input}
                  value={input}
                  onChangeText={setInput}
                  placeholder="Type a message..."
                  placeholderTextColor="#aaa"
                  onSubmitEditing={handleSend}
                  returnKeyType="send"
                  onFocus={handleInputFocus}
                  autoFocus={false}
                  editable={!isLoading}
                  enablesReturnKeyAutomatically={true} // iOS specific - enables send button only when text exists
                />
                <TouchableOpacity 
                  onPress={handleSend} 
                  style={[styles.sendButton, isLoading && styles.sendButtonDisabled]}
                  activeOpacity={0.7}
                  disabled={isLoading || !input.trim()}
                >
                  {isLoading ? (
                    <ActivityIndicator color="#fff" size="small" />
                  ) : (
                    <Ionicons name="send" size={22} color="#fff" />
                  )}
                </TouchableOpacity>
              </View>
            )}
            
            {/* Hidden "Click to chat" button when at initial height */}
            {panelState === 'initial' && (
              <TouchableOpacity 
                style={styles.chatButton} 
                onPress={expandPanel} // Only expand the panel without focusing the input
                activeOpacity={0.7}
              >
                <Text style={styles.chatButtonText}>Click to chat</Text>
              </TouchableOpacity>
            )}
          </View>
        </View>
      </Animated.View>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  overlay: {
    ...StyleSheet.absoluteFillObject,
    backgroundColor: "rgba(0,0,0,0.25)",
  },
  panel: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: "#fff",
    borderTopLeftRadius: 32,
    borderTopRightRadius: 32,
    overflow: "hidden",
    shadowColor: "#000",
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.15,
    shadowRadius: 8,
    elevation: 8,
  },
  dragHandleArea: {
    paddingTop: 8,
    paddingBottom: 8,
  },
  gripContainer: {
    alignItems: "center",
    paddingTop: 8,
  },
  grip: {
    width: 40,
    height: 5,
    borderRadius: 2.5,
    backgroundColor: "#ccc",
    marginBottom: 6,
  },
  title: {
    fontSize: 22,
    fontWeight: "bold",
    textAlign: "center",
    color: "#d32f2f",
    marginTop: 8,
    marginBottom: 8,
    paddingHorizontal: 24,
  },
  bubble: {
    maxWidth: '80%',
    borderRadius: 18,
    paddingVertical: 10,
    paddingHorizontal: 16,
    marginVertical: 4,
  },
  userBubble: {
    backgroundColor: '#d32f2f',
    marginRight: 8,
  },
  systemBubble: {
    backgroundColor: '#eee',
    marginLeft: 8,
  },
  userText: {
    color: '#fff',
    fontSize: 16,
  },
  systemText: {
    color: '#222',
    fontSize: 16,
  },
  inputBar: {
    position: 'absolute',
    left: 0,
    right: 0,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 12,
    paddingVertical: 10, // More padding for iOS
    borderTopWidth: 1,
    borderColor: '#eee',
    backgroundColor: '#fff',
    zIndex: 10, // Ensure it's above other elements
  },
  inputBarWithKeyboard: {
    // iOS-specific styling when keyboard is visible
    // This ensures input stays in position during keyboard transitions
    // borderTopWidth: Platform.OS === 'ios' ? 0.5 : 1,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 5
  },
  input: {
    flex: 1,
    height: 40,
    borderRadius: 20,
    backgroundColor: '#f2f2f2',
    paddingHorizontal: 16,
    fontSize: 16,
    marginRight: 8,
    color: '#222',
  },
  sendButton: {
    backgroundColor: '#d32f2f',
    borderRadius: 20,
    padding: 10,
    justifyContent: 'center',
    alignItems: 'center',
  },
  chatButton: {
    backgroundColor: '#d32f2f',
    borderRadius: 20,
    paddingVertical: 12,
    paddingHorizontal: 20,
    alignSelf: 'center',
    marginBottom: 16,
  },
  chatButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
  sendButtonDisabled: {
    opacity: 0.5,
  },
  loadingContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 5,
  },
});

