import strategy.CelsiusStrategy;
import strategy.FahrenheitStrategy;
import strategy.MasseinheitStrategy;
import strategy.Temperature;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        LandFileReader landFileReader = new LandFileReader();
        ArrayList<Land> lands = landFileReader.getCountries("laender.txt");
        System.out.println(lands);

        Streams streams = new Streams();
        streams.saveToFile(lands);

        Stadt cluj = new Stadt("Cluj", "Rumanien");
        Stadt madrid = new Stadt("Madrid", "Spanien");
        Stadt napoli = new Stadt("Napoli", "Italien");

        Temperatur t1 = new Temperatur(5, "Celsius", 3);
        Temperatur t2 = new Temperatur(25, "Celsius", 8);
        Temperatur t3 = new Temperatur(31, "Celsius", 8);
        Temperatur t4 = new Temperatur(1, "Celsius", 1);
        Temperatur t5 = new Temperatur(17, "Celsius", 5);

        StadtTemperatures stadtTemperatures = new StadtTemperatures();
        stadtTemperatures.setTemperature(cluj, t2);
        stadtTemperatures.setTemperature(cluj, t1);


        Temperature temperature = new Temperature(32, 8);
        temperature.setStrategy(new CelsiusStrategy());
        temperature.displayWert();
        temperature.setStrategy(new FahrenheitStrategy());
        temperature.displayWert();


    }
}