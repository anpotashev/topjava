package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.formatter.LocalDateFormatter;
import ru.javawebinar.topjava.web.formatter.LocalTimeFormatter;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String URL = MealRestController.REST_URL + "/";

//    @LocalDateOrLocalTime
    private final LocalDate START_DATE = LocalDate.of(2015, 05, 30);
//    @LocalDateOrLocalTime(type = LocalDateOrLocalTime.Type.TIME)
    private final LocalTime START_TIME = LocalTime.of(9, 0);
//    @LocalDateOrLocalTime
    private final LocalDate END_DATE = LocalDate.of(2015, 05, 30);
//    @LocalDateOrLocalTime(type = LocalDateOrLocalTime.Type.TIME)
    private final LocalTime END_TIME = LocalTime.of(11, 0);

    @Autowired
    private LocalDateFormatter dateFormatter;
    @Autowired
    private LocalTimeFormatter timeFormatter;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEAL1))
                ;
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEALS));
    }

    @Test
    public void createTest() throws Exception {
        Meal newMeal = MealTestData.getCreated();
        mockMvc.perform(
                    post(URL)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(JsonUtil.writeValue(newMeal))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(getCreated()));
        newMeal.setId(NEXT_MEAL_ID);
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, newMeal));
    }

    @Test
    public void updateTest() throws Exception {

        mockMvc.perform(
                    put(URL+getUpdated().getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(getUpdated()))
                )
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(getUpdated(), MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));
    }

    @Test
    public void getBetweenTest() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.of(2015, 05, 30), LocalTime.of(9, 0));
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.of(2015, 05, 30), LocalTime.of(11, 0));
        mockMvc.perform(get(URL+"filter")
                        .param("startDateTime", startDateTime.toString())
                        .param("endDateTime", endDateTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentJson(new Meal[] {MEAL1}))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_FirstTest() throws Exception {
        getBetween(START_DATE, START_TIME, END_DATE, END_TIME)
                .andExpect(contentJson(new Meal[] {MEAL1}))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_StartDateIsNull() throws Exception {
        getBetween(null, START_TIME, END_DATE, END_TIME)
                .andExpect(contentJson(new Meal[] {MEAL1}))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_EndDateIsNull() throws Exception {
        getBetween(START_DATE, START_TIME, null, END_TIME)
                .andExpect(contentJson(new Meal[] {MEAL1, MEAL4}))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_DatesAreNull() throws Exception {
        getBetween(null, START_TIME, null, END_TIME)
                .andExpect(contentJson(MEAL1, MEAL4));
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_StartTimeIsNull() throws Exception {
        getBetween(START_DATE, null, END_DATE, END_TIME)
                .andExpect(contentJson(new Meal[] {MEAL1}))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_END_TimeIsNull() throws Exception {
        getBetween(START_DATE, START_TIME, END_DATE, null)
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3))
        ;
    }

    @Test
    public void getBetweenWithSeparetDateAndTimeParameters_END_TimesAreNull() throws Exception {
        getBetween(START_DATE, null, END_DATE, null)
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3))
        ;
    }

    private ResultActions getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) throws Exception {
        MockHttpServletRequestBuilder builder = get(URL+"filter2");
        addParam(builder, "startDate", startDate);
        addParam(builder, "startTime", startTime);
        addParam(builder, "endDate", endDate);
        addParam(builder, "endTime", endTime);
        return mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                ;
    }

    private MockHttpServletRequestBuilder addParam(MockHttpServletRequestBuilder builder, String paramName, Object param) {
        if (param != null) {
            builder.param(paramName, format(param));
        }
        return builder;
    }

    private String format(Object dateOrTime) {

        if (dateOrTime instanceof LocalDate) {
            LocalDate date = (LocalDate) dateOrTime;
            return dateFormatter.print(date, Locale.getDefault());
        }
        if (dateOrTime instanceof LocalTime) {
            LocalTime time = (LocalTime) dateOrTime;
            return timeFormatter.print(time, Locale.getDefault());
        }
        throw new UnsupportedOperationException("Only localdate or localtime");
    }

}