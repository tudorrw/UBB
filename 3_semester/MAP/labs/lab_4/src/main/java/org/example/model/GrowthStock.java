package org.example.model;

public class GrowthStock extends Stock{
    private int growth_rate;

    public GrowthStock(int id, String name, Company company, Market market, int growth_rate) {
        super(id, name, company, market);
        this.growth_rate = growth_rate;
    }

    public int getGrowth_rate() {
        return growth_rate;
    }

    public void setGrowth_rate(int growth_rate) {
        this.growth_rate = growth_rate;
    }
}

