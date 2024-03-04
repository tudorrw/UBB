import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Streams {
    public void saveToFile(ArrayList<Land> lands) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("filterLaender.csv"));
        lands.stream()
                .filter(land -> land.getName().startsWith("M") && land.getFlaeche() > 10000)
                .map(Land::toString)
                .forEach(land -> {
                    try {
                        bw.write(land);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        bw.close();
    }
}
