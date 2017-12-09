package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
//@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        List<MealWithExceed> meals = mealController.getAll();
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/add")
    public String addMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

//    @GetMapping("/add/{id}")
    @GetMapping("/edit/")
    public String editMeal(Model model, @PathVariable int id) {
        Meal meal = mealController.get(id);
        model.addAttribute("meal", meal);
        return "redirect:/meals";
//        return "mealForm";
    }

//    @GetMapping("/delete/{id}")
    @RequestMapping(value = "meals/delete/{id}", method = RequestMethod.POST)
    public String delMeal(@PathVariable int id ) {
        mealController.delete(id);
        return "redirect:/meals";
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String save(
//            @RequestParam(name = "id", required = false) Integer id
//                       , @RequestParam(name = "dateTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
//                       , @RequestParam(name = "description", required = true) String description
//                       , @RequestParam(name = "calories", required = true) int calories
//                       ) {
//        System.out.println(dateTime);
//        Meal meal = new Meal(dateTime, description, calories);
//        if (id != null) {
//            mealController.update(meal, id);
//        } else{
//            mealController.create(meal);
//        }
//        return "redirect:/meals";
//    }
}
