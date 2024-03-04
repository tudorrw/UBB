import java.util.ArrayList;
import java.util.List;

public class Stadt {
    private String name;
    private String land;
    private List<Temperatur> temperatures;

    public Stadt(String name, String land) {
        this.name = name;
        this.land = land;
        this.temperatures = new ArrayList<>();
    }

    public void addTemperature(Temperatur temperatur) {
        if(!temperatures.isEmpty()) {
            for(Temperatur t: temperatures) {
                if(t.getMonat() == temperatur.getMonat()) {
                    System.out.println("Es gibt bereits eine Temperatur fur den ausgewahlten Monat!");
                    return;
                }
            }
        }
        if(temperatur.getMonat() >= 1 && temperatur.getMonat() <= 12) {
            this.temperatures.add(temperatur);
        }
        else {
            System.out.println("Der Monat stellt keine gültige Zahl dar!");
        }
    }

    public void updateTemperature(Temperatur temperatur) {
        if(!temperatures.isEmpty()) {
            for(Temperatur t: temperatures) {
                if(t.getMonat() == temperatur.getMonat() && t.getMasseinheit().equals(temperatur.getMasseinheit())) {
                    t.setWert(temperatur.getWert());
                    System.out.println("Die Temperatur wurde aktualisiert!");
                    return;
                }
            }
        }
    }


    public String getName() {
        return name;
    }

    public String getLand() {
        return land;
    }

    public List<Temperatur> getTemperatures() {
        return temperatures;
    }
}
