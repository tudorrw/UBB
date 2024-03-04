package strategy;

public class CelsiusStrategy implements MasseinheitStrategy {
    @Override
    public void display(double wert) {
        System.out.println(String.format("%.2f C", wert));
    }
}
