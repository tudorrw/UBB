import java.util.List;

public class Animal {
    private String name;
    private String species;
    private int age;
    private Enclosure_type enclosure_type;
    private Health_status health_status;


    public Animal(String name, String species, int age, Enclosure_type enclosure_type, Health_status health_status) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.enclosure_type = enclosure_type;
        this.health_status = health_status;
    }
    public static Animal parseString(String row) {
        List<String> attributes = List.of(row.split(","));
        return new Animal(
                attributes.get(0),
                attributes.get(1),
                Integer.parseInt(attributes.get(2)),
                Enclosure_type.valueOf(attributes.get(3)),
                Health_status.valueOf(attributes.get(4))
        );
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", age=" + age +
                ", enclosure_type=" + enclosure_type +
                ", health_status=" + health_status +
                '}';
    }

    public void setHealth_status(Health_status health_status) {
        this.health_status = health_status;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public Enclosure_type getEnclosure_type() {
        return enclosure_type;
    }

    public Health_status getHealth_status() {
        return health_status;
    }
}
