import React, { useState, useEffect, useRef } from 'react';
import { StyleSheet, View, Text, ActivityIndicator, TouchableOpacity, Animated, Dimensions } from 'react-native';
import MapView, { Marker, AnimatedRegion, Callout } from 'react-native-maps';
import * as Location from 'expo-location';
import { SafeAreaView } from "react-native-safe-area-context";
import { Ionicons } from '@expo/vector-icons';
import { BlurView } from 'expo-blur';
import { TimelineSegment } from './utils/audio/declarations';

const GOOGLE_MAPS_API_KEY = process.env.EXPO_PUBLIC_GOOGLE_MAPS_API_KEY;
const LANGUAGE = process.env.EXPO_PUBLIC_LANGUAGE;

const GoogleMap = () => {
  const [location, setLocation] = useState<Location.LocationObject | null>(null);
  const [animatedMarkers, setAnimatedMarkers] = useState<any[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [transcription, setTranscription] = useState<string>('');
  const [currentRows, setCurrentRows] = useState<string[]>([]);
  const [isTopComponentVisible, setIsTopComponentVisible] = useState(false);
  const [timeline, setTimeline] = useState<TimelineSegment[]>([]);
  const slideAnimation = useRef(new Animated.Value(0)).current;
  const currentIndex = useRef(0);
  const textRows = useRef<string[]>([]);

  const longText = `This is a long description of a place. It contains detailed information about the history, significance, and interesting facts about the location. The text is long enough to require scrolling or sliding to read completely. It contains paragraphs, sections, and more for a better understanding of the topic.`;

  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        return;
      }

      let currentLocation = await Location.getCurrentPositionAsync({});
      setLocation(currentLocation);
    })();
  }, []);

  const fetchNearbyPlaces = async () => {
    if (!location) return;

    setIsLoading(true);
    const { latitude, longitude } = location.coords;
    const url = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=500&type=tourist_attraction&language=${LANGUAGE}&key=${GOOGLE_MAPS_API_KEY}`;

    try {
      const response = await fetch(url);
      const data = await response.json();
      if (data.results && data.results.length > 0) {
        const newMarkers = data.results.map((place) => ({
          coordinate: new AnimatedRegion({
            latitude: place.geometry.location.lat,
            longitude: place.geometry.location.lng,
            latitudeDelta: 0.005,
            longitudeDelta: 0.005,
          }),
          title: place.name,
          description: place.vicinity,
        }));
        setAnimatedMarkers(newMarkers);
      }
    } catch (error) {
      console.error('Failed to fetch nearby places:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchTTSTimeline = async (text: string) => {
    try {
      const response = await fetch('/api/deepgram_tts', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text })
      });
      
      if (!response.ok) throw new Error('Failed to fetch TTS timeline');
      const timelineData = await response.json();
      setTimeline(timelineData);
    } catch (error) {
      console.error('Error fetching TTS timeline:', error);
    }
  };

  const animateText = (newRows: string[]) => {
    // Slide the old text up and then bring the new text from below
    Animated.sequence([
      Animated.timing(slideAnimation, {
        toValue: -100, // Move old text up
        duration: 500,
        useNativeDriver: true,
      }),
    ]).start(() => {
      // Update text and reset position for new text below
      setCurrentRows(newRows);
      slideAnimation.setValue(100); // new text starts below

      Animated.timing(slideAnimation, {
        toValue: 0, // Slide new text up into view
        duration: 500,
        useNativeDriver: true,
      }).start();
    });
  };

  const startTextSimulation = () => {
    // Calculate the number of characters that fit the width of the screen
    const screenWidth = Dimensions.get('window').width;
    const charWidth = 12;
    const charsPerRow = Math.floor(screenWidth / charWidth);

    // Split text into chunks that fit the calculated width
    const words = longText.split(' ');
    textRows.current = [];
    let currentRow = '';

    words.forEach(word => {
      if ((currentRow + word).length <= charsPerRow) {
        currentRow += `${word} `;
      } else {
        textRows.current.push(currentRow.trim());
        currentRow = `${word} `;
      }
    });

    if (currentRow.length > 0) {
      textRows.current.push(currentRow.trim());
    }

    currentIndex.current = 0;
    // Show first two rows immediately
    setCurrentRows(textRows.current.slice(0, 2));

    const showNextRows = () => {
      currentIndex.current += 2;
      if (currentIndex.current >= textRows.current.length) {
        setTimeout(() => {
          setIsTopComponentVisible(false);
        }, 3000);
        return;
      }

      // Get next two rows
      const nextRows = textRows.current.slice(currentIndex.current, currentIndex.current + 2);
      animateText(nextRows);
      setTimeout(showNextRows, 3000);
    };

    setTimeout(showNextRows, 3000);
  };

  const handleMoreInfoPress = async (title: string) => {
    setTranscription(longText);
    await fetchTTSTimeline(longText);
    
    // Reset any existing animation state
    slideAnimation.setValue(0);
    currentIndex.current = 0;
    setCurrentRows([]);
    setIsTopComponentVisible(true);
    setTimeout(() => {
      startTextSimulation();
    }, 100);
  };

  if (location === null) {
    return (
      <View style={styles.loader}>
        <ActivityIndicator size="large" color="#0000ff" />
        <Text>Loading...</Text>
      </View>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <MapView
        style={StyleSheet.absoluteFillObject}
        showsUserLocation={true}
        initialRegion={{
          latitude: location.coords.latitude,
          longitude: location.coords.longitude,
          latitudeDelta: 0.005,
          longitudeDelta: 0.005,
        }}
      >
        {animatedMarkers.map((marker, index) => (
          <Marker.Animated
            key={index}
            coordinate={marker.coordinate}
            title={marker.title}
            description={marker.description}
          >
            <Callout onPress={() => handleMoreInfoPress(marker.title)}>
              <View style={{ width: 150 }}>
                <Text>{marker.title}</Text>
                <Text>{marker.description}</Text>
                <TouchableOpacity style={styles.calloutButton}>
                  <Text style={styles.calloutButtonText}>More Info</Text>
                </TouchableOpacity>
              </View>
            </Callout>
          </Marker.Animated>
        ))}
      </MapView>

      {isTopComponentVisible && (
      // {true && (
        <View style={styles.textOverlayContainer}>
          <BlurView intensity={40} style={styles.textOverlayBlur}>
            <Animated.View
              style={[
                styles.animatedTextContainer,
                {
                  transform: [{ translateY: slideAnimation }],
                },
              ]}
            >
              {currentRows.map((row, index) => (
                <Text key={index} style={styles.overlayTitle}>
                  {row}
                </Text>
              ))}
            </Animated.View>
          </BlurView>
        </View>
      )}

      <View style={styles.blurContainer}>
        <TouchableOpacity
          style={styles.button}
          onPress={fetchNearbyPlaces}
          disabled={isLoading}
        >
          {isLoading ? (
            <ActivityIndicator size="small" color="#ffffff" />
          ) : (
            <Ionicons name="location-sharp" size={24} color="white" />
          )}
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f0f0f0',
  },
  calloutButton: {
    backgroundColor: '#007AFF',
    padding: 10,
    borderRadius: 5,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 5,
  },
  calloutButtonText: {
    color: '#fff',
    fontSize: 14,
  },
  loader: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  textOverlayContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    height: 150, // Increased to accommodate two rows
    overflow: 'hidden',
    zIndex: 10,
    justifyContent: 'center',
    alignItems: 'center',
  },
  textOverlayBlur: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    paddingHorizontal: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  animatedTextContainer: {
    bottom: 20,
    position: 'absolute',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
  },
  overlayTitle: {
    fontSize: 22,
    color: '#fff',
    textAlign: 'center',
    lineHeight: 30,
    paddingHorizontal: 20,
    width: '100%',
    marginVertical: 3, // Add spacing between rows
  },
  blurContainer: {
    position: 'absolute',
    bottom: 24,
    right: 24,
    borderRadius: 50,
    overflow: 'hidden',
  },
  button: {
    backgroundColor: 'rgba(0, 122, 255, 0.8)',
    padding: 16,
    borderRadius: 50,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default GoogleMap;
