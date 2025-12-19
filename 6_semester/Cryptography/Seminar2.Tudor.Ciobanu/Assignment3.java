import java.util.*;

public class Assignment3 {
    private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String encodeCaesarCipher(String plainText) {
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            int charPos = ALPHABET.indexOf(plainText.charAt(i));
            int keyVal = (charPos + 3) % ALPHABET.length();
            char replaceVal = ALPHABET.charAt(keyVal);
            cipherText.append(replaceVal);
        }
        return cipherText.toString();
    }

    private static Map<Character, Double> getEncryptedSignalByAlphabet() {
        Map<Character, Double> encryptedSignalMap = new HashMap<>();
        for(double i = -10; i < 16; i++) {
            encryptedSignalMap.put(ALPHABET.charAt((int) (i + 10)), i/16);
        }
        return encryptedSignalMap;
    }

    private static List<Double> encryptSignal(String ciphertext, Map<Character, Double> signalAlphabet) {
        List<Double> encryptedSignal = new ArrayList<>();
        for (int i = 0; i < ciphertext.length(); i++) {
            encryptedSignal.add(signalAlphabet.get(ciphertext.charAt(i)));
        }
        return encryptedSignal;

    }

    public static void main(String[] args) {

        //codebyjava
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine().toUpperCase();
        String ciphertext = encodeCaesarCipher(plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        Map<Character, Double> signalAlphabet = getEncryptedSignalByAlphabet();
        System.out.println("Map voltage: " +  signalAlphabet);
        System.out.println("Encrypted Signal: " + encryptSignal(ciphertext, signalAlphabet));
    }

}
