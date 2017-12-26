package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.formatter.ALocalDate;
import ru.javawebinar.topjava.web.formatter.ALocalTime;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create1(@RequestBody Meal meal) {
        Meal meal1 = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(meal1.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(meal1);
//        return super.create(meal);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @GetMapping(value = "/filter")
    public List<MealWithExceed> getBetween(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime endDateTime) {
        return super.getBetween(
                startDateTime.toLocalDate()
                , startDateTime.toLocalTime()
                , endDateTime.toLocalDate()
                , endDateTime.toLocalTime()
        );
    }

    @GetMapping(value = "/filter2")
    public List<MealWithExceed> getBetween(
            @RequestParam(value = "startDate") @ALocalDate java.time.LocalDate startDate
            , @RequestParam(value = "startTime") @ALocalTime java.time.LocalTime startTime
            , @RequestParam(value = "endDate") @ALocalDate java.time.LocalDate endDate
            , @RequestParam(value = "endTime") @ALocalTime java.time.LocalTime endTime
    ) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}