import React, {forwardRef, useEffect, useImperativeHandle, useState} from "react";
import {View, TouchableOpacity, Keyboard} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { BlurView } from "expo-blur";

interface NavigationHeaderProps {
    onBackPress: () => void;
    onSettingsPress: () => void;

}

// Define the ref's exposed methods
export interface NavigationHeaderRef {
    triggerGoBack: () => void;
    triggerSettingsPress: () => void;
}

export const NavigationHeader = forwardRef<NavigationHeaderRef, NavigationHeaderProps>(
    ({ onBackPress, onSettingsPress }: NavigationHeaderProps, ref) => {
    const [isSettingsVisible, setIsSettingsVisible] = useState(false);
    const [isSettingsPressed, setIsSettingsPressed] = useState(false);
    const [isChevronVisible, setIsChevronVisible] = useState(true);
    const [isChevronPressed, setIsChevronPressed] = useState(false);

    // Expose the handleGoBackPress method via the ref
    useImperativeHandle(ref, () => ({
        triggerGoBack: handleGoBackPress,
        triggerSettingsPress: handleSettingsPress,
    }));

    const handleSettingsPress = () => {
        setIsSettingsPressed((prev) => !prev);
        setIsSettingsVisible((prev) => !prev);
        setIsChevronVisible( (prev) => !prev);
        onSettingsPress();
    }

    const handleGoBackPress = () => {
        setIsChevronPressed((prev) => !prev);
        setIsSettingsVisible((prev) => !prev);

        if(isSettingsVisible && isSettingsPressed) {
            handleSettingsPress();
        }
        Keyboard.dismiss();
        onBackPress();
    };

    return (
        <View className="flex-row justify-between items-center px-6 py-4">
            <View style={{ width: 40 }}>
                {!isSettingsVisible && (
                    <BlurView
                        intensity={!isSettingsVisible ? 50 : 0} // Blur only when isSettingsVisible is true
                        style={{
                            borderRadius: 50,
                            overflow: "hidden",
                        }}
                    >
                        <TouchableOpacity
                            className="p-2 bg-white/20 rounded-full"
                            onPress={handleSettingsPress}
                            accessibilityLabel="Settings"
                            accessibilityRole="button"
                        >
                            <Ionicons name="settings-outline" size={24} color="#1f2937" />
                        </TouchableOpacity>
                    </BlurView>
                )}
            </View>

            {isChevronVisible && (
            <BlurView
                intensity={!isSettingsVisible ? 50 : 0} // Blur only when isSettingsVisible is true
                style={{
                    borderRadius: 50,
                    overflow: "hidden",
                }}
            >
                <TouchableOpacity
                    className="p-2 bg-white/20 rounded-full"
                    onPress={handleGoBackPress}
                    accessibilityLabel="Show places"
                    accessibilityRole="button"
                >
                    <Ionicons name={isChevronPressed ? "chevron-up" : "chevron-down"} size={24} color="#1f2937" />
                </TouchableOpacity>
            </BlurView>
            )}
        </View>
    );
}
);

