package dbms.concurrency.backend.javaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/java-concurrency")
public class BandController {
    @Autowired
    private BandService bandService;

    @PostMapping("/dirty-read")
    public ResponseEntity<String> dirty_read(@RequestParam Integer bandId) throws InterruptedException {
        Optional<Band> bandBeforeTransaction = bandService.getBandById(bandId);
        if (bandBeforeTransaction.isEmpty()) {
            return ResponseEntity.ok("band not found");
        }
        String before = "Before Transaction: " + (bandBeforeTransaction.isPresent() ? bandBeforeTransaction.get().toString() : "band not found");
        System.out.println(before);

        AtomicReference<String> during = new AtomicReference<>(); // AtomicReference to hold during transaction value
        // Simulate dirty read
        Thread updateQuery = new Thread(() -> {
            bandService.callDirtyRead(bandId);
        });

        Thread selectQuery = new Thread(() -> {
            try {
                Band bandInTransaction = bandService.dirty_read(bandId);
                during.set("During Transaction: " + (bandInTransaction != null ? bandInTransaction.toString() : "band not found"));
                System.out.println(during.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        updateQuery.start();
        selectQuery.start();

        // Wait for threads to complete
        updateQuery.join();
        selectQuery.join();

        Thread.sleep(2000);
//        entityManager.clear();
        Optional<Band> bandAfterTransaction = bandService.getBandById(bandId);
        String after = "After Transaction: " + (bandAfterTransaction.isPresent() ? bandAfterTransaction.get().toString() : "band not found");
        System.out.println(after);

        String response = "{ \"before\": \"" + before + "\", \"during\": \"" + during.get() + "\", \"after\": \"" + after + "\" }";

        return ResponseEntity.ok(response);
    }
}
