import React from "react";
import {
  View,
  Text,
  Image,
  ScrollView,
  TouchableOpacity,
  ActivityIndicator,
} from "react-native";
import { Place } from "./types/PlaceTypes";
import i18n from "@/config/i18n";

interface PlacesListProps {
  places: Place[];
  isLoading: boolean;
  onPlacePress: (place: Place) => void;
}

export const PlacesList = ({ places, isLoading, onPlacePress }: PlacesListProps) => {
  if (isLoading) {
    return (
        <View className="flex-1 justify-center items-center">
          <ActivityIndicator size="large" color="#ef4444" />
        </View>
    );
  }
  if (places.length === 0) {
    // Display this message if no results match the search query
    return (
        <View className="flex-1 justify-center items-center">
          <Text className="text-white text-lg">
              {i18n.t("search.noResults")}
          </Text>
        </View>
    );
  }

  return (
      <ScrollView className="flex-1 mt-4 px-4"
                  contentContainerStyle={{ paddingBottom: 100 }} // Add padding to avoid being hidden by the SearchBar
      >
        {places.map((place) => (
            <TouchableOpacity
                key={place.id}
                className="bg-white/30 rounded-2xl mb-3 overflow-hidden"
                accessibilityRole="button"
                accessibilityLabel={`View details for ${place.name}`}
                onPress={() => onPlacePress(place)}  // Call onPlacePress when a place is clicked
            >
              <Image
                  source={{ uri: place.image }}
                  className="w-full h-32 rounded-t-2xl"
                  accessibilityRole="image"
                  accessibilityLabel={`Image of ${place.name}`}
              />
              <View className="p-3">
                  <Text className="text-lg font-bold text-gray-800">{place.name}</Text>
                  <Text className="text-gray-600 mt-1">{`${place.distance} km`}</Text>
              </View>
            </TouchableOpacity>
        ))}
      </ScrollView>
  );
};