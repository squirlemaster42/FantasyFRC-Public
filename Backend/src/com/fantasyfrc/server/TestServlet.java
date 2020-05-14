package com.fantasyfrc.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(5);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Hello, Welcome to the site.");
    }
}
