import React, { useRef, useEffect, useState } from "react";
import {
    View,
    Text,
    TouchableOpacity,
    Animated,
    Dimensions,
    TextInput,
    Image
} from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { FIREBASE_AUTH } from "@/config/firebase";
import { signOut } from "firebase/auth";
import i18n from "@/config/i18n";
import { SafeAreaView } from "react-native-safe-area-context";
import { MapType } from "@/app/places/types/MapTypes";
import { UserInterfaceStyle } from "@/app/places/types/UserInterfaceStyle";

interface ToolbarProps {
    isVisible: boolean;
    onChangeLanguage: (language: string) => void;
    onChangeMapType: (mapType: MapType) => void;
    onChangeUserInterfaceStyle: (style: UserInterfaceStyle) => void;
    onChangeRadius: (radius: number) => void;
}

export const Toolbar: React.FC<ToolbarProps> = ({
    isVisible,
    onChangeLanguage,
    onChangeMapType,
    onChangeUserInterfaceStyle,
    onChangeRadius,
}) => {
    const screenWidth = Dimensions.get("window").width;
    const toolbarWidth = screenWidth * 0.65;
    const slideAnimation = useRef(new Animated.Value(screenWidth)).current;

    const [language, setLanguage] = useState(i18n.language);
    const [mapType, setMapType] = useState<MapType>(MapType.Standard);
    const [userInterfaceStyle, setUserInterfaceStyle] = useState<UserInterfaceStyle>(UserInterfaceStyle.Light);
    const [radius, setRadius] = useState<number>(3000);
    const [openDropdown, setOpenDropdown] = useState<string | null>(null);


    useEffect(() => {
        const toValue = isVisible ? 0 : -screenWidth;
        Animated.timing(slideAnimation, {
            toValue,
            duration: 300,
            useNativeDriver: true,
        }).start();
    }, [isVisible]);

    const handleLogout = async () => {
        try {
            await signOut(FIREBASE_AUTH);
            console.log("User signed out successfully.");
        } catch (error) {
            console.error("Error signing out:", error);
        }
    };

    const LanguageDropdown = ({
        label,
        data,
        value,
        onSelect,
        withFlags = false,
    }: {
        label: string;
        data: { label: string; value: string | number; flag?: any }[];
        value: string | number;
        onSelect: (value: string | number) => void;
        withFlags?: boolean;
    }) => (
        <View className="relative w-full mb-6">
            <TouchableOpacity
                onPress={() => setOpenDropdown(openDropdown === label ? null : label)}
                className="px-4 py-2 border border-gray-300 rounded-lg bg-white flex-row justify-between items-center"
            >
                <View className="flex-row items-center">
                    {withFlags && data.find((d) => d.value === value)?.flag && (
                        <Image
                            source={data.find((d) => d.value === value)?.flag}
                            className="w-8 h-5"
                        />
                    )}
                    <Text className="text-gray-700 font-medium">
                        {data.find((d) => d.value === value)?.label || "Select"}
                    </Text>
                </View>
                <Ionicons
                    name={openDropdown === label ? "chevron-up" : "chevron-down"}
                    size={20}
                    color="gray"
                />
            </TouchableOpacity>

            {openDropdown === label && (
                <Animated.View className="border border-gray-300 rounded-lg mt-2 bg-white">
                    {data.map((item, index) => (
                        <TouchableOpacity
                            key={item.value}
                            onPress={() => {
                                onSelect(item.value);
                                setOpenDropdown(null);
                                if (label === "Language") {
                                    setLanguage(item.value as string);
                                    i18n.changeLanguage(item.value as string);
                                }
                            }}
                            className={`flex-row items-center p-3 ${
                                index % 2 === 0 ? "bg-gray-100" : "bg-gray-200"
                            } ${item.value === value ? "bg-blue-200" : ""}`}
                        >
                            {withFlags && item.flag && (
                                <Image source={item.flag} className="w-6 h-6 mr-2" />
                            )}
                            <Text
                                className={`text-gray-700 ${
                                    item.value === value ? "font-bold" : ""
                                }`}
                            >
                                {item.label}
                            </Text>
                        </TouchableOpacity>
                    ))}
                </Animated.View>
            )}
        </View>
    );

    const Dropdown = ({
        label,
        data,
        value,
        onSelect,
    }: {
        label: string;
        data: { label: string; value: string | number }[];
        value: string | number;
        onSelect: (value: string | number) => void;
    }) => (
        <View className="relative w-full mb-6">
            <TouchableOpacity
                onPress={() => setOpenDropdown(openDropdown === label ? null : label)}
                className="px-4 py-2 border border-gray-300 rounded-lg bg-white flex-row justify-between items-center"
            >
                <Text className="text-gray-700 font-medium">
                    {data.find((d) => d.value === value)?.label || "Select"}
                </Text>
                <Ionicons
                    name={openDropdown === label ? "chevron-up" : "chevron-down"}
                    size={20}
                    color="gray"
                />
            </TouchableOpacity>

            {openDropdown === label && (
                <Animated.View className="border border-gray-300 rounded-lg mt-2 bg-white">
                    {data.map((item, index) => (
                        <TouchableOpacity
                            key={item.value}
                            onPress={() => {
                                // if (item.value === "custom") {
                                //     setIsCustomRadius(true);
                                //     setOpenDropdown(null);
                                // } else {
                                //     setIsCustomRadius(false);
                                    onSelect(item.value);

                                setOpenDropdown(null);
                            }}
                            className={`p-3 ${
                                index % 2 === 0
                                    ? "bg-gray-100"
                                    : "bg-gray-200"
                            } ${
                                item.value === value
                                    ? "bg-blue-200"
                                    : ""
                            }`}
                        >
                            <Text
                                className={`text-gray-700 ${
                                    item.value === value ? "font-bold" : ""
                                }`}
                            >
                                {item.label}
                            </Text>
                        </TouchableOpacity>
                    ))}
                </Animated.View>
            )}
        </View>
    );

    return (
        <Animated.View
            className="absolute top-0 bottom-0 left-0 bg-white shadow-lg rounded-r-2xl"
            style={{
                width: toolbarWidth,
                transform: [{ translateX: slideAnimation }],
            }}
        >
            <SafeAreaView className="flex-1">
                {/* Toolbar Header */}
                <View className="flex-row justify-center items-center px-5 py-4 border-b border-gray-300">
                    <Text className="text-lg font-bold text-gray-800">
                        {i18n.t("settings.Settings")}
                    </Text>
                </View>

                {/* Toolbar Content */}
                <View className="flex-1 justify-start px-6 mt-6 space-y-6">
                    {/* Language */}
                    <View>
                        <Text className="text-xl font-semibold text-gray-800 mb-2">
                            {i18n.t("settings.language")}
                        </Text>
                        <LanguageDropdown
                            label="Language"
                            data={[
                                {
                                    label: i18n.t("languages.english"),
                                    value: "en",
                                    flag: require("@/assets/flags/gb.png"),
                                },
                                {
                                    label: i18n.t("languages.romanian"),
                                    value: "ro",
                                    flag: require("@/assets/flags/ro.png"),
                                },
                                {
                                    label: i18n.t("languages.german"),
                                    value: "de",
                                    flag: require("@/assets/flags/de.png"),
                                },
                            ]}
                            value={language}
                            onSelect={(value) => {
                                setLanguage(value as string);
                                onChangeLanguage(value as string);
                            }}
                        />
                    </View>

                    {/* Map Toolbar Options */}
                    <View>
                        <Text className="text-xl font-semibold text-gray-800 mb-2">
                            {i18n.t("settings.mapSettings")}
                        </Text>
                          <View className="left-2">
                              {/* Radius */}
                              <View>
                                <Text className="text-lg font-semibold text-gray-600 mb-2">
                                    {i18n.t("settings.radius")}
                                </Text>
                                <Dropdown
                                    label="Radius"
                                    data={[
                                        { label: i18n.t("radius.300"), value: 300 },
                                        { label: i18n.t("radius.1000"), value: 1000 },
                                        { label: i18n.t("radius.3000"), value: 3000 },

                                    ]}
                                    value={radius}
                                    onSelect={(value) => {
                                        if (typeof value === "number") {
                                            setRadius(value);
                                            onChangeRadius(value);
                                        }
                                    }}
                                />
                            </View>



                            {/* Map Section */}
                            <View>
                                <Text className="text-lg font-semibold text-gray-600 mb-2">
                                    {i18n.t("settings.mapType")}
                                </Text>
                                <Dropdown
                                    label="Map Type"
                                    data={[
                                        { label: i18n.t("mapType.standard"), value: MapType.Standard },
                                        { label: i18n.t("mapType.satellite"), value: MapType.Satellite },
                                    ]}
                                    value={mapType}
                                    onSelect={(value) => {
                                        setMapType(value as MapType);
                                        onChangeMapType(value as MapType);
                                    }}
                                />
                            </View>

                            {/* Appearance Section */}
                            <View>
                                <Text className="text-lg font-semibold text-gray-600 mb-2">
                                    {i18n.t("settings.appearance")}
                                </Text>
                                <Dropdown
                                    label="User Interface Style"
                                    data={[
                                        { label: i18n.t("appearance.light"), value: UserInterfaceStyle.Light },
                                        { label: i18n.t("appearance.dark"), value: UserInterfaceStyle.Dark },
                                    ]}
                                    value={userInterfaceStyle}
                                    onSelect={(value) => {
                                        setUserInterfaceStyle(value as UserInterfaceStyle);
                                        onChangeUserInterfaceStyle(value as UserInterfaceStyle);
                                    }}
                                />
                            </View>
                        </View>
                    </View>
                </View>

                {/* Log Out Button */}
                <View className="absolute bottom-10 left-0 right-0 items-center">
                    <TouchableOpacity
                        onPress={handleLogout}
                        className="w-3/4 py-3 rounded-full bg-black"
                    >
                        <Text className="text-white text-center text-base font-bold">
                            {i18n.t("settings.logout")}
                        </Text>
                    </TouchableOpacity>
                </View>
            </SafeAreaView>
        </Animated.View>
    );
};