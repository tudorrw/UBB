import React from "react";
import { View, Text, TouchableOpacity, ScrollView } from "react-native";
import i18n from "@/config/i18n";

type Category = "near" | "questions" | "recommended" | "history";

interface CategoryTabsProps {
    activeTab: Category;
    setActiveTab: React.Dispatch<React.SetStateAction<Category>>;
}

export const CategoryTabs = ({ activeTab, setActiveTab }: CategoryTabsProps) => {
    const categories = [
        { key: "near", label: i18n.t("categories.near") },
        { key: "history", label: i18n.t("categories.history") },
        // { key: "questions", label: i18n.t("categories.questions") },
        // { key: "recommended", label: i18n.t("categories.recommended") },
    ];

    return (
        <View className="px-6 mt-4">
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                {categories.map((category) => (
                    <TouchableOpacity
                        key={category.key}
                        className={`px-6 py-2 mx-2 rounded-full ${
                            activeTab === category.key ? "bg-red-500" : "bg-white/20"
                        }`}
                        onPress={() => setActiveTab(category.key as Category)}
                        accessibilityRole="tab"
                        accessibilityState={{ selected: activeTab === category.key }}
                    >
                        <Text
                            className={`font-semibold ${
                                activeTab === category.key ? "text-white" : "text-gray-700"
                            }`}
                        >
                            {category.label}
                        </Text>
                    </TouchableOpacity>
                ))}
            </ScrollView>
        </View>
    );
};