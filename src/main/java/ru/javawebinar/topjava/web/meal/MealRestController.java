package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.formatter.LocalDateOrLocalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return super.getBetween(
                startDateTime.toLocalDate()
                , startDateTime.toLocalTime()
                , endDateTime.toLocalDate()
                , endDateTime.toLocalTime()
        );
    }

    @GetMapping(value = "/filter2", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) @LocalDateOrLocalTime LocalDate startDate
            , @RequestParam(value = "startTime", required = false) @LocalDateOrLocalTime(type = LocalDateOrLocalTime.Type.TIME) LocalTime startTime
            , @RequestParam(value = "endDate", required = false) @LocalDateOrLocalTime LocalDate endDate
            , @RequestParam(value = "endTime", required = false) @LocalDateOrLocalTime(type = LocalDateOrLocalTime.Type.TIME) LocalTime endTime
    ) {
        return super.getBetween(
                startDate == null ? DateTimeUtil.MIN_DATE : startDate
                , startTime == null ? LocalTime.MIN : startTime
                , endDate == null ? DateTimeUtil.MAX_DATE : endDate
                , endTime == null ? LocalTime.MAX : endTime
        );
    }
}