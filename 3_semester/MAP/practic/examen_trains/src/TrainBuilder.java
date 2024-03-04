public class TrainBuilder {
    private int train_number;
    private String destination;
    private String departure_time;
    private String platform;
    private String status;

    public TrainBuilder train_number(int train_number) {
        this.train_number = train_number;
        return this;
    }

    public TrainBuilder destination(String destination) {
        this.destination = destination;
        return this;
    }

    public TrainBuilder departure_time(String departure_time) {
        this.departure_time = departure_time;
        return this;


    }

    public TrainBuilder platform(String platform) {
        this.platform = platform;
        return this;
    }

    public TrainBuilder status(String status) {
        this.status = status;
        return this;

    }
    public Train build() {
        return new Train(train_number, destination, departure_time, platform, status);
    }
}
