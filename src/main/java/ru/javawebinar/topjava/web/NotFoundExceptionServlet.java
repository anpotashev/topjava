package ru.javawebinar.topjava.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class NotFoundExceptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {

        String attributeName;
        Enumeration<String> attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            attributeName = attrs.nextElement();
            log.debug("{} - {}", attributeName, request.getAttribute(attributeName));
        }
        Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("errorMessage", ex.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
