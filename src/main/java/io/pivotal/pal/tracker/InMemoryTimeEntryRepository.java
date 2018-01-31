package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> entries = new HashMap<>();
    private AtomicLong sequence = new AtomicLong();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long id = sequence.incrementAndGet();

        return create(id, timeEntry);
    }

    @Override
    public TimeEntry find(long id) {
        return entries.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(entries.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry foundEntry = find(id);

        if (foundEntry == null) {
            return null;
        } else {
            delete(id);

            return create(id, timeEntry);
        }
    }

    @Override
    public TimeEntry delete(long id) {
        return entries.remove(id);
    }

    private TimeEntry create(long id, TimeEntry timeEntry) {
        TimeEntry created = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

        entries.put(id, created);

        return created;
    }
}
