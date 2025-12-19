import java.util.SortedMap;

public class DES {
    // Initial Permutation
    private static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Final Permutation (IP^-1)
    private static final int[] FP = {
            40,  8, 48, 16, 56, 24, 64, 32,
            39,  7, 47, 15, 55, 23, 63, 31,
            38,  6, 46, 14, 54, 22, 62, 30,
            37,  5, 45, 13, 53, 21, 61, 29,
            36,  4, 44, 12, 52, 20, 60, 28,
            35,  3, 43, 11, 51, 19, 59, 27,
            34,  2, 42, 10, 50, 18, 58, 26,
            33,  1, 41,  9, 49, 17, 57, 25
    };

    // Expansion table
    private static final int[] E = {
            32,  1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };
    // S-boxes

    private static final int[][][] S_BOX = {
            {   // S1
                    {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                    {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
                    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
            },
            {   // S2
                    {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
                    {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
                    {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
                    {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
            },
            {   // S3
                    {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
                    {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
                    {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
                    {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
            },
            {   // S4
                    {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
                    {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
                    {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
                    {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
            },
            {   // S5
                    {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
                    {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
                    {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
                    {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
            },
            {   // S6
                    {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
                    {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
                    {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
                    {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
            },
            {   // S7
                    {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
                    {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
                    {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
                    {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
            },
            {   // S8
                    {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
                    {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
                    {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
                    {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
            }
    };

    private static final int[] P = {
            16,  7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2,  8, 24, 14,
            32, 27,  3,  9,
            19, 13, 30,  6,
            22, 11,  4, 25
    };

    // PC1 (Key Permutation 1) - 56 bits
    private static final int[] PC1 = {
            57,49,41,33,25,17,9,
            1,58,50,42,34,26,18,
            10, 2,59,51,43,35,27,
            19,11, 3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14, 6,61,53,45,37,29,
            21,13, 5,28,20,12, 4
    };

    // PC2 (Key Permutation 2) - 48 bits
    private static final int[] PC2 = {
            14,17,11,24, 1, 5,
            3,28,15, 6,21,10,
            23,19,12, 4,26, 8,
            16, 7,27,20,13, 2,
            41,52,31,37,47,55,
            30,40,51,45,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32
    };

    // Number of left shifts for each round
    private static final int[] SHIFTS = {
            1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1 // 1 is for 1, 2, 9, 16
    };

    private static String[] roundKeys = new String[16];


    // Helper: circular left shift for a binary string
    private static String shiftLeft(String half, int n) {
        return half.substring(n) + half.substring(0, n);
    }

//    Create 16 subkeys, each of which is 48-bits long.
    public static void generateKeys(String key64) {
        //  From the original 64-bit key we get the 56-bit permutation
        StringBuilder permutedKey = new StringBuilder();
        for (int i = 0; i < 56; i++) {
            permutedKey.append(key64.charAt(PC1[i] - 1));
        }
        // Split into left (28 bits) and right (28 bits)
        String left = permutedKey.substring(0, 28);
        String right = permutedKey.substring(28);

        // Generate 16 round keys
        for (int round = 0; round < 16; round++) {
            left = shiftLeft(left, SHIFTS[round]);
            right = shiftLeft(right, SHIFTS[round]);
            String combined = left + right;
            StringBuilder roundKey = new StringBuilder();
            for (int i = 0; i < 48; i++) {
                roundKey.append(combined.charAt(PC2[i] - 1));
            }
            roundKeys[round] = roundKey.toString();
//            System.out.println("Round Key " + (round + 1) + ": " + roundKeys[round]);
        }
    }


    private static String textToHex(String text) {
        StringBuilder hex = new StringBuilder();
        for (char c : text.toCharArray()) {
            hex.append(String.format("%02X", (int)c));
        }
        return hex.toString();
    }

    private static String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    // Convert a binary string to a hex string (each 4 bits becomes one hex char)
    private static String binToHex(String bin) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bin.length(); i += 4) {
            String nibble = bin.substring(i, i + 4);
            int val = Integer.parseInt(nibble, 2);
            hex.append(Integer.toHexString(val).toUpperCase());
        }
        return hex.toString();
    }

    // ------------------------------
    // 3) DES Block Encryption
    // ------------------------------
    public static String encryptBlock(String block64) {
        // block64: 64-bit binary string plaintext block

        // Initial Permutation (IP)
        String permutedBlock = permute(block64, IP);

        // Split into left (L) and right (R) halves
        String left = permutedBlock.substring(0, 32);
        String right = permutedBlock.substring(32);

        // 16 rounds of DES
        for (int round = 0; round < 16; round++) {
            // Expansion: expand R from 32 to 48 bits using table E
            String expandedRight = permute(right, E);
            // XOR with round key
            String xorResult = xor(expandedRight, roundKeys[round]);
            // S-box substitution: compress 48 bits to 32 bits
            String sboxOutput = sboxSubstitution(xorResult);
            // Permutation (P-box)
            String pboxOutput = permute(sboxOutput, P);
            // XOR with left half
            String newRight = xor(left, pboxOutput);

            // For next round: new left is current R, new R is result
            left = right;
            right = newRight;

            // (Optional) Print round details for debugging:
            System.out.println("\n--- Round " + (round + 1) + " ---");
            System.out.println("Round Key : " + roundKeys[round]);
            System.out.println("L: " + left);
            System.out.println("R: " + right);
        }

        // After 16 rounds, swap L and R and apply Final Permutation (FP)
        String combined = right + left;
        //cipherblock returned
        return permute(combined, FP);
    }

    // Helper: Permute a binary string using a given table
    private static String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            output.append(input.charAt(table[i] - 1));
        }
        return output.toString();
    }

    // Helper: XOR two binary strings of equal length
    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    // Helper: S-box substitution, input is 48 bits, output is 32 bits
    private static String sboxSubstitution(String input48) {
        StringBuilder output32 = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String sixBits = input48.substring(i * 6, i * 6 + 6);
            // Row: first and last bit
            int row = Integer.parseInt("" + sixBits.charAt(0) + sixBits.charAt(5), 2);
            // Column: middle 4 bits
            int col = Integer.parseInt(sixBits.substring(1, 5), 2);
            int val = S_BOX[i][row][col];
            String fourBits = String.format("%4s", Integer.toBinaryString(val)).replace(' ', '0');
            output32.append(fourBits);
        }
        return output32.toString();
    }




    public static String encryptText(String plaintext, String key) {
        // Convert plaintext to hex (each character → 2 hex digits)
        String plaintextHex = textToHex(plaintext);
        System.out.println("Plaintext (hex): " + plaintextHex);

        // Generate DES keys from the key (converted to binary)
        generateKeys(hexToBin(key));
        int remainder = plaintextHex.length() % 16;
        if (remainder != 0) {
            int padLen = 16 - remainder;
            for (int i = 0; i < padLen; i++) {
                plaintextHex += "0";
            }
        }
        System.out.println("Plaintext (hex): " + plaintextHex);

        // Process each 64-bit (16 hex digit) block
        System.out.println(plaintextHex.length());
        StringBuilder cipherHex = new StringBuilder();
        for (int i = 0; i < plaintextHex.length(); i += 16) {
            String blockHex = plaintextHex.substring(i, i + 16);
            String blockBin = hexToBin(blockHex);
            String cipherBlockBin = encryptBlock(blockBin);
            cipherHex.append(binToHex(cipherBlockBin));
        }
        return cipherHex.toString();
    }




    public static void main(String[] args) {
        String plaintext = "DES algoritm is better than classical algorithms";
//        String plaintext = "Your lips are smoother than vaseline";
        System.out.println(textToHex(plaintext));
        String key = "3B3898371520F75E";
//        String key = "0E329232EA6D0D73";
//        0011101100111000100110000011011100010101001000001111011101011110

        String encrypted = encryptText(plaintext, key);
        // Plaintext as provided:
        System.out.println("Ciphertext (hex): " + encrypted);
    }
}
