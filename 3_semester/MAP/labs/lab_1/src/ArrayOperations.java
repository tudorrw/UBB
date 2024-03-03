public class ArrayOperations {
    public static int max_number(int[] intArr) throws IllegalAccessException {
        int length = intArr.length;
        if (length == 0) {
            throw new IllegalAccessException("Empty array!");
        }

        int max = 0;
        for(int i: intArr) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public static int sum_min(int[] intArr) {
        int sum = 0;
        int max = 0;
        for(int i: intArr) {
            if(i > max) {
                max = i;
            }
            sum += i;
        }
        return sum - max;
    }

    public static int min_number(int[] intArr) throws IllegalAccessException {
        int length = intArr.length;
        if (length == 0) {
            throw new IllegalAccessException("Empty array!");
        }

        int min = Integer.MAX_VALUE;
        for(int i: intArr) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    public static int sum_max(int[] intArr) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for(int i: intArr) {
            if(i < min) {
                min = i;
            }
            sum += i;
        }
        return sum - min;
    }
}

