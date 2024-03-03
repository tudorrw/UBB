import java.util.Arrays;

public class LargeNumbers {
    public static int[] add(int[] n1, int[] n2) {
        int length = n1.length;
        int[] newNum = new int[length + 1];
        int carry = 0;
        for(int i = length - 1; i >= 0; i--) {
            int s = n1[i] + n2[i] + carry;
            newNum[i+1] = s % 10;
            carry = s / 10;
        }

        if(carry != 0) {
            newNum[0] = carry;
            return newNum;
        }
        return Arrays.copyOfRange(newNum, 1, length + 1);
    }

    public static int[] subtract(int[] n1, int[] n2) {
        int length = n1.length;
        int[] newNum = new int[length];
        int borrow = 0;
        for(int i = length - 1; i >= 0; i--) {
            int diff = n1[i] - n2[i] - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            }
            else {
                borrow = 0;
            }
            newNum[i] = diff;
        }
        int leadingZeros = 0;
        while(newNum[leadingZeros] == 0 && leadingZeros < length - 1) {
            leadingZeros++;
        }
        if(leadingZeros == length - 1) {
            return new int[] {0};
        }
        return Arrays.copyOfRange(newNum, leadingZeros, length);
    }

    public static int[] multiply(int[] n, int digit) {
        if (digit == 0) {
            return new int[] {0};
        }
        int length = n.length;
        int[] newNum = new int[length + 1];
        int carry = 0;
        for(int i = length - 1; i >= 0; i--) {
            int m = n[i] * digit + carry;
            newNum[i+1] = m % 10;
            carry = m / 10;
        }
        if(carry != 0) {
            newNum[0] = carry;
            return newNum;
        }
        return Arrays.copyOfRange(newNum, 1, length + 1);
    }

    public static int[] divide(int[] n, int digit) {
        try {
            int length = n.length;
            int[] newNum = new int[length];
            int borrow = 0;
            for(int i = 0; i < length; i++) {
                int currentVal = n[i] + borrow * 10;
                newNum[i] = currentVal / digit;
                borrow = currentVal % digit;
            }
            if (newNum[0] != 0) {
                newNum[0] = n[0] / digit;
                return newNum;
            }
            return Arrays.copyOfRange(newNum, 1, length);
        } catch (ArithmeticException e) {
            return new int[] {-1};
        }

    }
}
