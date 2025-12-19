import FontAwesome from '@expo/vector-icons/FontAwesome';
import { useFonts } from 'expo-font';
import * as SplashScreen from 'expo-splash-screen';
import { useEffect } from 'react';
import 'react-native-reanimated';
import { Audio } from 'expo-av';

// Import your global CSS file
import "../constants/Global.css";

import {NavigationContainer, NavigationProp} from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";

export type ScreenNames = ["Index", "Login", "SignUp", "Dashboard", "Maps", "Places"] // type these manually
export type RootStackParamList = Record<ScreenNames[number], undefined>;
export type StackNavigation = NavigationProp<RootStackParamList>;

import useAuth from "@/hooks/useAuth";
import Index from "@/app/index";
import Login from "@/app/login";
import SignUp from "@/app/signup";
import Dashboard from "@/app/dashboard";
import GoogleMap from "@/app/maps";
import {PlacesScreen} from "@/app/places/PlacesScreen";
import {Platform} from "react-native";

export {
  // Catch any errors thrown by the Layout component.
  ErrorBoundary,
} from 'expo-router';


export default function RootLayout() {
  const [loaded, error] = useFonts({
    SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
    ...FontAwesome.font,
  });

  // Expo Router uses Error Boundaries to catch errors in the navigation tree.
  useEffect(() => {
    if (error) throw error;
  }, [error]);

  useEffect(() => {
    if (loaded) {
      SplashScreen.hideAsync();
    }
  }, [loaded]);

  if (!loaded) {
    return null;
  }

  return <RootLayoutNav />;
}

if (Platform.OS === "ios")
    Audio.setAudioModeAsync({ playsInSilentModeIOS: true });

function RootLayoutNav() {
  const {user} = useAuth();
  const Stack = createNativeStackNavigator<RootStackParamList>();
  if(user) {
    return (
        <Stack.Navigator initialRouteName='Places'>
            <Stack.Screen name="Places" options={{headerShown: false}} component={PlacesScreen} />
          <Stack.Screen name="Maps" options={{headerShown: false}} component={GoogleMap} />

        </Stack.Navigator>
    );
  }else {
    return (
        <Stack.Navigator initialRouteName='Index'>
          <Stack.Screen name="Index" options={{headerShown: false}} component={Index}/>
          <Stack.Screen name="Login" options={{headerShown: false}} component={Login}/>
          <Stack.Screen name="SignUp" options={{headerShown: false}} component={SignUp}/>
        </Stack.Navigator>
    );
  }
}


