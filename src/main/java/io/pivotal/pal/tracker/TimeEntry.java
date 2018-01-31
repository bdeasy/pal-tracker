package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Locale;

public class TimeEntry {
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this(1L, projectId, userId, date, hours);
    }


    public TimeEntry() {
        this(1L, 0, 0, LocalDate.now(), 0);
    }

    public TimeEntry(TimeEntry timeEntry) {
        this(timeEntry.getId(), timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeEntry) {
            TimeEntry te = (TimeEntry) obj;
            return te.id == id &&
                    te.projectId == projectId &&
                    te.userId == userId &&
                    te.date.equals(date) &&
                    te.hours == hours;
        } else {
            return false;
        }
    }
}
