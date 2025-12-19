import React, { useState, useEffect, useRef } from "react";
import {
    View,
    ActivityIndicator,
    Animated,
    LayoutChangeEvent,
    StyleSheet,
    Text,
    TouchableWithoutFeedback,
    TouchableOpacity
} from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { BlurView } from "expo-blur";
import * as Location from "expo-location";
import { PlacesList } from "./PlacesList";
import { SearchBar } from "./SearchBar";
import { NavigationHeader, NavigationHeaderRef } from "./NavigationHeader";
import { CategoryTabs } from "./CategoryTabs";
import { MapComponent } from "./MapComponent";
import { Place } from "./types/PlaceTypes";
import { Toolbar } from "@/app/places/Toolbar";
import {MapType} from "@/app/places/types/MapTypes";
import {UserInterfaceStyle} from "@/app/places/types/UserInterfaceStyle";
import i18n from "@/config/i18n";
import MapView, {Callout, Marker} from "react-native-maps";

import {
    getFirestore,
    collection,
    addDoc,
    serverTimestamp,
    where,
    getDocs,
    query,
    Timestamp,
    orderBy, limit
} from 'firebase/firestore';
import { FIREBASE_AUTH, firestore } from '@/config/firebase';
import { getAuth } from "firebase/auth";
import is from "@sindresorhus/is";
import integer = is.integer;
import { Audio } from 'expo-av';
import {TimelineSegment} from "@/app/utils/audio/declarations";
import {Ionicons} from "@expo/vector-icons";


const GOOGLE_MAPS_API_KEY = process.env.EXPO_PUBLIC_GOOGLE_MAPS_API_KEY;

