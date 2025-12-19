import {Alert, Animated, Image, ImageBackground, Text, TextInput, TouchableOpacity, View} from "react-native";
import {SafeAreaView} from "react-native-safe-area-context";
import {ArrowLeftIcon} from "react-native-heroicons/solid";
import {useNavigation} from "@react-navigation/native";
import {StackNavigation} from "@/app/_layout";
import React, {useState} from "react";
import {signInWithEmailAndPassword} from "firebase/auth";
import {FIREBASE_AUTH} from "@/config/firebase";
import {BlurView} from "expo-blur";
import i18n from "@/config/i18n";
import FloatLabelInput from "@/components/FloatLabelInput";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { navigate } = useNavigation<StackNavigation>();
    const navigation = useNavigation();
    const [error, setError] = useState({ email: false, password: false });




    const handleSignIn = async () => {
        if(email && password) {
                try{
                    await signInWithEmailAndPassword(FIREBASE_AUTH, email, password)
                } catch (err) {
                    console.log(err);
                    if ((err as any).code === "auth/invalid-email") {
                        setError({ email: true, password: false }); // Only email is invalid
                        Alert.alert(`${i18n.t("login.error-handling.invalid-email")}`);
                    } else if ((err as any).code === "auth/invalid-credential") {
                        setError({ email: true, password: true }); // Both email and password are invalid
                        Alert.alert(`${i18n.t("login.error-handling.invalid-credentials")}`);}
                }
            }
        else {
            console.log("Please enter email and password");
        }
    }

    const isButtonDisabled = !email || !password


    return(
        <View className="flex-1" style={{ flex: 1, backgroundColor: "#000" }}>
            <ImageBackground
                source={require("../assets/images/japanese-padoga-3x4.png")}
                resizeMode="cover"
                style={{ flex: 1, justifyContent: "center" }}
                imageStyle={{ opacity: 0.7 }}
            >
                <SafeAreaView className="flex">

                    <View className="flex-row justify-start">
                        <TouchableOpacity
                            onPress={() => navigation.goBack()}
                            className="bg-yellow-600 p-2 rounded-tr-2xl rounded-bl-2xl ml-4"
                        >
                            <ArrowLeftIcon size="20" color="black"/>
                        </TouchableOpacity>
                    </View>
                </SafeAreaView>
                <View
                    style={{
                        flex: 1,
                        justifyContent: "flex-start",
                        alignItems: "center",
                        marginTop: "20%", // Adjust for space above the card
                    }}
                >
                    <BlurView
                        intensity={30}
                        tint="light"
                        style={{
                            borderRadius: 10,
                            overflow: 'hidden',
                            width: "90%",
                            padding: 20,
                            backgroundColor: "rgba(255, 255, 255, 0.8)", // Adds a white tint with opacity
                        }}
                    >
                        <Text
                            className="font-xl font-bold text-center text-black"
                        >
                            {i18n.t("login.login")}
                        </Text>
                        <View className="form space-y-2">
                            <FloatLabelInput
                                label={i18n.t("login.email")}
                                value={email}
                                onChangeText={(value) => {
                                    setEmail(value);
                                    if (value) setError((prev) => ({ ...prev, email: false })); // Clear email error on input
                                }}
                                error={error.email} // Dynamically set error state
                            />

                            {/* Password Input */}
                            <FloatLabelInput
                                label={i18n.t("login.password")}
                                value={password}
                                onChangeText={(value) => {
                                    setPassword(value);
                                    if (value) setError((prev) => ({ ...prev, password: false })); // Clear password error on input
                                }}
                                secureTextEntry
                                error={error.password} // Dynamically set error state
                            />


                            <TouchableOpacity className="flex items-end mb-3">
                                <Text className="text-gray-700">{i18n.t("login.forgotPassword")}</Text>
                            </TouchableOpacity>

                            <TouchableOpacity
                                className={`py-3 rounded-xl ${
                                    isButtonDisabled ? "bg-gray-300" : "bg-yellow-600"
                                }`}
                                disabled={isButtonDisabled}
                                onPress={handleSignIn}
                            >
                                <Text
                                    className={`font-xl font-bold text-center ${
                                        isButtonDisabled ? "text-gray-500" : "text-white"
                                    }`}
                                >
                                    {i18n.t("login.submit")}
                                </Text>
                            </TouchableOpacity>
                        </View>
                        <View className="flex-row justify-center mt-4">
                            <Text className="text-gray-500 font-semibold">{i18n.t("login.noAccount")}
                            </Text>
                            <TouchableOpacity onPress={() => navigate('SignUp')}>
                                <Text className="font-semibold text-yellow-600"> {i18n.t("login.signUp")}</Text>
                            </TouchableOpacity>
                        </View>
                    </BlurView>
                </View>
            </ImageBackground>
        </View>
    );
}