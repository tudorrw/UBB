import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ElGamal {
    // Fast modular exponentiation for (base^exponent) % modulus
    public static long squareAndMultiply(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return result;
    }

    // Extended Euclidean algorithm to compute modular inverse
    public static long modInverse(long a, long m) {
        long m0 = m, y = 0, x = 1;
        if (m == 1) return 0;

        while (a > 1) {
            long q = a / m, t = m;

            // Euclid's algorithm
            m = a % m; a = t; t = y;

            // Update x and y
            y = x - q * y; x = t;
        }

        // Make x positive
        if (x < 0) x += m0;
        return x;
    }

    // Verify beta value
    public static long verify_beta(long alpha, long a, long p) {
        return squareAndMultiply(alpha, a, p);
    }

    // Sign message with ElGamal
    public static SignedMessage Sign_ElGamal(long p, long alpha, long a, String message, long k) {
        // Calculate a hash of the message
        long messageHash = calculateMessageHash(message, p);

        // Calculate r = alpha^k mod p
        long gamma = squareAndMultiply(alpha, k, p);

        // Calculate s = (m - a*r) * k^(-1) mod (p-1)
        long kInverse = modInverse(k, p - 1);
        long t = (messageHash - a * gamma)  % (p - 1);
        if (t < 0) t += p - 1;
        long delta = (t * kInverse) % (p - 1);

        return new SignedMessage(messageHash, gamma, delta, message);
    }

    // Calculate a simple hash of the message that fits in a long
    private static long calculateMessageHash(String message, long p) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        long hash = 0;

        for (byte b : bytes) {
            System.out.println("b = " + b);
            //Shift left 5 bits, % (p-1) to keep it in range [0, p-1]
            hash = ((hash << 5) - hash + (b & 0xFF)) % (p - 1);
            System.out.println("hash = " + hash);
            if (hash < 0) hash += (p - 1); // Ensure positive value
        }

        return hash;
    }

    // Verify signature
    public static boolean verifySignature(long p, long alpha, long beta, long r, long s, long messageHash) {
        // Check if r is in the range [1, p-1]
        if (r < 1 || r >= p) {
            return false;
        }

        // Left side: beta^r * r^s mod p
        long v1 = (squareAndMultiply(beta, r, p) * squareAndMultiply(r, s, p)) % p;

        // Right side: alpha^m mod p
        long v2 = squareAndMultiply(alpha, messageHash, p);

        return v1 == v2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Given parameters from assignment
//        long p = 107, alpha = 122503, a = 10;
        long p = 467, alpha = 2, a = 127;

        // Verify beta
        long beta = verify_beta(alpha, a, p);
        System.out.println("Beta = " + beta);

        // Get message and k from user input
        System.out.print("Enter the message to sign: ");
        String message = scanner.nextLine();
//        String message = "tudor";

        System.out.print("Enter the value of k (a number used for signing): ");
//        long k = scanner.nextLong();
        long k = 213;
        // Sign message
        SignedMessage signed = Sign_ElGamal(p, alpha, a, message, k);
        System.out.println("Message: \"" + signed.originalMessage + "\"");
        System.out.println("Message Hash: " + signed.messageHash);
        System.out.println("Signature (r, s): (" + signed.gamma + ", " + signed.delta + ")");

        // Verify signature
        boolean isValid = verifySignature(p, alpha, beta, signed.gamma, signed.delta, signed.messageHash);
        System.out.println("Signature valid: " + isValid);

        scanner.close();
    }

    // Inner class to hold signed message
    public static class SignedMessage {
        public final long messageHash;
        public final long gamma;
        public final long delta;
        public final String originalMessage;

        public SignedMessage(long messageHash, long r, long s, String originalMessage) {
            this.messageHash = messageHash;
            this.gamma = r;
            this.delta = s;
            this.originalMessage = originalMessage;
        }
    }
}
