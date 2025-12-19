import java.util.Scanner;

public class RSA_Hack {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static long[] egcd(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        } else {
            long[] ret = egcd(b, a % b);
            long tmp = ret[1] - ret[2] * (a / b);
            ret[1] = ret[2];
            ret[2] = tmp;
            return ret;
        }
    }
    private static int[] splitCiphertext(String cipherText) {
        String[] parts = cipherText.split("\\s*,\\s*");
        int[] cipher = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            cipher[i] = Integer.parseInt(parts[i]);
        }
        return cipher;
    }
    private static long[] factorizeN (int n) {
        int p = 1, q = 1;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                p = i;
                q = n / i;
                break;
            }
        }
        return new long[]{p, q};
    }

    public static long modInverse(long e, long phi) {
        long[] res = egcd(e, phi);
        if (res[0] != 1) return -1;
        long inv = res[1] % phi;
        return inv < 0 ? inv + phi : inv;
    }

    private static String toBinary(long exponent) {
        StringBuilder binaryNumber = new StringBuilder();
        while (exponent > 0) {
            binaryNumber.insert(0, exponent % 2);
            exponent /= 2;
        }
        return binaryNumber.toString();
    }

    private static long squareAndMultiply(long base, String binExp, long mod) {
        long result = base % mod;
        for (int i = 1; i < binExp.length(); i++) {
            // square
            result = (result * result) % mod;
            // multiply
            if (binExp.charAt(i) == '1') {
                result = (result * (base % mod)) % mod;
            }
        }
        return result;
    }


    public static String HackRSA(int n, int b, String cipherText) {
        // split the ciphertext into array of integers
        int[] cipher = splitCiphertext(cipherText);
        // factor n into p and q
        long[] factorize = factorizeN(n);
        long phi = (factorize[0] - 1) * (factorize[1] - 1);
        long a = modInverse(b, phi);

        StringBuilder pt = new StringBuilder();
        for (int j : cipher) {
            long m = squareAndMultiply(j, toBinary(a), n);
            pt.append(ALPHABET.charAt((int) m));
        }
        return pt.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

//        // read public key
//        System.out.print("Enter n: ");
//        int n = sc.nextInt();
//        System.out.print("Enter b: ");
//        int b = sc.nextInt();
//        sc.nextLine();
//        //read ciphertext
//        System.out.print("Enter ciphertext: ");
//        String cipherText = sc.nextLine().trim();
//
        int n = 18721, b = 25;
        String cipherText = "365, 0, 4845, 14930, 2608, 2608, 0";

        //split the ciphertext into array of integers

        System.out.println(HackRSA(n, b, cipherText));

    }
}
