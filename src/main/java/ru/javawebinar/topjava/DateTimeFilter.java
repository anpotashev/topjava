package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
public class DateTimeFilter {

    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MAX;
    private LocalDate startDate = null;
    private LocalDate endDate = null;

    public DateTimeFilter() {
        log.debug("NEW DateTimeFilter");
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartTime(LocalTime startTime) {
        if(startTime == null)
            this.startTime = LocalTime.MIN;
        else
            this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime == null)
            this.endTime = LocalTime.MAX;
        else
            this.endTime = endTime;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public void setStartDate(String line) {
        setStartDate(line != null && line.length()>0 ? LocalDate.parse(line, DateTimeUtil.DATE_FORMATTER) : null);
    }

    public void setEndDate(String line) {
        setEndDate(line != null && line.length()>0 ? LocalDate.parse(line, DateTimeUtil.DATE_FORMATTER) : null);
    }

    public void setStartTime(String line) {
        setStartTime(line != null && line.length()>0 ? LocalTime.parse(line, DateTimeUtil.TIME_FORMATTER) : null);
    }

    public void setEndTime(String line) {
        setEndTime(line != null && line.length()>0 ? LocalTime.parse(line, DateTimeUtil.TIME_FORMATTER) : null);
    }
}
