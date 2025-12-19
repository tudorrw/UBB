import React, { useState, useEffect } from 'react';
import { StyleSheet, View, Text, ActivityIndicator, Button } from 'react-native';
import MapView, { PROVIDER_GOOGLE } from 'react-native-maps';
import * as Location from 'expo-location';
import NotificationBanner from './NotificationBanner'; // Adjust the path accordingly
import { GoogleGenerativeAI } from "@google/generative-ai"; // Import Google Generative AI
import * as Speech from 'expo-speech';
import {SafeAreaView} from "react-native-safe-area-context";
// import { GOOGLE_MAPS_API_KEY, AI_API_KEY, LANGUAGE } from '@env';
const GOOGLE_MAPS_API_KEY=process.env.EXPO_PUBLIC_GOOGLE_MAPS_API_KEY;
const AI_API_KEY=process.env.EXPO_PUBLIC_AI_API_KEY;
const LANGUAGE=process.env.EXPO_PUBLIC_LANGUAGE;

const GoogleMap = () => {
  const [location, setLocation] = useState<Location.LocationObject | null>(null);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [notifications, setNotifications] = useState<any[]>([]); // State for notifications

  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        setErrorMsg('Permission to access location was denied');
        return;
      }

      let currentLocation = await Location.getCurrentPositionAsync({});
      setLocation(currentLocation);
      
    })();

  }, []);


  // const speak = async (text: string | null) => {
  //   if (!text) {
  //     return;
  //   }

  //   try {
  //     // Stop any ongoing speech before starting new speech
    
  //     await Tts.stop();
  //     console.log('Speaking:', text);
  //     Tts.speak(text);
  //   } catch (error) {
  //     console.error('Error during TTS:', error);
  //   }
  // };

  const fetchNearbyPlaces = async () => {
    if (!location) {
      setErrorMsg('Location not available');
      return;
    }

    const { latitude, longitude } = location.coords;
    const url = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=1350&type=tourist_attraction&language=${LANGUAGE}&key=${GOOGLE_MAPS_API_KEY}`;

    try {
      const response = await fetch(url);
      const data = await response.json();
      if (data.results && data.results.length > 0) {
        data.results.forEach((place: { name: string, vicinity: string }, index: number) => {
          const notification = {
            message: place.name + " " + place.vicinity,
            actionText: "Ask AI", // Action text for each notification
            onAction: async () => {
              // Call the Google Generative AI on action
              console.log(Speech.maxSpeechInputLength);
              try {
                Speech.speak("hello there");
                console.log('Speaking:', place.name);
              } catch (error) {
                console.error('Speech error:', error);
              }

              const aiResponse = await callGenerativeAI(place.name + " " + place.vicinity);

              console.log(`AI response for ${place.name}:`, aiResponse);
              // You can also show the AI response in a different way if needed
              // speak(aiResponse);
            },
          };
          setNotifications((prev) => [...prev, notification]); // Append new notification
        });
      } else {
        setNotifications((prev) => [...prev, { message: 'No nearby places found' }]);
      }
    } catch (error) {
      setNotifications((prev) => [...prev, { message: 'Failed to fetch places' }]);
    }
  };

  const callGenerativeAI = async (placeName: string) => {
    const ai = new GoogleGenerativeAI(AI_API_KEY! );
    try {
      const model = await ai.getGenerativeModel({ model: "gemini-pro" });

      const response = await model.generateContent(`Tell me more about ${placeName}.`);
      return response.response.text(); // Adjust based on your response structure
    } catch (error) {
      console.error('Error calling Generative AI:', error);
      return null;
    }
  };

  const handleDismissNotification = (index: number) => {
    setNotifications((prev) => prev.filter((_, i) => i !== index)); // Dismiss specific notification
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
    <SafeAreaView style={styles.container} >
      <Button title="Show Nearby Places" onPress={fetchNearbyPlaces} />
      {notifications.length > 0 && (
        <NotificationBanner notifications={notifications} onDismiss={handleDismissNotification} />
      )}
      <MapView
        style={styles.map}
        // provider={PROVIDER_GOOGLE}
        showsUserLocation={true}
        initialRegion={{
          latitude: location.coords.latitude,
          longitude: location.coords.longitude,
    // Adjust these values for bigger zoom
    latitudeDelta: 0.005, // Smaller value for closer zoom
    longitudeDelta: 0.005, // Smaller value for closer zoom
        }}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    marginTop: 25,
    flex: 1,
  },
  map: {
    zIndex: -1,
    width: '100%',
    height: '100%',
  },
  loader: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default GoogleMap;