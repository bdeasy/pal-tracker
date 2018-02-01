package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.List;

@RequestMapping("/time-entries")
@RestController
public class TimeEntryController {
    private final CounterService counterService;
    private final GaugeService gaugeService;
    private final TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository,
                               CounterService counter,
                               GaugeService gaugeService) {
        this.repository = repository;
        this.counterService = counter;
        this.gaugeService = gaugeService;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry request) {
        TimeEntry createdTimeEntry = repository.create(request);
        counterService.increment("TimeEntry.created");
        gaugeService.submit("timeEntries.count", repository.list().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeEntry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
        TimeEntry readEntry = repository.find(id);

        if (readEntry == null) {
            return ResponseEntity.notFound().build();
        } else {
            counterService.increment("TimeEntry.read");
            return ResponseEntity.ok(repository.find(id));
        }
    }


    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {
        counterService.increment("TimeEntry.listed");

        return ResponseEntity.ok(repository.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry request) {
        TimeEntry entry = repository.update(id, request);

        if (entry == null) {
            return ResponseEntity.notFound().build();
        } else {
            counterService.increment("TimeEntry.updated");
            return ResponseEntity.ok(repository.update(id, request));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        repository.delete(id);
        counterService.increment("TimeEntry.deleted");
        gaugeService.submit("timeEntries.count", repository.list().size());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
