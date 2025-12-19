import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SplittableRandom;

public class RSA_Encryption {
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

    private static long modInverse(long e, long phi) {
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

    public static String Encryption(int p, int q, int b, String plaintext, int blocklength) {
        long n = (long) p * q;
        // Clean and pad plaintext
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        int pad = (blocklength - (plaintext.length() % blocklength)) % blocklength;
        plaintext += "A".repeat(pad);
        System.out.println("Padded plaintext: " + plaintext);

        List<Long> cipherNums = new ArrayList<>();
        for (int i = 0; i < plaintext.length(); i += blocklength) {
            long m = 0;
            for (int j = 0; j < blocklength; j++) {
                int idx = ALPHABET.indexOf(plaintext.charAt(i + j));
                m = m * 100 + idx;
            }
            long ciph = squareAndMultiply(m, toBinary(b), n);
            cipherNums.add(ciph);
        }
        return String.join(" ", cipherNums.stream().map(Object::toString).toArray(String[]::new));
    }

    public static String Decryption(int p, int q, int a, String cipherText, int blocklength) {
        long n = (long)p * q;
        StringBuilder pt = new StringBuilder();

        for (String tok : cipherText.trim().split("\\s+")) {
            long c = Long.parseLong(tok);
            // 1) square–and–multiply with exponent = a
            long m = squareAndMultiply(c, toBinary(a), n);
            char[] block = new char[blocklength];
            for (int j = blocklength-1; j >= 0; j--) {
                block[j] = ALPHABET.charAt((int)(m % 100) % ALPHABET.length());
                m /= 100;
            }
            pt.append(block);
        }
        return pt.toString();
    }


    public static int calculateBlockLength(long n) {
        int blocklength = 1;
        long maxBlockValue = 100;
        while (maxBlockValue < n) {
            maxBlockValue *= 100;
            blocklength++;
        }
        return blocklength - 1; // we subtract 1 because last multiplication exceeded n
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1) Bob generates his RSA parameters
        int p = 113, q = 101, b = 3533;
        long n   = (long)p * q;
        long phi = (long)(p-1)*(q-1);
//        int blocklength = calculateBlockLength(n);
        System.out.print("Enter blocklength: ");
        int blocklength = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Bob: picked primes p=" + p + ", q=" + q + " and public exponent b=" + b + "and the blocklength=" + blocklength);
        System.out.println("Bob: computed n = p*q = " + n + " and phi(n) = (p-1)(q-1) = " + phi);

        // 2) Bob checks gcd(b, phi) and computes his private exponent a
        long gcd = egcd(b, phi)[0];
        System.out.println("Bob: gcd(b, phi) = gcd(" + b + ", " + phi + ") = " + gcd);
        int a = (int)modInverse(b, phi);
        System.out.println("Bob: a = b^(-1) mod phi = " + a);

        // 3) Bob publishes (n, b)
        System.out.println("Bob → Alice: public key (n=" + n + ", b=" + b + ")\n");

        // 4) Alice encrypts
        System.out.print("Alice: enter plaintext to encrypt: ");
        String pt = scanner.nextLine().trim();
//        String pt = "HOWAREYOU";
        String ct = Encryption(p, q, b, pt, blocklength);
        System.out.println("Alice → Bob: ciphertext = " + ct + "\n");

        // 5) Bob decrypts
        System.out.println("Bob: received ciphertext = " + ct);
        String recovered = Decryption(p, q, a, ct, blocklength);
        System.out.println("Bob: decrypting with a = " + a + " → recovered plaintext = " + recovered);
    }

}
