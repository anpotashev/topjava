package ru.javawebinar.topjava.web;

import lombok.extern.slf4j.Slf4j;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MealDAOInMemoryImpl;
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

    private static final int DEFAULT_CALORIES_PER_DAY = 2000;
    private DAO<Meal> mealDAO;
    private static int defalutCaloriesPerDay = DEFAULT_CALORIES_PER_DAY;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void init() {
        mealDAO = MealDAOInMemoryImpl.getDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("mealServler->doGet");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        action = action==null ? "" : action;
        switch (action) {
            case "resetToDefault":
                log.debug("reset to default");
                resetToDefault(request, response);
                break;
            case "delete":
                log.debug("delete");
                delete(request, response);
                break;
            case "create":
                log.debug("delete");
                create(request);
//                break;
            case "edit":
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

//        log.debug("datetime {} value {}", dateTime1 == null, (String) dateTime1);
//        log.debug("description {} value {}", description1 == null, (String) description1);
//        log.debug("calories {} value {}", calories1 == null, (String) calories1);
//        log.debug("id {} value {}", id1 == null, (String) id1);

        if (dateTime1 != null && description1 != null && calories1 != null) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse((String) dateTime1, formatter);
                String description = (String) description1;
                Integer calories = Integer.parseInt((String) calories1);
                Long id = id1==null ? null : Long.parseLong((String) id1);

                Meal meal = new Meal(dateTime, description, calories);
                if (id != null) {
                    meal.setId(id);
                }
                mealDAO.merge(meal);
            } catch (DateTimeParseException | NumberFormatException ex) {
                response.sendRedirect("meals?errorSaving=true");
                return;
            }
        }
        response.sendRedirect("meals");
    }

    private void resetToDefault(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MealDAOInMemoryImpl.resetToDefault();
        defalutCaloriesPerDay = DEFAULT_CALORIES_PER_DAY;
        response.sendRedirect("meals");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object id = request.getParameter("id");
        if (id != null) {
            Long mealId = Long.parseLong((String) request.getParameter("id"));
            mealDAO.delete(mealId);
        }
        response.sendRedirect("meals");
    }

    private void create(HttpServletRequest request) {
        request.setAttribute("newMeal", true);
    }

    private void edit(HttpServletRequest request) {
        Object id = request.getParameter("id");
        Meal meal = null;
        if (id != null) {
            Long mealId = Long.parseLong((String) request.getParameter("id"));
            meal = mealDAO.findById(mealId);
            request.setAttribute("meal", meal);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object calories = request.getParameter("caloriesPerDay");
        int caloriesPerDay;
        try {
            caloriesPerDay = (calories == null) ? defalutCaloriesPerDay : Integer.parseInt((String) calories);
        } catch (NumberFormatException ex) {
            caloriesPerDay = DEFAULT_CALORIES_PER_DAY;
        }
        defalutCaloriesPerDay = caloriesPerDay;
        log.debug("caloriesPerDay = {}", caloriesPerDay);

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
