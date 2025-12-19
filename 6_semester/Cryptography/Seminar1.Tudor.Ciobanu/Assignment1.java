import java.util.Scanner;

public class Assignment1 {

    public static final String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private static String Decryption(int Key, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for(int cnt = 0; cnt < ciphertext.length(); cnt++) {
            char c = ciphertext.charAt(cnt);
            if(Character.isLowerCase(c)) {
                int charPosition = lowerCaseAlphabet.indexOf(c);
                int newVal = (charPosition - Key) % 26;
                if(newVal < 0) {
                    newVal += 26;
                };
                plaintext.append(lowerCaseAlphabet.charAt(newVal));
            }
            if(Character.isUpperCase(c)) {
                int charPosition = upperCaseAlphabet.indexOf(c);
                int newVal = (charPosition - Key) % 26;
                if(newVal < 0) {
                    newVal += 26;
                };
                plaintext.append(upperCaseAlphabet.charAt(newVal));
            }
        }
        return plaintext.toString();
    }


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Choose k: ");
        int k = reader.nextInt();
        reader.nextLine();
        System.out.println("The ciphertext is: ");
        String cipherText = reader.nextLine();
        System.out.println("The decryption of the cipherText is: " + Decryption(k, cipherText));

    }
}