public class ElectronicShop {
    public static int CheapestKeyboard(int[] keyboardPrices) {
        int min = Integer.MAX_VALUE;
        for(int i: keyboardPrices) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }
    public static int MostExpensiveItem(int[] keyboardPrices, int[] usbPrices) {
        int max = Integer.MIN_VALUE;
        for(int i: keyboardPrices) {
            if (i > max) {
                max = i;
            }
        }

        for(int i: usbPrices) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public static int BuyMostExpensiveUSBWithBudget(int[] usbPrices, int budget) {
        int max = Integer.MIN_VALUE;
        for(int i: usbPrices) {
            if (i < budget && max < i) {
                max = i;
            }
        }
        return max;
    }
    public static int BuyKeyboardAndUsb(int[] keyboardPrices, int[] usbPrices, int budget) {
        int maxPrice = -1;
        for (int keyboardPrice : keyboardPrices) {
            for (int usbPrice : usbPrices) {
                int total = keyboardPrice + usbPrice;
                if (total <= budget && total > maxPrice) {
                    maxPrice = total;
                }
            }
        }
        return maxPrice;
    }
}

