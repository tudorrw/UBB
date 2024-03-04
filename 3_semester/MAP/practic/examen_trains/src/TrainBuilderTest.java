public class TrainBuilderTest {
    public static void main(String[] args) {
        Train train = new TrainBuilder()
                .train_number(1)
                .destination("Cluj-Napoca")
                .departure_time("7:40 PM")
                .platform("Platform 2")
                .status("Delayed")
                .build();
        System.out.println(train);
        try {
            train.ignition();
        } catch (IllegalStateException e) {
            System.out.println("Execution caught!");
        }


    }

}
