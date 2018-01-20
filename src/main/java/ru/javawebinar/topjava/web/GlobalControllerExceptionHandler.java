package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception/exception");
        Throwable rootCause = getRootCause(e);
        mav.addObject("exception", rootCause);
        mav.addObject("message", rootCause.toString());

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView DataIntegrityViolationExceptionHandler(HttpServletRequest req, HttpServletResponse res, DataIntegrityViolationException e) throws Exception {

        Unique_idx idx = parseDataIntegrityViolationExceptionAndReturnUniqueIdx(e);
        ModelAndView modelAndView = new ModelAndView("profile");
        switch(idx) {
            case USER_EMAIL:
                modelAndView.setViewName("profile");
                UserTo userTo = new UserTo();
                userTo.setName(req.getParameter("name"));
                userTo.setEmail(req.getParameter("email"));
                userTo.setCaloriesPerDay(Integer.parseInt(req.getParameter("caloriesPerDay")));
                modelAndView.addObject("userTo", userTo);

                if (req.getRequestURI().equals("/register")) {
                    modelAndView.addObject("register", true);
                }
                break;
            case MEAL_DATETIME:
                break; // meals creating and updating work over ajax
        }

        modelAndView.addObject("hasduplicateerror", true);
        modelAndView.addObject("duplicateErrorMessage", messageSource.getMessage(idx.getMsgCode(), null, req.getLocale()));
        return modelAndView;
    }

}
