import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MenuItemStreams {

    public void displayCategoriesAndNumberOfItems(ArrayList<MenuItem> menuItems) {
         menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getCategory, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> a.getValue().compareTo(b.getValue()) * -1)
                 .forEach(System.out::println);
    }

    public void saveToFile(ArrayList<MenuItem> menuItems) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("unavailable_items.txt"));
        menuItems.stream()
                .filter(menuItem -> !menuItem.isAvailability())
                .map(menuItem -> menuItem.getName().length())
                .forEach(menuItem -> {
                    try {
                        bw.write(menuItem);
                        bw.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        bw.close();
    }
}














