import React, { useRef, useEffect, useState } from "react";
import { TouchableOpacity, View, Animated, Text, Easing, LayoutChangeEvent } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { BlurView } from "expo-blur";


interface TextNowPlayingWidgetProps {
  title: string | null;
  onPress: () => void;
}

export const TextNowPlayingWidget = ({ title, onPress }: TextNowPlayingWidgetProps) => (
  <TouchableOpacity
    onPress={onPress}
    activeOpacity={0.85}
    style={{ flex: 1, minHeight: 40 }}
  >
    <BlurView intensity={50} tint="dark" style={{
      flexDirection: "row",
      alignItems: "center",
      borderRadius: 24,
      paddingHorizontal: 18,
      paddingVertical: 8,
      flex: 1,
      minHeight: 40,
    }}>
      <View style={{ marginRight: 10 }}>
        <Ionicons name="chatbubbles" size={28} color="black" />
      </View>
      <Text
        style={{
          color: "#fff",
          fontWeight: "600",
          fontSize: 16,
          flex: 1,
        }}
        numberOfLines={1}
      >
        {title ?? ""}
      </Text>
      <Ionicons name="chevron-up" size={28} color="#222" />
    </BlurView>
  </TouchableOpacity>
);
