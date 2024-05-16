package dbms.concurrency.backend.javaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
            bandService.dirtyReadPython(bandId);
        });

        Thread selectQuery = new Thread(() -> {
            try {
                Band bandInTransaction = bandService.dirtyRead(bandId);
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
        System.out.println("-----------------------------------------------");

        String response = "{ \"before\": \"" + before + "\", \"during\": \"" + during.get() + "\", \"after\": \"" + after + "\" }";

        return ResponseEntity.ok(response);
    }


    @PostMapping("/lost-update")
    public ResponseEntity<String> lost_update(@RequestParam Integer bandId) throws InterruptedException {
        Optional<Band> bandBeforeTransaction = bandService.getBandById(bandId);
        if (bandBeforeTransaction.isEmpty()) {
            return ResponseEntity.ok("band not found");
        }
        String name = bandBeforeTransaction.get().getName();
        String before = "Before Transaction: " + (bandBeforeTransaction.isPresent() ? bandBeforeTransaction.get().toString() : "band not found");
        System.out.println(before);
        Thread updateFromPython = new Thread(() -> bandService.lostUpdatePython(bandId));
        Thread updateFromJava = new Thread(() -> {
            try {
                bandService.lostUpdate(bandId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        updateFromJava.start();
        updateFromPython.start();

        updateFromPython.join();
        updateFromJava.join();

        Optional<Band> bandAfterTransaction = bandService.getBandById(bandId);
        String after = "After Transaction: " + (bandAfterTransaction.isPresent() ? bandAfterTransaction.get().toString() : "band not found");
        bandService.updateBandNameById(bandId, name);
        System.out.println(after);
        System.out.println("-----------------------------------------------");
        String response = "{ \"before\": \"" + before + "\", \"after\": \"" + after + "\" }";
        return ResponseEntity.ok(response);
    }
    @PostMapping("/unrepetable-reads")
    public ResponseEntity<String> unrepetable_read(@RequestParam Integer bandId) throws InterruptedException {
        Optional<Band> bandBeforeTransaction = bandService.getBandById(bandId);
        if (bandBeforeTransaction.isEmpty()) {
            return ResponseEntity.ok("band not found");
        }
        String name = bandBeforeTransaction.get().getName();

        Thread updateThread = new Thread(() -> bandService.unrepetableReadPython(bandId));
        AtomicReference<Map<String, String>> result = new AtomicReference<>();
        Thread readThread = new Thread(() -> {
            try {
                result.set(bandService.unrepeatableRead(bandId));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        readThread.start();
        updateThread.start();

        updateThread.join();
        readThread.join();

        bandService.updateBandNameById(bandId, name);
        System.out.println("-----------------------------------------------");
        return ResponseEntity.ok(String.valueOf(result));
    }

    @PostMapping("/dirty-write")
    public ResponseEntity<String> dirtyWrite(@RequestParam Integer bandId) throws InterruptedException {
        Optional<Band> bandBeforeTransaction = bandService.getBandById(bandId);
        if (bandBeforeTransaction.isEmpty()) {
            return ResponseEntity.ok("Band not found");
        }
        String name = bandBeforeTransaction.get().getName();
        String before = "Before Transaction: " + (bandBeforeTransaction.isPresent() ? bandBeforeTransaction.get().toString() : "Band not found");
        System.out.println(before);

        Thread updateFromPython = new Thread(() -> bandService.dirtyWritePython(bandId));
        Thread updateFromJava = new Thread(() -> {
            try {
                bandService.dirtyWrite(bandId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        updateFromPython.start();
        updateFromJava.start();

        updateFromPython.join();
        updateFromJava.join();

        Optional<Band> bandAfterTransaction = bandService.getBandById(bandId);
        String after = "After Transaction: " + (bandAfterTransaction.isPresent() ? bandAfterTransaction.get().toString() : "Band not found");
        bandService.updateBandNameById(bandId, name);
        System.out.println(after);
        System.out.println("-----------------------------------------------");
        String response = "{ \"before\": \"" + before + "\", \"after\": \"" + after + "\" }";
        return ResponseEntity.ok(response);
    }


    @PostMapping("/phantom-read")
    public ResponseEntity<String> phantomRead() throws InterruptedException {
        List<Band> beforeTransaction = bandService.getAllBands();
        String before = "Before Transaction: " + beforeTransaction.size();
//        System.out.println(before);
//        for (Band band : beforeTransaction) {
//            System.out.println(band.toString());
//        }
//

        Thread insertPython = new Thread(() -> bandService.phantomReadPython());
        Thread selectsJava = new Thread(() -> {
            try {
                bandService.phantomRead();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        selectsJava.start();
        insertPython.start();

        selectsJava.join();
        insertPython.join();


        List<Band> afterTransaction = bandService.getAllBands();
        String after = "After Transaction: " + afterTransaction.size();
//        for (Band band : afterTransaction) {
//            System.out.println(band.toString());
//        }
        System.out.println("-----------------------------------------------");
        String response = "{ \"before\": \"" + before + "\", \"after\": \"" + after + "\" }";
        return ResponseEntity.ok(response);
    }
}
