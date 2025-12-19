import React, { useState, useRef, useEffect } from "react";
import {
  View,
  TextInput,
  TouchableOpacity,
  Keyboard,
  Animated,
  KeyboardAvoidingView,
  Platform
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { useSafeAreaInsets } from "react-native-safe-area-context";
import i18n from "@/config/i18n";

interface SearchBarProps {
  onSearch?: (query: string) => void;
}

export const SearchBar: React.FC<SearchBarProps> = ({ onSearch }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [isKeyboardVisible, setKeyboardVisible] = useState(false);
  const translateY = useRef(new Animated.Value(0)).current;
  const insets = useSafeAreaInsets();

  useEffect(() => {
    const keyboardWillShow = Keyboard.addListener(
        Platform.OS === "ios" ? "keyboardWillShow" : "keyboardDidShow",
        () => {
          setKeyboardVisible(true);
          Animated.spring(translateY, {
            toValue: -15,
            useNativeDriver: true,
            bounciness: 0,
          }).start();
        }
    );

    const keyboardWillHide = Keyboard.addListener(
        Platform.OS === "ios" ? "keyboardWillHide" : "keyboardDidHide",
        () => {
          setKeyboardVisible(false);
          Animated.spring(translateY, {
            toValue: 0,
            useNativeDriver: true,
            bounciness: 0,
          }).start();
        }
    );

    return () => {
      keyboardWillShow.remove();
      keyboardWillHide.remove();
    };
  }, []);

  const handleSearch = () => {
    if (onSearch) {
      onSearch(searchQuery); // Pass query to the parent component
    }
    Keyboard.dismiss();
  };

  return (
      <KeyboardAvoidingView
          behavior={Platform.OS === "ios" ? "padding" : "height"}
          style={{ position: "absolute", left: 0, right: 0, bottom: 0 }}
      >
        <Animated.View
            style={[
              {
                transform: [{ translateY }],
                paddingBottom: isKeyboardVisible ? 10 : insets.bottom + 8,
                paddingHorizontal: 24,
              },
            ]}
        >
          <View className="flex-row items-center bg-white rounded-full px-4 py-3 shadow-lg">
            <TextInput
                className="flex-1 text-gray-800 ml-2"
                placeholder={i18n.t("search.searchBarPlaceholder")}
                placeholderTextColor="#9ca3af"
                value={searchQuery}
                onChangeText={setSearchQuery}
                onSubmitEditing={handleSearch}
                returnKeyType="search"
                autoCapitalize="none"
                autoCorrect={false}
                accessibilityLabel="Search places"
                accessibilityHint="Enter location name to search"
            />
            <TouchableOpacity
                className="bg-red-500 p-2 rounded-full"
                onPress={handleSearch}
                accessibilityLabel="Search"
                accessibilityRole="button"
            >
              <Ionicons name="search" size={20} color="white" />
            </TouchableOpacity>
          </View>
        </Animated.View>
      </KeyboardAvoidingView>
  );
};