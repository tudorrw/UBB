import React, { useState, useEffect, useRef } from 'react';
import { View, TextInput, Text, Animated, StyleSheet, TextStyle } from 'react-native';

interface FloatLabelInputProps {
    label: string;
    value: string;
    onChangeText: (text: string) => void;
    secureTextEntry?: boolean; // Optional for password fields
    error?: boolean; // New prop to indicate error
}

const FloatLabelInput: React.FC<FloatLabelInputProps> = ({
                                                             label,
                                                             value,
                                                             onChangeText,
                                                             secureTextEntry = false,
                                                             error = false,
                                                         }) => {
    const [isFocused, setIsFocused] = useState(false);
    const animatedIsFocused = useRef(new Animated.Value(value ? 1 : 0)).current;

    useEffect(() => {
        Animated.timing(animatedIsFocused, {
            toValue: isFocused || value ? 1 : 0,
            duration: 200,
            useNativeDriver: false,
        }).start();
    }, [isFocused, value]);

    const labelStyle: Animated.WithAnimatedObject<TextStyle> = {
        position: 'absolute',
        left: 12,
        top: animatedIsFocused.interpolate({
            inputRange: [0, 1],
            outputRange: [26, -8],
        }),
        fontSize: animatedIsFocused.interpolate({
            inputRange: [0, 1],
            outputRange: [16, 12],
        }),
        color: error ? 'red' : animatedIsFocused.interpolate({
            inputRange: [0, 1],
            outputRange: ['#aaa', '#000'],
        }),
        paddingHorizontal: 4,
    };

    return (
        <View style={[styles.container, error && styles.errorContainer]}>
            <Animated.Text style={labelStyle}>{label}</Animated.Text>
            <TextInput
                value={value}
                onChangeText={onChangeText}
                style={[styles.input, error && styles.inputError]} // Apply error styles
                onFocus={() => setIsFocused(true)}
                onBlur={() => setIsFocused(false)}
                blurOnSubmit
                secureTextEntry={secureTextEntry}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        paddingTop: 14,
        marginVertical: 12,
        position: 'relative',
    },
    input: {
        height: 45,
        fontSize: 16,
        paddingLeft: 12,
        borderWidth: 1,
        borderColor: '#000',
        borderRadius: 8, // Rounded corners
        color: '#000',
        alignItems: "center"
    },
    inputError: {
        borderColor: 'red',
    },
    errorContainer: {
        borderColor: 'red',
    },
});

export default FloatLabelInput;