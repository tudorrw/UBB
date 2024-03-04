import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StadtTemperatures {
    private Map<String, Stadt> cities;

    public StadtTemperatures() {
        this.cities = new HashMap<>();
    }

    public void setTemperature(Stadt stadt, Temperatur temperatur) {
        if(!cities.containsKey(stadt.getName())) {
            cities.put(stadt.getName(), stadt);
        } else {
            Stadt stadt1 = cities.get(stadt.getName());
            System.out.println(stadt.getName());
            stadt1.addTemperature(temperatur);
        }
    }


}

