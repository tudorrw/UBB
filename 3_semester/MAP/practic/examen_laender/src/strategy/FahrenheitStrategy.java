package strategy;

public class FahrenheitStrategy implements MasseinheitStrategy {
    @Override
    public void display(double wert) {
        double fahrenheit = wert * 1.8 + 32;
        System.out.println(String.format("%.2f F", fahrenheit));
    }
}
