import React, { useRef, useEffect, useState } from "react";
import { Text, View, ImageBackground, TouchableOpacity, Animated, Image } from "react-native";
import {useNavigation} from "@react-navigation/native";
import {StackNavigation} from "@/app/_layout";
import i18n from "@/config/i18n";
import {SafeAreaView} from "react-native-safe-area-context";

export default function Index() {
    // Animation state
    const fadeAnim = useRef(new Animated.Value(0)).current; // Initial opacity is 0
    const [isFirstTime, setIsFirstTime] = useState(true); // Track whether animation should run
    const { navigate } = useNavigation<StackNavigation>();
    const [language, setLanguage] = useState(i18n.language);




    const handleChangeLanguage = async (value: string) => {
        i18n.changeLanguage(value);
        setLanguage(value);
    };

    useEffect(() => {
        if (isFirstTime) {
            // Start the animation only if it's the first time
            Animated.timing(fadeAnim, {
                toValue: 1,
                duration: 1500, // Animation duration (1.5 seconds)
                useNativeDriver: true,
            }).start();
            setIsFirstTime(false);
        }
    }, [fadeAnim, isFirstTime]);
    return (
        <View className="flex-1" style={{ flex: 1, backgroundColor: "#000" }}>
            <ImageBackground
                source={require("../assets/images/japanese-padoga-3x4.png")}
                resizeMode="cover"
                style={{ flex: 1, justifyContent: "center" }}
                imageStyle={{ opacity: 0.7 }}
            >
                <SafeAreaView className="flex-row justify-end px-4 mt-4">
                {/* Language Buttons */}
                <TouchableOpacity onPress={() => handleChangeLanguage("en")} className="ml-2">
                    <Image source={require("../assets/flags/gb.png")} className="w-8 h-5" />
                </TouchableOpacity>
                <TouchableOpacity onPress={() => handleChangeLanguage("ro")} className="ml-2">
                    <Image source={require("../assets/flags/ro.png")} className="w-8 h-5" />
                </TouchableOpacity>
                <TouchableOpacity onPress={() => handleChangeLanguage("de")} className="ml-2">
                    <Image source={require("../assets/flags/de.png")} className="w-8 h-5" />
                </TouchableOpacity>
            </SafeAreaView>

                {/* Animated container */}
                <Animated.View
                    style={{
                        flex: 1,
                        justifyContent: "space-around",
                        marginTop: 10,
                        opacity: fadeAnim, // Bind opacity to fadeAnim
                    }}
                >
                    <Text className="text-white font-bold text-4xl text-center">
                        {i18n.t("login.welcome")}
                    </Text>

                    <View className="space-y-4 pt-20">
                        <TouchableOpacity
                            onPress={() => navigate('SignUp')}
                            className="py-3 bg-amber-50 mx-12 rounded-xl"
                        >
                            <Text className="text-xl font-bold text-center text-yellow-600">
                                {i18n.t("login.signUp")}
                            </Text>
                        </TouchableOpacity>

                        <View className="pt-4">
                            <TouchableOpacity
                                onPress={() => navigate('Login')}
                                className="py-3 bg-amber-50 mx-12 rounded-xl"
                            >
                                <Text className="text-xl font-bold text-center text-yellow-600">
                                    {i18n.t("login.login")}
                                </Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </Animated.View>
            </ImageBackground>
        </View>
    );
}