export const PlacesScreen = () => {
    const [location, setLocation] = useState<Location.LocationObject | null>(null);
    const [places, setPlaces] = useState<Place[]>([]);
    const [pastPlaces, setPastPlaces] = useState<Place[]>([]);
    const [filteredPlaces, setFilteredPlaces] = useState<Place[]>([]);
    const [filteredPastPlaces, setFilteredPastPlaces] = useState<Place[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [activeTab, setActiveTab] = useState<"near" | "questions" | "recommended" | "history">("near");

    const [language, setLanguage] = useState(i18n.language);
    const [mapType, setMapType] = useState<MapType>(MapType.Standard);
    const [userInterfaceStyle, setUserInterfaceStyle] = useState<UserInterfaceStyle>(UserInterfaceStyle.Light);
    const [radius, setRadius] = useState<number>(3000); // Default radius
    const [region, setRegion] = useState({
        latitude: 0,
        longitude: 0,
        latitudeDelta: 0.005,
        longitudeDelta: 0.005,
    });
    const mapRef = useRef<MapView | null>(null); // Reference to the map
    const navHeaderRef = useRef< NavigationHeaderRef | null>(null);
    const [sound, setSound] = useState<Audio.Sound | null>(null);
    const [timeline, setTimeline] = useState<TimelineSegment[]>([]);
    const [isPlaying, setIsPlaying] = useState(false); // Track whether the audio is playing
    const [audioPosition, setAudioPosition] = useState(0); // Store the current position




    useEffect(() => {
        const fetchLocation = async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                return;
            }

            let currentLocation = await Location.getCurrentPositionAsync({});
            setLocation(currentLocation);  // Sets the new location on every reload
            setRegion({
                latitude: currentLocation.coords.latitude,
                longitude: currentLocation.coords.longitude,
                latitudeDelta: 0.005,
                longitudeDelta: 0.005,
            })
            fetchNearbyPlaces(currentLocation);  // Fetch places based on this location
            fetchPastPlaces(currentLocation);  // Fetch past places
        };

        fetchLocation();  // This will fetch the location and update the state each time the component reloads
        // navHeaderRef.current?.triggerGoBack(); // Close the content view

    }, [language, radius]);  // The effect will re-run if `language` or `radius` changes
    const fetchNearbyPlaces = async (userLocation: Location.LocationObject) => {
        setIsLoading(true);
        // console.log("Fetching nearby places...");
        // console.log(filteredPastPlaces);
        try {
            const { latitude, longitude } = userLocation.coords;
            const response = await fetch(
                `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=${radius}&type=tourist_attraction&language=${language}&key=${GOOGLE_MAPS_API_KEY}`
            );
            const data = await response.json();
            if (data.results) {
                const formattedPlaces: Place[] = data.results.map((place: any) => {
                    const photoReference = place.photos?.[0]?.photo_reference;
                    const imageUrl = photoReference
                        ? `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoReference}&key=${GOOGLE_MAPS_API_KEY}`
                        : "https://via.placeholder.com/400x300.png?text=" +i18n.t("noImage");

                    return {
                        id: place.place_id,
                        name: place.name,
                        image: imageUrl,
                        distance: calculateDistance(
                            latitude,
                            longitude,
                            place.geometry.location.lat,
                            place.geometry.location.lng
                        ).toFixed(1),
                        coordinate: {
                            latitude: place.geometry.location.lat,
                            longitude: place.geometry.location.lng,
                        },
                        vicinity: place.vicinity,
                    };
                });

                const sortedPlaces = formattedPlaces.sort((a, b) => a.distance - b.distance);
                setPlaces(sortedPlaces);
                setFilteredPlaces(sortedPlaces);
            }
        } catch (error) {
            console.error("Failed to fetch places:", error);
        } finally {
            setIsLoading(false);
        }
    };

    const calculateDistance = (lat1: number, lon1: number, lat2: number, lon2: number) => {
        const toRad = (value: number) => (value * Math.PI) / 180;
        const R = 6371;
        const dLat = toRad(lat2 - lat1);
        const dLon = toRad(lon2 - lon1);
        const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    };

    const handleSearch = (query: string) => {
        if (query.trim() === "") {
            // if(activeTab === "near") {
                setFilteredPlaces(places);
            // }
            // else if (activeTab === "history") {
                setFilteredPastPlaces(pastPlaces);
            // }

        } else {
            // if(activeTab === "near") {
                const filtered = places.filter((place) => place.name.toLowerCase().includes(query.toLowerCase()));
                setFilteredPlaces(filtered);
            // }
            // else if (activeTab === "history") {
                const filteredPast = pastPlaces.filter((place) => place.name.toLowerCase().includes(query.toLowerCase()));
                setFilteredPastPlaces(filteredPast);
            }


    };

    const [isContentVisible, setIsContentVisible] = useState(true);
    const contentOpacity = useRef(new Animated.Value(1)).current;
    const [headerHeight, setHeaderHeight] = useState(0);

    const handleHeaderLayout = (event: LayoutChangeEvent) => {
        const { height } = event.nativeEvent.layout;
        setHeaderHeight(height);
    };

    const [isToolbarVisible, setIsToolbarVisible] = useState(false);

    const toggleContent = () => {
        Animated.timing(contentOpacity, {
            toValue: isContentVisible ? 0 : 1,
            duration: 300,
            useNativeDriver: true,
        }).start();
        setIsContentVisible((prev) => !prev);
    };

    const toggleToolbar = () => {
        setIsToolbarVisible((prev) => !prev);
    };

    if (!location) {
        return (
            <View className="flex-1 justify-center items-center">
                <ActivityIndicator size="large" color="#ef4444" />
            </View>
        );
    }

    const focusOnNewPlace = async (place: Place) => {
        focusOnPlace(place);


        const user = FIREBASE_AUTH.currentUser; // Get the current user
        const email = user ? user.email : null;

        if (email) {
            try {
                // Save the place data to Firestore
                const placesCollectionRef = collection(firestore, 'places_history'); // Reference to Firestore collection
                await addDoc(placesCollectionRef, {
                    place_id: place.id,
                    place_name: place.name,
                    place_image: place.image,
                    location: {
                        latitude: place.coordinate.latitude,
                        longitude: place.coordinate.longitude,
                    },
                    user_email: email,
                    timestamp: serverTimestamp(), // Firestore server timestamp
                    vicinity: place.vicinity,
                });

                console.log("Place saved to Firestore:", place);
            } catch (error) {
                console.error("Error saving place to Firestore:", error);
            }
        } else {
            console.log("No user is logged in.");
        }

        fetchPastPlaces(location);

    }


    const fetchTTSTimeline = async (place: Place) => {
        try {
            const response = await fetch('/api/deepgram_tts', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    text: '',
                    generate: true,
                    // prompt: `Provide a brief summary ( maximum 2-3 sentences) about ${place.name} located in ${place.vicinity}. Describe if notable the history, general atmosphere, local culture, and any other features that make the place unique. Keep the response concise but informative. Provide an answer similar but NOT exactly the same as "Information about ${place.name} located in ${place.vicinity} not found." ONLY IF ABSOLUTELY no information is not found AT ALL but if something at all is found then return the brief (maximum 2-3 sentences) summary based on that. Provide the information in the language: ${language}`,
                    prompt: `Provide a brief summary ( maximum 2-3 sentences) about ${place.name} located in ${place.vicinity}. Describe if notable the history, general atmosphere, local culture, and any other features that make the place unique. Keep the response concise but informative. Provide an answer similar but VERY IMPORTANT, NOT the same as "Information about ${place.name} located in ${place.vicinity} not found." ONLY IF ABSOLUTELY no information is not found AT ALL but if something at all is found then return the brief (maximum 2-3 sentences) summary based on that. Provide the information in the language: ${language}`,
                }),
            });

            if (!response.ok) throw new Error('Failed to fetch TTS timeline');
            const timelineData = await response.json();
            setTimeline(timelineData);
        } catch (error) {
            console.error('Error fetching TTS timeline:', error);
        }
    };

    const playSound = async () => {
        try {
            if (sound) {
                await sound.unloadAsync(); // Unload the previous sound
            }

            const { sound: newSound } = await Audio.Sound.createAsync(
                require('../../assets/output.mp3'), // Adjust the path to your file
            {},
            onPlaybackStatusUpdate
            );

            setSound(newSound);
            await newSound.playAsync(); // Play the audio
            setIsPlaying(true); // Update state to reflect that audio is playing

            console.log("Playing sound...");
            console.log(isPlaying)
        } catch (error) {
            console.error('Error playing sound:', error);
        }
    };

    const stopSound = async () => {
        try {
            if (!sound) {
                console.warn("No audio loaded to stop.");
                return;
            }

            await sound.stopAsync(); // Stop the audio
            setIsPlaying(false); // Update state to reflect that audio is stopped
        } catch (error) {
            console.error("Error stopping sound:", error);
        }
    };

    const toggleAudio = async () => {
        if(sound){
        if (isPlaying) {
            // Pause the audio and store the current position
            const status = await sound.getStatusAsync();
            setAudioPosition(status.positionMillis);
            await sound.pauseAsync();
        } else {
            // Play the audio from the stored position (or start from the beginning)
            sound.playFromPositionAsync(audioPosition).catch(error => console.log(error));
        }
        setIsPlaying(!isPlaying);
            console.log(isPlaying)
        }};

    // Define the onPlaybackStatusUpdate method
    const onPlaybackStatusUpdate = (status: any) => {
        if (status.isLoaded) {
            if (status.didJustFinish) {
                setIsPlaying(false); // Audio finished, update state
                setAudioPosition(0); // Reset position if audio ends
            } else if (status.positionMillis !== audioPosition) {
                setAudioPosition(status.positionMillis); // Update position
            }
        }
    };

    // New function to focus the map on a selected place
    const focusOnPlace = async (place: Place) => {
        if (mapRef.current) {
            navHeaderRef.current?.triggerGoBack(); // Close the content view
            mapRef.current.animateToRegion(
                {
                    latitude: place.coordinate.latitude,
                    longitude: place.coordinate.longitude,
                    latitudeDelta: 0.005,
                    longitudeDelta: 0.005,
                },
                1000 // Duration of the animation
            );
        }
        await fetchTTSTimeline(place).then(() => playSound());
        // console.log("Chat response")
        // const chatResponse = await fetch('/api/gpt_stream', {
        //     method: 'POST',
        //     headers: { 'Content-Type': 'application/json' },
        //     body: JSON.stringify({ prompt:"Hello" })
        // });
        // const chatStreamReader = chatResponse.body?.getReader();
        //
        // if (!chatStreamReader) {
        //     console.log("Failed to read ChatGPT stream");
        // }
        // console.log("Chat response Done")
        // playSound(); // Call the function to play the sound

        console.log("Focused on place:", place);
    };


    const fetchPastPlaces = async (userLocation : Location.LocationObject) => {
        console.log("Fetching past places...");

        const user = getAuth().currentUser;
        const email = user ? user.email : null;

        if (!email) {
            console.log("No user is logged in.");
            return [];
        }

        try {
            // Create a reference to the 'visited_places' collection
            const placesCollectionRef = collection(firestore, 'places_history');

            // Query the collection to get documents where the 'email' field matches the current user's email
            const q = query(
                placesCollectionRef,
                where("user_email", "==", email),
                orderBy("timestamp", "desc"), // Order by timestamp, most recent first
                limit(15) // Limit the query to the last 15 places
            );
            // Get the documents that match the query
            const querySnapshot = await getDocs(q);

            if (querySnapshot.empty) {
                console.log("No places found for this user.");
                return [];
            }

            // Process the documents
            let places: Place[] = querySnapshot.docs.map((doc) => {
                const place: any = doc.data();

                // Assuming your Firestore document has fields like `name`, `coordinates`, `vicinity`
                const imageUrl = place.place_image || "default-image-url"; // Set a default image URL if not available

                const timestamp = place.timestamp as Timestamp;
                return {
                    id: timestamp.toMillis().toString(),
                    name: place.place_name,
                    image: imageUrl,
                    distance: calculateDistance(userLocation.coords.latitude, userLocation.coords.longitude, place.location.latitude, place.location.longitude).toFixed(1),
                    coordinate: {
                        latitude: place.location.latitude,
                        longitude: place.location.longitude,
                    },
                    vicinity: place.vicinity,
                };
            });

            // Return the retrieved places
            places = places.sort((a, b) => parseInt(b.id) - parseInt(a.id));
            setPastPlaces(places);
            setFilteredPastPlaces(places);
        } catch (error) {
            // console.error("Error fetching places from Firestore:", error);
        }
    }

    return (
        <TouchableWithoutFeedback
            onPress={() => {
                if (isToolbarVisible) {
                    navHeaderRef.current?.triggerSettingsPress(); // Close the content view

                }
            }}
        >
        <SafeAreaView className="flex-1 bg-white">
            <MapView
                ref={mapRef}
                style={StyleSheet.absoluteFillObject}
                initialRegion={{
                    latitude: location.coords.latitude,
                    longitude: location.coords.longitude,
                    latitudeDelta: 0.005,
                    longitudeDelta: 0.005,
                }}
                region={region}
                showsUserLocation={true}
                userInterfaceStyle={userInterfaceStyle}
                mapType={mapType}
                showsCompass={false}
                zoomEnabled={true} // Enables pinch-to-zoom gestures
                zoomControlEnabled={false} // Displays zoom controls on the map
                customMapStyle={userInterfaceStyle === UserInterfaceStyle.Dark ? mapCustomStyle : []}
            >
                {places.map((place) => (
                    <Marker.Animated key={place.id} coordinate={place.coordinate} title={place.name} description={place.vicinity}>
                        <Callout>
                            <View className="w-40 p-2">
                                <Text className="font-bold">{place.name}</Text>
                                <Text className="text-gray-600">{place.vicinity}</Text>
                            </View>
                        </Callout>
                    </Marker.Animated>
                ))}
            </MapView>

            <View onLayout={handleHeaderLayout} className="absolute inset-x-0 top-0 z-10">
                <View className="mt-16">
                    <NavigationHeader ref={navHeaderRef} onBackPress={toggleContent} onSettingsPress={toggleToolbar}/>
                </View>
            </View>

            <Animated.View className="absolute inset-0 top-0 left-0 right-0 bottom-0" style={{ opacity: contentOpacity }}     pointerEvents={isContentVisible ? "auto" : "none"}>
                <BlurView intensity={50} className="flex-1">
                    <View style={{ top: 0, marginTop: headerHeight, left: 0, right: 0 }} className="mt-0">
                        <CategoryTabs activeTab={activeTab} setActiveTab={setActiveTab} />
                    </View>
                    {activeTab === "near" && <PlacesList places={filteredPlaces} isLoading={isLoading} onPlacePress={focusOnNewPlace}/>}
                    {activeTab === "history" && <PlacesList places={filteredPastPlaces} isLoading={isLoading} onPlacePress={focusOnPlace}/>}
                    <SearchBar onSearch={handleSearch} />
                </BlurView>
            </Animated.View>

          <Toolbar
              isVisible={isToolbarVisible}
              onChangeLanguage={setLanguage}
              onChangeMapType={(type: MapType) => setMapType(type)}
              onChangeUserInterfaceStyle={(style: UserInterfaceStyle) => setUserInterfaceStyle(style)}
              onChangeRadius={(newRadius: number) => setRadius(newRadius)} // Pass radius handler
          />
            {!isContentVisible && !isToolbarVisible && (
                <View style={{ flex: 1 }}>
                {/* Other UI components */}

                <View style={styles.container}>
                    <TouchableOpacity
                        style={styles.audioButton}
                        onPress={toggleAudio}
                        disabled={!sound} // Disable if no sound is loaded
                    >
                        <Ionicons
                            name={isPlaying ? "stop-circle" : "play-circle"}
                            size={30} // Smaller icon size
                            color="#FFF" // White icon color
                        />
                    </TouchableOpacity>
                </View>
            </View>
            )}
        </SafeAreaView>
        </TouchableWithoutFeedback>
    );
};

