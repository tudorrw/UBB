public class Main {
    public static void main(String[] args) {

        int[] n = { 5, 9 };
        int[] multiply = LargeNumbers.divide(n, 0);
        for(int i: multiply) {
            System.out.print(i + " ");
        }
    }
}