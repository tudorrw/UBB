import java.util.List;

public class MenuItem {
    private int item_id;
    private String name;
    private Category category;

    private double price;

    private boolean availability;

    public MenuItem (int item_id, String name) {
        this.item_id = item_id;
        this.name = name;
    }
    public MenuItem(int item_id, String name, Category category, double price, boolean availability) {
        this.item_id = item_id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.availability = availability;
    }

    public static MenuItem parseString(String row) {
        List<String> attributes = List.of(row.split("-"));
        return new MenuItem (
                Integer.parseInt(attributes.get(0)),
                attributes.get(1),
                Category.valueOf(attributes.get(2)),
                Double.parseDouble(attributes.get(3)),
                Boolean.parseBoolean(attributes.get(4))
        );
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "item_id=" + item_id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }


}
