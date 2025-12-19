package com.vvss.FlavorFiesta.security.passwordencoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SimplePasswordEncryption implements PasswordEncoder {
    final int shift = 10;

    @Override
    public String encode(CharSequence rawPassword) {
        return encrypt(rawPassword.toString(), shift);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(decrypt(encodedPassword, shift));
    }

    // Encrypt plaintext using Caesar cipher with a given shift
    public static String encrypt(String plaintext, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int charIndex = c - base;
                char encryptedChar = (char) (((charIndex + shift) % 26) + base);
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    // Decrypt ciphertext using Caesar cipher with a given shift
    public static String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - shift); // Decryption is just encryption with the opposite shift
    }
}
