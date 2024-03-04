import java.util.List;

public class Land {
    private String name;
    private String kontinent;
    private long flaeche;
    private long bevoelkerung;
    private String hauptstadt;

    public Land(String name, String kontinent, long flaeche, long bevoelkerung, String hauptstadt) {
        this.name = name;
        this.kontinent = kontinent;
        this.flaeche = flaeche;
        this.bevoelkerung = bevoelkerung;
        this.hauptstadt = hauptstadt;
    }
    public static Land parseString(String row) {
        List<String> attributes = List.of(row.split(" "));
        return new Land (
                attributes.get(0),
                attributes.get(1),
                Long.parseLong(attributes.get(2)),
                Long.parseLong(attributes.get(3)),
                attributes.get(4)
        );
    }


    @Override
    public String toString() {
        return name +
                "," + kontinent +
                "," + flaeche +
                "," + bevoelkerung +
                "," + hauptstadt;
    }

    public String getName() {
        return name;
    }

    public String getKontinent() {
        return kontinent;
    }

    public long getFlaeche() {
        return flaeche;
    }

    public long getBevoelkerung() {
        return bevoelkerung;
    }

    public String getHauptstadt() {
        return hauptstadt;
    }
}
