public class AnimalCareTaker implements AnimalService {
    private final AnimalService animalService;

    public AnimalCareTaker() {
        this.animalService = new AnimalThief();
    }

    @Override
    public void steal() {
        System.out.println("I am not actually an animal care taker...");
        animalService.steal();
    }
}

