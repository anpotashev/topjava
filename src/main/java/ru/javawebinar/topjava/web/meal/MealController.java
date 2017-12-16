package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/meals")
public class MealController extends AbstractMealController {

    @GetMapping
    public String getMeals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping("/filter")
    public String filter(Model model
            , @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
            , @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
            , @RequestParam(name = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime
            , @RequestParam(name = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime
    ) {
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/edit/{id}")
    public String editMeal(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delMeal(@PathVariable(name = "id") Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam(name = "id", required = false) Integer id
            , @RequestParam(name = "dateTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
            , @RequestParam(name = "description", required = true) String description
            , @RequestParam(name = "calories", required = true) int calories
    ) {
        Meal meal = new Meal(dateTime, description, calories);
        if (id != null) {
            super.update(meal, id);
        } else {
            super.create(meal);
        }
        return "redirect:/meals";
    }
}
