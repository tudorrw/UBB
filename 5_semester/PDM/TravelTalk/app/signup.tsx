import {Alert, Image, ImageBackground, Text, TextInput, TouchableOpacity, View, Modal, Pressable} from "react-native";
import {SafeAreaView} from "react-native-safe-area-context";
import {ArrowLeftIcon} from "react-native-heroicons/solid";
import React, {useState} from "react";
import {createUserWithEmailAndPassword} from "firebase/auth";
import {FIREBASE_AUTH} from "@/config/firebase";
import {useNavigation} from "@react-navigation/native";
import {StackNavigation} from "@/app/_layout";
import {BlurView} from "expo-blur";
import {getDatabase, ref, set} from "firebase/database";
import i18n from "@/config/i18n";
import FloatLabelInput from "@/components/FloatLabelInput";

export default function SignUp() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [modalVisible, setModalVisible] = useState(false);
    const { navigate } = useNavigation<StackNavigation>();
    const navigation = useNavigation();
    const [error, setError] = useState({ name: false, email: false, password: false });
    const currentLanguage = i18n.language || "en"; // Fetch the current language, fallback to "en"


    const handleSignUp = async () => {
        if(email && password && name) {
           try{
               const userCredential = await createUserWithEmailAndPassword(FIREBASE_AUTH, email, password)
               const user = userCredential.user;
               const db = getDatabase();
               await set(ref(db, `users/${user.uid}`), {
                   name,
                   settings: { currentLanguage }
               })
               // Alert.alert("USer created with those credentials. Please login to continue.")
               // navigate("Login");
           } catch (err) {
               if ((err as any).code === "auth/invalid-email") {
                   Alert.alert(`${i18n.t("login.error-handling.invalid-email")}`);
                   setError({ email: true, password: false, name: false });
               } else if ((err as any).code === "auth/weak-password") {
                   Alert.alert(`${i18n.t("login.error-handling.weak-password")}`);
                   setError({ email: false, password: true, name: false });
               } else if ((err as any).code === "auth/email-already-in-use") {
                   setModalVisible(true); // Show the modal
                   setError({ email: true, password: false, name: false });

               }
           }
        }
        else {
            console.log("Complete all required fields");
            setError({ email: true, password: true, name: true });

        }
    }


    const isButtonDisabled = !name || !email || !password;


    return(
        <View className="flex-1" style={{ flex: 1, backgroundColor: "#000" }}>
            <ImageBackground
                source={require("../assets/images/japanese-padoga-3x4.png")}
                resizeMode="cover"
                style={{ flex: 1, justifyContent: "center" }}
                imageStyle={{ opacity: 0.7 }}
            >
                {/* Modal for email already in use */}
                <Modal
                    transparent
                    visible={modalVisible}
                    animationType="slide"
                    onRequestClose={() => setModalVisible(false)}
                >
                    <View
                        style={{
                            flex: 1,
                            justifyContent: "center",
                            alignItems: "center",
                            backgroundColor: "rgba(0,0,0,0.5)",
                        }}
                    >
                        <View
                            style={{
                                backgroundColor: "white",
                                padding: 20,
                                borderRadius: 10,
                                width: "80%",
                                alignItems: "center",
                            }}
                        >
                            <Text style={{ fontSize: 18, fontWeight: "bold", marginBottom: 10 }}>
                                {i18n.t("login.error-handling.email-already-in-use")}
                            </Text>
                            <Text style={{ fontSize: 16, marginBottom: 20 }}>
                                {i18n.t("login.error-handling.do-you-want-to-login")}

                            </Text>
                            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                                <Pressable
                                    style={{
                                        backgroundColor: "#FFA500",
                                        padding: 10,
                                        borderRadius: 5,
                                        marginHorizontal: 10,
                                    }}
                                    onPress={() => {
                                        setModalVisible(false);
                                        navigate("Login");
                                    }}
                                >
                                    <Text style={{ color: "white", fontWeight: "bold" }}>{i18n.t("login.login")}</Text>
                                </Pressable>
                                <Pressable
                                    style={{
                                        backgroundColor: "#ddd",
                                        padding: 10,
                                        borderRadius: 5,
                                        marginHorizontal: 10,
                                    }}
                                    onPress={() => setModalVisible(false)}
                                >
                                    <Text style={{ fontWeight: "bold" }}>{i18n.t("login.dismiss")}</Text>
                                </Pressable>
                            </View>
                        </View>
                    </View>
                </Modal>

                {/*{Form}*/}
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
                        marginTop: "10%", // Space between the top of the screen and the form
                    }}
                >
                    <BlurView
                        intensity={30}
                        tint="light"
                        style={{
                            borderRadius: 20,
                            overflow: 'hidden',
                            width: "90%",
                            padding: 20,
                            backgroundColor: "rgba(255, 255, 255, 0.8)", // Adds a white tint with opacity
                        }}
                    >
                        <Text
                            className="font-xl font-bold text-center text-black"
                        >
                            {i18n.t("login.signUp")}

                        </Text>
                        <View className="form space-y-2">
                            <FloatLabelInput
                                label={i18n.t("login.name")}
                                value={name}
                                onChangeText={(value) => {
                                    setName(value);
                                    if (value) setError((prev) => ({ ...prev, email: false })); // Clear email error on input
                                }}
                            />

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

                            <TouchableOpacity
                                className={`py-3 rounded-xl ${
                                    isButtonDisabled ? "bg-gray-300" : "bg-yellow-600"
                                }`}
                                disabled={isButtonDisabled}
                                onPress={handleSignUp}
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
                            <Text className="text-gray-500 font-semibold">{i18n.t("login.alreadyHaveAccount")}
                            </Text>
                            <TouchableOpacity onPress={() => navigate("Login")}>
                                <Text className="font-semibold text-yellow-600"> {i18n.t("login.login")}</Text>
                            </TouchableOpacity>
                        </View>
                    </BlurView>
                </View>
            </ImageBackground>
        </View>
    );
}