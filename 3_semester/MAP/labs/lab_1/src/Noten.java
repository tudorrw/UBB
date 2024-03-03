public class Noten {
    public static int[] insufficient_grades(int[] notenArr) {
        int[] temp = new int[notenArr.length];
        int cnt = 0;
        for (int j : notenArr) {
            if (j < 40) {
                temp[cnt++] = j;
            }
        }
        int[] arr = new int[cnt];
        System.arraycopy(temp, 0, arr, 0, cnt);
        return arr;
    }

    public static double average_value(int[] notenArr) {
        double sum = 0;
        for (int j : notenArr) {
            sum += j;
        }
        return sum / notenArr.length;
    }
        public static int rounded_grade(int note) {
            if (note >= 38) {
                int rounded = (note / 5 + 1) * 5;
                if (rounded - note < 3) {
                    return rounded;
                }
            }
            return note;
        }

        public static int[] rounded_grades(int[] notenArr) {
            int length = notenArr.length;
            int[] temp = new int[length];

            int cnt = 0;
            for (int j : notenArr) {
                int rounded = rounded_grade(j);
                if (rounded != j) {
                    temp[cnt++] = rounded;
                }
            }
            int[] arr = new int[cnt];
            System.arraycopy(temp, 0, arr, 0, cnt);
            return arr;
        }
    public static int max_rounded_grade(int[] notenArr) {
        int max = Integer.MIN_VALUE;
        for (int j : notenArr) {
            int rounded = rounded_grade(j);
            if (rounded > max && rounded != j) {
                max = rounded;
            }
        }
        return max;
    }
}
