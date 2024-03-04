import java.util.List;

public class TrainEntry {
    private int train_number;
    private String destination;
    private String departure_time;
    private String platform;

    private String status;

    public TrainEntry(int train_number, String destination, String departure_time, String platform, String status) {
        this.train_number = train_number;
        this.destination = destination;
        this.departure_time = departure_time;
        this.platform = platform;
        this.status = status;
    }

    public static TrainEntry parseString(String row) {
        List<String> attributes = List.of(row.split(","));
        return new TrainEntry (
                Integer.parseInt(attributes.get(0)),
                attributes.get(1),
                attributes.get(2),
                attributes.get(3),
                attributes.get(4)
        );
    }
    @Override
    public String toString() {
        return "TrainEntry{" +
                "train_number=" + train_number +
                ", destination='" + destination + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", platform='" + platform + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getTrain_number() {
        return train_number;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getPlatform() {
        return platform;
    }

    public String getStatus() {
        return status;
    }
}
