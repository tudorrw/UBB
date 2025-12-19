// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { initializeAuth, getReactNativePersistence } from "firebase/auth";
import ReactNativeAsyncStorage from '@react-native-async-storage/async-storage';
import React from "react";
import {getFirestore} from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebase = {
  apiKey: "AIzaSyBWI4RzxxaYZV77pGx_RHTNH4nql1f8iEc",
  authDomain: "walktalk-c2e4a.firebaseapp.com",
  projectId: "walktalk-c2e4a",
  storageBucket: "walktalk-c2e4a.appspot.com",
  messagingSenderId: "822955840973",
  appId: "1:822955840973:web:c1090fed17503ac646e084",
  measurementId: "G-8PL88DV2XC"
};

// Initialize Firebase
const FIREBASE_APP = initializeApp(firebase);
export const firestore = getFirestore(FIREBASE_APP);
export const FIREBASE_AUTH = initializeAuth(FIREBASE_APP, {
    persistence: getReactNativePersistence(ReactNativeAsyncStorage),
    });
