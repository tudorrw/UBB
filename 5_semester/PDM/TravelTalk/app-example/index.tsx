import { useState } from 'react';

import { Text, View } from "react-native";
import AnimatedIntro from "@/components/AnimatedIntro";
import BottomLoginSheet from "@/components/BottomLoginSheet";
import { SafeAreaView } from 'react-native-safe-area-context';
export default function Index() {
  return (
    <SafeAreaView
      style={{
        flex: 1,
        // justifyContent: "center",
        // alignItems: "center",
      }}
    >
      <AnimatedIntro />
      <BottomLoginSheet />
    </SafeAreaView>
  );
}
