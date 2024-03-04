public class Temperatur {
    private double wert;
    private String masseinheit;
    private int monat;

    public Temperatur(double wert, String masseinheit, int monat) {
        this.wert = wert;
        this.masseinheit = masseinheit;
        this.monat = monat;
    }

    public double getWert() {
        return wert;
    }

    public String getMasseinheit() {
        return masseinheit;
    }

    public int getMonat() {
        return monat;
    }

    public void setWert(double wert) {
        this.wert = wert;
    }
}
