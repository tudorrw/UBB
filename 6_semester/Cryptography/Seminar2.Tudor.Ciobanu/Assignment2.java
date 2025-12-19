import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Assignment2 {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //just first 5 letters, suppose that we will find something
    private static final String[] COMMON_MONOGRAMS = {
            "E", "T", "A", "O", "I", "N"
    };

    // Digram frequency (most common pairs)
    private static final String[] COMMON_DIGRAMS = {
            "TH", "HE", "AN", "IN", "ER", "RE"
    };

    // Trigram frequency (most common triplets)
    private static final String[] COMMON_TRIGRAMS = {
            "THE", "ING", "AND", "ION", "TIO", "ENT"
    };
    private static Map<Character, Long> sortedMonogramFrequency(String cipherText) {
        //Calculate the frequency of each character in the ciphertext
        Map<Character, Long> characterFrequency = cipherText.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //Sort the characters by frequency
        return characterFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static Map<String, Integer> sortedDigramFrequency(String text) {
        Map<String, Integer> freq = new HashMap<>();
        for (int i = 0; i < text.length() - 1; i++) {
            String digram = text.substring(i, i + 2);
            freq.put(digram, freq.getOrDefault(digram, 0) + 1);
        }
        return freq.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(12)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static Map<String, Integer> sortedTrigramFrequency(String text) {
        Map<String, Integer> freq = new HashMap<>();
        for (int i = 0; i < text.length() - 2; i++) {
            String trigram = text.substring(i, i + 3);
            freq.put(trigram, freq.getOrDefault(trigram, 0) + 1);
        }
        return freq.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(12)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private static int modInverse(int a) {
        a %= 26;
        for (int x = 1; x < 26; x++) {
            if ((a * x) % 26 == 1)
                return x;
        }
        return -1;
    }
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

//for R(17): E(4) and for D(3): T(19)


//    E(p1) = y1 = a * p1 + b (mod 26) -> 4a + b = 17 (mod 26)
//    E(p2) = y2 = a * p2 + b (mod 26) -> 19a + b =3 (mod 26)
//    (y1 - y2) = a(p1 - p2) (mod 26) -> 17 - 3 = a(4 - 19) (mod 26) -> 14 = 11a (mod 26)
//    a = (y1 - y2) * inverse(p1 - p2) mod 26
    private static Integer calculateA(int y1, int y2, int p1, int p2) {
        int dx = ((p1 - p2) % 26 + 26) % 26;
        int dy = ((y1 - y2) % 26 + 26) % 26;
//        System.out.println("dx: " + dx + " dy: " + dy);
//        System.out.println("Mod inv"+ modInverse(dx));
        return (dy * modInverse(dx) + 26) % 26;
    }

    private static Integer calculateAForTrigrams(int y1, int y2, int y3, int p1, int p2, int p3) {
        int dx = ((2*p1 - p2 - p3) % 26 + 26) % 26;
        int dy = ((2*y1 - y2 - y3) % 26 + 26) % 26;
//        System.out.println("dx: " + dx + " dy: " + dy);
//        System.out.println("Mod inv"+ modInverse(dx));
        return (dy * modInverse(dx) + 26) % 26;
    }

    private static String Decryption(int a, int b, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        int aInverse = modInverse(a);
        for (int cnt = 0; cnt < ciphertext.length(); cnt++) {
            int charPosition = ALPHABET.indexOf(ciphertext.charAt(cnt));
            int newVal = (aInverse * (charPosition - b)) % 26;
                if(newVal < 0) {
                    newVal += 26;
                }
                plaintext.append(ALPHABET.charAt(newVal));
        }
        return plaintext.toString();
    }

    private static boolean HackAffineMonogram(String cipherText) {
        Scanner scanner = new Scanner(System.in);
        //Calculate the frequency of each character in the ciphertext
        Map<Character, Long> sortedCharacterFrequency = sortedMonogramFrequency(cipherText);
        System.out.println("Sorted character frequency: " + sortedCharacterFrequency);
        List<Character> cipherTop = new ArrayList<>(sortedCharacterFrequency.keySet());
        for (int x1 = 0; x1 < COMMON_MONOGRAMS.length - 1; x1++) {
            System.out.println("first common letter: " + COMMON_MONOGRAMS[x1]);
            for (int x2 = x1 + 1; x2 < COMMON_MONOGRAMS.length; x2++) {
                System.out.println("second common letter: " + COMMON_MONOGRAMS[x2]);
                for (int i = 0; i < cipherTop.size(); i++) {
                    System.out.println("letter 1: " + cipherTop.get(i));
                    for (int j = i + 1; j < cipherTop.size(); j++) {
                        System.out.println("letter 2: " + cipherTop.get(j));
                        int y1 = ALPHABET.indexOf(cipherTop.get(i));
                        int y2 = ALPHABET.indexOf(cipherTop.get(j));
                        int p1 = ALPHABET.indexOf(COMMON_MONOGRAMS[x1]);
                        int p2 = ALPHABET.indexOf(COMMON_MONOGRAMS[x2]);

                        if (p1 == p2 || y1 == y2) continue;
                        int a = calculateA(y1, y2, p1, p2);
                        System.out.println("a: " + a);
                        if (a == -1 || gcd(a, 26) != 1) continue;
                        int b = (y1 - a * p1 + 26 * 26) % 26;
                        String decryptedText = Decryption(a, b, cipherText);
                        System.out.println("Candidate decryption found:");
                        System.out.println("a = " + a + ", b = " + b);
                        System.out.println("Plaintext: " + decryptedText);
                        System.out.print("Are you satisfied with this decryption? (yes/no/q-quit): ");

                        String response = scanner.nextLine().trim().toLowerCase();
                        if (response.equals("yes")) {
                            System.out.println("Decryption confirmed!");
                            return true;
                        }
                        if (response.equals("q")) return false;
                    }
                }
            }
        }
        return false;
    }

    private static boolean HackAffineDigram(String cipherText) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> topDigrams = sortedDigramFrequency(cipherText);
        List<String> topDigramList = new ArrayList<>(topDigrams.keySet());
        System.out.println("Top digrams: " + topDigrams);

        for (String pDig : COMMON_DIGRAMS) {
            System.out.println("common digram: " + pDig);
            for (String cDig : topDigramList) {
                System.out.println("cipher digram: " + cDig);
                int y1 = ALPHABET.indexOf(cDig.charAt(0));
                int y2 = ALPHABET.indexOf(cDig.charAt(1));
                int p1 = ALPHABET.indexOf(pDig.charAt(0));
                int p2 = ALPHABET.indexOf(pDig.charAt(1));

                int a = calculateA(y1, y2, p1, p2);
                if (a == -1 || gcd(a, 26) != 1) continue;
                int b = (y1 - a * p1 + 26 * 26) % 26;

                String decryptedText = Decryption(a, b, cipherText);
                System.out.println("Candidate decryption (digram):");
                System.out.println("a = " + a + ", b = " + b);
                System.out.println("Plaintext: " + decryptedText);
                System.out.print("Are you satisfied with this decryption? (yes/no/q): ");

                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    System.out.println("Decryption confirmed!");
                    return true;
                }
                if (response.equals("q")) return false;
            }
        }
        return false;
    }

    private static boolean HackAffineTrigram(String cipherText) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> topTrigrams = sortedTrigramFrequency(cipherText);
        List<String> topTrigramList = new ArrayList<>(topTrigrams.keySet());
        System.out.println("Top trigrams: " + topTrigrams);

        for (String pDig : COMMON_TRIGRAMS) {
            System.out.println("common trigram: " + pDig);
            for (String cDig : topTrigramList) {
                System.out.println("cipher trigram: " + cDig);
                int y1 = ALPHABET.indexOf(cDig.charAt(0));
                int y2 = ALPHABET.indexOf(cDig.charAt(1));
                int y3 = ALPHABET.indexOf(cDig.charAt(2));
                int p1 = ALPHABET.indexOf(pDig.charAt(0));
                int p2 = ALPHABET.indexOf(pDig.charAt(1));
                int p3 = ALPHABET.indexOf(pDig.charAt(2));

                int a = calculateAForTrigrams(y1, y2, y3, p1, p2, p3);
                if (a == -1 || gcd(a, 26) != 1) continue;
                int b = (y1 - a * p1 + 26 * 26) % 26;

                String decryptedText = Decryption(a, b, cipherText);
                System.out.println("Candidate decryption (trigram):");
                System.out.println("a = " + a + ", b = " + b);
                System.out.println("Plaintext: " + decryptedText);
                System.out.print("Are you satisfied with this decryption? (yes/no/q): ");
                String response = scanner.nextLine().trim().toLowerCase();
               if (response.equals("yes")){
                   System.out.println("Decryption confirmed!");
                   return true;
               }
               if (response.equals("q")) return false;
            }
        }
        return false;
    }


    public static void HackAffine(String cipherText) {
        Scanner scanner = new Scanner(System.in);
        boolean found = false;

        while (true) {
            System.out.println("Choose method (1 = monogram, 2 = digram, 3 = trigram, q = quit): ");
            String method = scanner.nextLine().trim().toLowerCase();
            if(method.equals("1")) {
                found = HackAffineMonogram(cipherText);
                break;
            } else if(method.equals("2")) {
                found = HackAffineDigram(cipherText);
                break;
            } else if(method.equals("3")) {
                found = HackAffineTrigram(cipherText);
                break;
            } else if(method.equals("q")) {
                System.out.println("Quitting...");
                return;
            } else {
                System.out.println("Invalid method. Try again.");
            }
        }
        if(!found) {
            System.out.println("No decryption found.");
        }
    }

    public static void main(String[] args) {
        //   QOZFZFNCHNSFHRZCPUFTNBXZGGBFHQNSZCWQOHYGPZCQHIQ
//        FMXVEDKAPHFERBNDKRXRSREFMORUDSDKDVSHVUFEDKAPRKDLYEVLRHHRH
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ciphertext: ");
        String cipherText = scanner.nextLine().toUpperCase();
        HackAffine(cipherText);
    }
}
