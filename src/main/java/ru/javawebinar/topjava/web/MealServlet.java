package ru.javawebinar.topjava.web;

import lombok.extern.slf4j.Slf4j;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOSimpleImpl;
import ru.javawebinar.topjava.dao.MealInMemoryCRUDImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Slf4j
public class MealServlet extends HttpServlet{

    private static MealDAO mealDAO = new MealDAOSimpleImpl();
    private static int defalutCaloriesPerDay = 2000;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("mealServler->doGet");
        String servletUrl = request.getServletPath();
        log.debug(servletUrl);
        switch (servletUrl) {
            case "/resetToDefault":
                log.debug("reset to default");
                resetToDefault(request, response);
                break;
            case "/deleteMeal":
                log.debug("delete");
                delete(request, response);
                break;
            case "/editMeal":
                log.debug("edit");
                edit(request);
            default:
                log.debug("default");
                list(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Object dateTime1 = request.getParameter("datetime");
        Object description1 = request.getParameter("description");
        Object calories1 = request.getParameter("calories");
        Object id1 = request.getParameter("id");
        log.debug("datetime " + new Boolean(dateTime1 == null) + " value " + (String) dateTime1);
        log.debug("description " + new Boolean(description1 == null)+ " value " + (String) description1);
        log.debug("calories " + new Boolean(calories1 == null) + " value " + (String) calories1);
        log.debug("id " + new Boolean(id1 == null) + " value " + (String) id1);
        if (dateTime1 != null && description1 != null && calories1 != null) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse((String) dateTime1, formatter);
                String description = (String) description1;
                Integer calories = Integer.parseInt((String) calories1);
                Long id = null;
                if (id1 != null) {
                    id = Long.parseLong((String) id1);
                }
                if (id != null) {
                    Meal meal = mealDAO.findById(id);
                    meal.setCalories(calories);
                    meal.setDateTime(dateTime);
                    meal.setDescription(description);
                    mealDAO.edit(meal);
                } else {
                    Meal meal = new Meal(dateTime, description, calories);
                    mealDAO.add(meal);
                }
                request.setAttribute("errorSaving", false);
            } catch (DateTimeParseException | NumberFormatException ex) {
                response.sendRedirect("meals?errorSaving=true");
//                request.setAttribute("errorSaving", true);
            }
        }
        response.sendRedirect("meals");
//        doGet(request, response);
    }

    private void resetToDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MealInMemoryCRUDImpl.resetToDefault();
        response.sendRedirect("meals");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object id = request.getParameter("id");
        if (id != null) {
            Long mealId = Long.parseLong((String) request.getParameter("id"));
            mealDAO.delete(mealDAO.findById(mealId));
        }
        response.sendRedirect("meals");
    }

    private void edit(HttpServletRequest request) {
        Object id = request.getParameter("id");
        Meal meal = null;
        if (id != null) {
            Long mealId = Long.parseLong((String) request.getParameter("id"));
            meal = mealDAO.findById(mealId);
        }
        if (meal == null) {
            request.setAttribute("newMeal", true);
        } else {
            request.setAttribute("meal", meal);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object calories = request.getParameter("caloriesPerDay");
        int caloriesPerDay = (calories == null) ? defalutCaloriesPerDay: Integer.parseInt((String) calories);
        defalutCaloriesPerDay = caloriesPerDay;
        log.debug("caloriesPerDay = " + caloriesPerDay);
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDAO.findAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
        request.setAttribute("caloriesPerDay", caloriesPerDay);
        request.setAttribute("mealsWithExceeds", mealWithExceeds);
        request.setAttribute("dateTimeFormatter", formatter);
        if (request.getParameter("errorSaving") != null) {
            request.setAttribute("errorSaving", true);
        }
        getServletContext().getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}
