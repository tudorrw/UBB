import java.util.Scanner;



public class Assignment2_1 {

    public static final String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Encryption function affine cipher
    private static String Encryption(int a, int b, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for(int cnt = 0; cnt < plaintext.length(); cnt++) {
            char c = plaintext.charAt(cnt);
            if(Character.isLowerCase(c)) {
                int charPosition = lowerCaseAlphabet.indexOf(c);
                int newVal = (a * charPosition + b) % 26;
                ciphertext.append(lowerCaseAlphabet.charAt(newVal));
            }
            if(Character.isUpperCase(c)) {
                int charPosition = upperCaseAlphabet.indexOf(c);
                int newVal = (a * charPosition + b) % 26;
                ciphertext.append(upperCaseAlphabet.charAt(newVal));
            }
        }
        return ciphertext.toString();
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("a = ");
        int a = reader.nextInt();
        System.out.println("b = ");
        int b = reader.nextInt();
        reader.nextLine();
        System.out.println("The plaintext is: ");
        String plaintext = reader.nextLine();
        System.out.println("The encryption of the plaintext is: " + Encryption(a, b, plaintext));
//        System.out.println("The decryption of the cipherText is: " + Decryption(k, cipherText));

    }
}
