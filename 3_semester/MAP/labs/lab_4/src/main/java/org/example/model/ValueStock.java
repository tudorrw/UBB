package org.example.model;

public class ValueStock extends Stock{
    private double dividend_rate;

    public ValueStock(int id, String name, Company company, Market market, double dividend_rate) {
        super(id, name, company, market);
        this.dividend_rate = dividend_rate;
    }

    public double getDividend_rate() {
        return dividend_rate;
    }

    public void setDividend_rate(double dividend_rate) {
        this.dividend_rate = dividend_rate;
    }
}
