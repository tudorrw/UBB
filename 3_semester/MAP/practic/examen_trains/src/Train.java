public class Train {
    private final int train_number;
    private final String destination;
    private final String departure_time;
    private final String platform;
    private final String status;

     Train(int train_number, String destination, String departure_time, String platform, String status) {
        this.train_number = train_number;
        this.destination = destination;
        this.departure_time = departure_time;
        this.platform = platform;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Train{" +
                "train_number=" + train_number +
                ", destination='" + destination + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", platform='" + platform + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void ignition() {
         if( departure_time == null || destination == null || platform == null || status == null) {
            throw  new IllegalStateException("Train not completely configured!");
         }
        System.out.println("Train " + train_number + " is completely configured");

    }
}
