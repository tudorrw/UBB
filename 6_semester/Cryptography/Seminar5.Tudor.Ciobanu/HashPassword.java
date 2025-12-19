import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class HashPassword {
    private static final SecureRandom random = new SecureRandom();

    // Generate a salted message
    public static String SaltedMessage(String message) {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Convert salt to hex string for readability
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        // Prepend salt to message
        return sb + message;
    }

    // Hash a message using SHA-256
    public static String HashedMessage(String message) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Hide the hash in an image using steganography
    public static void SteganographyImage(String hash, String inputImagePath, String outputImagePath) {
        try {
            // Convert the hash to binary
            String binaryHash = hexToBinary(hash);
            System.out.println("Binary Hash: " + binaryHash);

            // Read the input image
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            int width = image.getWidth();
            int height = image.getHeight();

            System.out.println("Image size: " + width + "x" + height);

            // Embed hash into image
            int bitIndex = 0;
            // Process each 8x8 block
            for (int blockY = 0; blockY < height && bitIndex < binaryHash.length(); blockY += 8) {
//                System.out.println("Block Y : (" + blockY + ")");
                for (int blockX = 0; blockX < width && bitIndex < binaryHash.length(); blockX += 8) {
                    // Process each pixel in block
//                    System.out.println("Block X : (" + blockX + ")");
                    for (int y = blockY; y < blockY + 8 && y < height && bitIndex < binaryHash.length(); y++) {
//                        System.out.println("Processing pixel at (" + blockX + ", " + y + ")");
                        for (int x = blockX; x < blockX + 8 && x < width && bitIndex < binaryHash.length(); x++) {
//                            System.out.println("Processing pixel at (" + x + ", " + y + ")");
                            // Get the RGB values

                            int pixel = image.getRGB(x, y);
                            int red = (pixel >> 16) & 0xff;
                            int green = (pixel >> 8) & 0xff;
                            int blue = pixel & 0xff;
//                            System.out.println("RGB: " + red + ", " + green + ", " + blue);

                            // Modify last bit of each color channel if we still have bits to hide
                            if (bitIndex < binaryHash.length()) {
                                red = (red & 0xFE) | (binaryHash.charAt(bitIndex++) == '1' ? 1 : 0);
                            }

                            if (bitIndex < binaryHash.length()) {
                                green = (green & 0xFE) | (binaryHash.charAt(bitIndex++) == '1' ? 1 : 0);
                            }

                            if (bitIndex < binaryHash.length()) {
                                blue = (blue & 0xFE) | (binaryHash.charAt(bitIndex++) == '1' ? 1 : 0);
                            }

//                            int bit = binaryHash.charAt(bitIndex++) - '0';
//                            red = (red + bit) & 0xFF;
//
//                            // Channel G
//                            if (bitIndex < binaryHash.length()) {
//                                bit = binaryHash.charAt(bitIndex++) - '0';
//                                green = (green + bit) & 0xFF;
//                            }
//
//                            // Channel B
//                            if (bitIndex < binaryHash.length()) {
//                                bit = binaryHash.charAt(bitIndex++) - '0';
//                                blue = (blue + bit) & 0xFF;
//                            }
                            // Combine the modified RGB values
                            int newPixel =  (red << 16) | (green << 8) | blue;
                            image.setRGB(x, y, newPixel);
                        }
                    }
                }
            }

            ImageIO.write(image, "png", new File(outputImagePath));
            System.out.println("Successfully embedded hash in image. " + bitIndex + " bits hidden.");

            if (bitIndex < binaryHash.length()) {
                System.out.println("Warning: Not all hash bits were embedded. Image too small.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert hex string to binary string
    private static String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            String bin = Integer.toBinaryString(Integer.parseInt(hex.substring(i, i+1), 16));
            while (bin.length() < 4) {
                bin = "0" + bin;
            }
            binary.append(bin);
        }
        return binary.toString();
    }

    // Extract the hidden hash from an image
    public static String extractHash(String imagePath, int hashLengthInBits) {
        try {
            // Read the image
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();

            StringBuilder binaryHash = new StringBuilder();
            int bitIndex = 0;

            // Process each 8x8 block
            outer:
            for (int blockY = 0; blockY < height; blockY += 8) {
                for (int blockX = 0; blockX < width; blockX += 8) {
                    // Process each pixel in block
                    for (int y = blockY; y < Math.min(blockY + 8, height) && bitIndex < hashLengthInBits; y++) {
                        for (int x = blockX; x < Math.min(blockX + 8, width) && bitIndex < hashLengthInBits; x++) {
                            // Get the RGB values
                            int pixel = image.getRGB(x, y);
                            int red = (pixel >> 16) & 0xff;
                            int green = (pixel >> 8) & 0xff;
                            int blue = pixel & 0xff;

                            // Extract last bit of each color channel
                            if (bitIndex < hashLengthInBits) {
                                binaryHash.append(red & 1);
                                bitIndex++;
                            }

                            if (bitIndex < hashLengthInBits) {
                                binaryHash.append(green & 1);
                                bitIndex++;
                            }

                            if (bitIndex < hashLengthInBits) {
                                binaryHash.append(blue & 1);
                                bitIndex++;
                            }

                            if (bitIndex >= hashLengthInBits) {
                                break outer;
                            }
                        }
                    }
                }
            }

            // Convert binary back to hex
            StringBuilder hexHash = new StringBuilder();
            for (int i = 0; i < binaryHash.length(); i += 4) {
                int end = Math.min(i + 4, binaryHash.length());
                String nibble = binaryHash.substring(i, end);
                hexHash.append(Integer.toHexString(Integer.parseInt(nibble, 2)));
            }

            return hexHash.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        ElGamal elGamal = new ElGamal();
        long p = 107;
        long alpha = 122503;
        long a = 10;
//         Get message and k from user input
        System.out.print("Enter the message to sign: ");
        String message = scanner.nextLine();
//        String message = "B";

//        System.out.print("Enter the value of k (a number used for signing): ");
//        long k = scanner.nextLong();
        long k = 45;
        // Sign message
        ElGamal.SignedMessage signed = elGamal.Sign_ElGamal(p, alpha, a, message, k);
        System.out.println("gamma = " + signed.gamma + ", delta = " + signed.delta);

        // Convert the signature to a string for hashing
        // Step 1: Add salt to message
        String saltedMessage = SaltedMessage(message + signed.gamma + signed.delta);
        System.out.println("Salted Message: " + saltedMessage);
        // Step 2: Hash the salted message
        String hashedMessage = HashedMessage(saltedMessage);
        System.out.println("Hashed Message: " + hashedMessage);

        // Step 3: Hide the hashed message in an image
        String inputImagePath = "src/yinyang.jpg";
        String outputImagePath = "src/image_new.jpg";
        System.out.println(inputImagePath);
        SteganographyImage(hashedMessage, inputImagePath, outputImagePath);

        // Optional: Extract and verify the hidden hash
        String extractedHash = extractHash(outputImagePath, hashedMessage.length() * 4); // Each hex char is 4 bits
        System.out.println("Extracted Hash: " + extractedHash);
        System.out.println("Hash matches: " + hashedMessage.equals(extractedHash));
    }
}