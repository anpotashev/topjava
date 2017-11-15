package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.AppContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private ConfigurableApplicationContext ctx;
    private AdminRestController adminRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ctx = AppContext.APP_CONTEXT.getContextInstance();
        adminRestController = ctx.getBean(AdminRestController.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("loginForm")) {
            request.setAttribute("users", adminRestController.getAll());
            request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
            return;
        }
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        response.sendRedirect("meals");
    }

}
