import React from "react";
import MapView, { Marker, Callout } from "react-native-maps";
import { View, Text, StyleSheet } from "react-native";
import { LocationState, Place } from "./types/PlaceTypes";
import * as Location from "expo-location";
import {MapType} from "@/app/places/types/MapTypes";
import {UserInterfaceStyle} from "@/app/places/types/UserInterfaceStyle";

interface MapComponentProps {
    location: Location.LocationObject;
    places: Place[];
    mapType: MapType;
    userInterfaceStyle: UserInterfaceStyle;
}

export const MapComponent = ({ location, places, mapType, userInterfaceStyle }: MapComponentProps) => {
    const initialRegion: LocationState = {
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
        latitudeDelta: 0.005,
        longitudeDelta: 0.005,
    };
    console.log("location",location);

    return (
        <View className="absolute inset-0">
            <MapView
                style={StyleSheet.absoluteFillObject}
                initialRegion={{
                    latitude: location.coords.latitude,
                    longitude: location.coords.longitude,
                    latitudeDelta: 0.005,
                    longitudeDelta: 0.005,
                }}
                showsUserLocation={true}
                userInterfaceStyle={userInterfaceStyle}
                // mapType={mapType}
                showsCompass={false}
                zoomEnabled={true} // Enables pinch-to-zoom gestures
                zoomControlEnabled={true} // Displays zoom controls on the map
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
        </View>
    );
};
