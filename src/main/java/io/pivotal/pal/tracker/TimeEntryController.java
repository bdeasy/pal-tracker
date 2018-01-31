package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.List;

@RequestMapping("/time-entries")
@RestController
public class TimeEntryController {

    private final TimeEntryRepository repository;

    public TimeEntryController(@Autowired TimeEntryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
        TimeEntry readEntry = repository.find(id);

        if (readEntry == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(repository.find(id));
        }
    }


    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(repository.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody TimeEntry request) {
        TimeEntry entry = repository.update(id, request);

        if (entry == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(repository.update(id, request));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        repository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
