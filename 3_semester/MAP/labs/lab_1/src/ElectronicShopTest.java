import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElectronicShopTest {
    public ElectronicShop eShop;
    public int expected, budget;
    public int[] keyboardPrices, usbPrices;

    @BeforeEach public void setup() {
        eShop = new ElectronicShop();
    }

    @Test
    public void cheapestKeyboard() {
        keyboardPrices = new int[] { 40, 35, 70, 15, 45 };
        expected = 15;
        assertEquals(expected, eShop.CheapestKeyboard(keyboardPrices));
    }

    @Test
    public void mostExpensiveItem() {
        keyboardPrices = new int[] { 15, 20, 10, 35 };
        usbPrices = new int[] { 20, 15, 40, 15 };
        expected = 40;
        assertEquals(expected, eShop.MostExpensiveItem(keyboardPrices, usbPrices));
    }

    @Test
    public void buyMostExpensiveUSBWithBudget() {
        usbPrices = new int[] { 15, 45, 20 };
        budget = 30;
        expected = 20;
        assertEquals(expected, eShop.BuyMostExpensiveUSBWithBudget(usbPrices, budget));
    }
    @Test
    public void buyKeyboardAndUsb() {
        keyboardPrices = new int[] {40, 50, 60};
        usbPrices = new int[] { 8, 12 };
        expected = 58;
        budget = 60;
        assertEquals(expected, eShop.BuyKeyboardAndUsb(keyboardPrices, usbPrices, budget));
    }
}