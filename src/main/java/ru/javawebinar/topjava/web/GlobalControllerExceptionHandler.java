package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.aop.DuplicateFieldException;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.UserUtil.asTo;
import static ru.javawebinar.topjava.util.ValidationUtil.getRootCause;

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


    @ExceptionHandler({DuplicateFieldException.class})
    public ModelAndView duplicateFieldExceptionHandler(HttpServletRequest req, DuplicateFieldException ex) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserTo userTo = (ex.getWrongData() instanceof UserTo) ? (UserTo) ex.getWrongData() : asTo((User) ex.getWrongData());
        modelAndView.addObject("userTo", userTo);
        modelAndView.addObject("register", ex.isCreating());
        modelAndView.addObject("hasDuplicateError", true);
        modelAndView.addObject("duplicateErrorMessage", messageSource.getMessage(ex.getMsgCode(), null, req.getLocale()));
        return modelAndView;
    }

}
