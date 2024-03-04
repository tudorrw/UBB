package strategy;

public class Temperature {
    private double wert;
    private int monat;
    private MasseinheitStrategy strategy;

    public Temperature(double wert, int monat) {
        this.wert = wert;
        this.monat = monat;
        this.strategy = new CelsiusStrategy();
    }
    public void setStrategy(MasseinheitStrategy masseinheitStrategy) {
        this.strategy = masseinheitStrategy;
    }
    public void displayWert() {
         strategy.display(wert);
    }
}
