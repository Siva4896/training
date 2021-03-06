package com.objectfrontier.training.java.jdbc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        // Must set the content type first
        res.setContentType("text/html");

        // Now obtain a PrintWriter to insert HTML into
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>" + "Hello World!" + "</title></head>");
        out.println("<body><h1>Welcome..!!</h1></body></html>");
    }
}