const styles = StyleSheet.create({
    container: {
        position: "absolute", // Position the container absolutely
        bottom: 40, // Distance from the bottom edge
        left: 0,
        right: 0,
        alignItems: "center", // Center horizontally
        zIndex: 10, // Ensure it appears above other elements
    },
    audioButton: {
        width: 60, // Smaller button
        height: 60,
        borderRadius: 30, // Make it circular
        backgroundColor: "rgba(128, 128, 128, 0.5)", // Grey with 50% transparency
        justifyContent: "center",
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        // elevation: 5, // For Android shadow
    },
});


    const mapCustomStyle = [
        {
            elementType: "geometry",
            stylers: [{ color: "#242f3e" }],
        },
        {
            elementType: "labels.text.fill",
            stylers: [{ color: "#746855" }],
        },
        {
            elementType: "labels.text.stroke",
            stylers: [{ color: "#242f3e" }],
        },
        {
            featureType: "administrative.locality",
            elementType: "labels.text.fill",
            stylers: [{ color: "#d59563" }],
        },
        {
            featureType: "poi",
            elementType: "labels.text.fill",
            stylers: [{ color: "#d59563" }],
        },
        {
            featureType: "poi.park",
            elementType: "geometry",
            stylers: [{ color: "#263c3f" }],
        },
        {
            featureType: "poi.park",
            elementType: "labels.text.fill",
            stylers: [{ color: "#6b9a76" }],
        },
        {
            featureType: "road",
            elementType: "geometry",
            stylers: [{ color: "#38414e" }],
        },
        {
            featureType: "road",
            elementType: "geometry.stroke",
            stylers: [{ color: "#212a37" }],
        },
        {
            featureType: "road",
            elementType: "labels.text.fill",
            stylers: [{ color: "#9ca5b3" }],
        },
        {
            featureType: "road.highway",
            elementType: "geometry",
            stylers: [{ color: "#746855" }],
        },
        {
            featureType: "road.highway",
            elementType: "geometry.stroke",
            stylers: [{ color: "#1f2835" }],
        },
        {
            featureType: "road.highway",
            elementType: "labels.text.fill",
            stylers: [{ color: "#f3d19c" }],
        },
        {
            featureType: "transit",
            elementType: "geometry",
            stylers: [{ color: "#2f3948" }],
        },
        {
            featureType: "transit.station",
            elementType: "labels.text.fill",
            stylers: [{ color: "#d59563" }],
        },
        {
            featureType: "water",
            elementType: "geometry",
            stylers: [{ color: "#17263c" }],
        },
        {
            featureType: "water",
            elementType: "labels.text.fill",
            stylers: [{ color: "#515c6d" }],
        },
        {
            featureType: "water",
            elementType: "labels.text.stroke",
            stylers: [{ color: "#17263c" }],
        },
    ]
