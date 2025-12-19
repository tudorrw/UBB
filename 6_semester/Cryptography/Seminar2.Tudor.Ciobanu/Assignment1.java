import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment1 {
//    WRDQVZHUWKHILQDOH0DP1RXQHHGWRXVHVHPLQDUVWRILQGFLSKHUWH0W

    public static void HackShift(String cipherText) {

        Scanner scanner = new Scanner(System.in);
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01";
        boolean found = false;
        while(!found) {

            Map<Integer, String> results = new HashMap<>();

            for(int key = 0; key < ALPHABET.length(); key++) {
                StringBuilder plainText = new StringBuilder();
                for(int i = 0; i < cipherText.length(); i++) {
                    char ch = cipherText.charAt(i);
                    int charPos = ALPHABET.indexOf(ch);
                    if (charPos != -1) { // Only decrypt known characters
                        int keyVal = (charPos - key + ALPHABET.length()) % ALPHABET.length();
                        char replaceVal = ALPHABET.charAt(keyVal);
                        plainText.append(replaceVal);
                    } else {
                        // add unknown characters that are not in the given alphabet
                        plainText.append(ch);
                    }
                }
                results.put(key, plainText.toString());
                System.out.println("Key " + key + ": " + plainText);
            }

            System.out.print("\nEnter the correct key number (or press Enter to expand alphabet): ");
            String response = scanner.nextLine().trim();
            if(response.matches("\\d+")) {
                int chosenKey = Integer.parseInt(response);
                if (results.containsKey(chosenKey)) {
                    System.out.println("Decryption Key: " + chosenKey);
                    System.out.println("Plaintext: " + results.get(chosenKey));
                    found = true;
                } else {
                    System.out.println("Invalid key number. Try again.");
                }
            } else if (response.isEmpty()) { // User pressed Enter -> Expand alphabet
                if (ALPHABET.length() < 36) { // Stop after adding numbers 0-9
                    char nextDigit = (char) ('0' + (ALPHABET.length() - 26));
                    ALPHABET += nextDigit;
                    System.out.println("Expanding alphabet to: " + ALPHABET);
                } else {
                    System.out.println("All digits added, but plaintext not found.");
                    break;
                }
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ciphertext: ");
        String cipherText = scanner.nextLine().toUpperCase();
        HackShift(cipherText);
    }
}
