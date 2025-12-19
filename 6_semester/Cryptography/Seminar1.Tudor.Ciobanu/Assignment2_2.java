import java.util.Scanner;

public class Assignment2_2 {
    public static final String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("No inverse for a = " + a + " modulo " + m);
    }

    private static String Decryption(int a, int b, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        int aInverse = modInverse(a, 26);
        for (int cnt = 0; cnt < ciphertext.length(); cnt++) {
            char c = ciphertext.charAt(cnt);
            if (Character.isLowerCase(c)) {
                int charPosition = lowerCaseAlphabet.indexOf(c);
                int newVal = (aInverse * (charPosition - b)) % 26;
                if(newVal < 0) {
                    newVal += 26;
                }
                plaintext.append(lowerCaseAlphabet.charAt(newVal));
            }
            if (Character.isUpperCase(c)) {
                int charPosition = upperCaseAlphabet.indexOf(c);
                int newVal = (aInverse * (charPosition - b)) % 26;
                if(newVal < 0) {
                    newVal += 26;
                }
                plaintext.append(upperCaseAlphabet.charAt(newVal));
            }
        }
        return plaintext.toString();

    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("a = ");
        int a = reader.nextInt();
        System.out.println("b = ");
        int b = reader.nextInt();
        reader.nextLine();
        System.out.println("The plaintext is: ");
        String ciphertext = reader.nextLine();
            System.out.println("The encryption of the plaintext is: " + Decryption(a, b, ciphertext));

    }
}
