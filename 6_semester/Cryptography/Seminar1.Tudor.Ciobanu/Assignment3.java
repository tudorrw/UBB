import java.util.Scanner;

public class Assignment3 {
    public static final String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private static Integer gcd(int x, int y) {
        int temp = 1;
        for(int i = 1; i <= x && i <= y; i++) {
            if(x % i == 0 && y % i == 0) {
                temp = i;
            }
        }
        return temp;
    }

    // Encryption function affine cipher
    private static String Encryption(int c11, int c12, int c21, int c22, String plaintext) {
        if (gcd((c11*c22 - c12*c21) % 26, 26) != 1) {
            System.out.println("det(K) and 26 are not coprime.");
            return "";
        }
        if(plaintext.length() % 2 != 0) {
            plaintext += "z";
        }

        StringBuilder ciphertext = new StringBuilder();
        for(int cnt = 0; cnt < plaintext.length(); cnt += 2) {
            char char1 = plaintext.charAt(cnt);
            char char2 = plaintext.charAt(cnt + 1);
            int char1Position = lowerCaseAlphabet.indexOf(char1);
            int char2Position = lowerCaseAlphabet.indexOf(char2);

            int newVal1 = (char1Position * c11 + char2Position * c21) % 26;
            int newVal2 = (char1Position * c12 + char2Position * c22) % 26;
            if (Character.isUpperCase(char1)) {
                ciphertext.append(upperCaseAlphabet.charAt(newVal1));
            } else {
                ciphertext.append(lowerCaseAlphabet.charAt(newVal1));
            }

            if (Character.isUpperCase(char2)) {
                ciphertext.append(upperCaseAlphabet.charAt(newVal2));
            } else {
                ciphertext.append(lowerCaseAlphabet.charAt(newVal2));
            }
        }
        return ciphertext.toString();
    }
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Giving inputs for matrix K");
        System.out.println("c11 = ");
        int c11 = reader.nextInt();
        System.out.println("c12 = ");
        int c12 = reader.nextInt();
        System.out.println("c21 = ");
        int c21 = reader.nextInt();
        System.out.println("c22 = ");
        int c22 = reader.nextInt();
        reader.nextLine();
        System.out.println("The plaintext is: ");
        String plaintext = reader.nextLine();
        System.out.println("The encryption of the plaintext is: " + Encryption(c11, c12, c21, c22, plaintext));

    }
}
