package com.fantasyfrc.server;

import com.fantasyfrc.draft.Draft;

import java.awt.*;
import java.io.IOException;
import java.util.jar.JarEntry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/employeeTask")
public class EmployeeTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeeTaskServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher //
                = this.getServletContext()//
                .getRequestDispatcher("/WEB-INF/views/employeeTaskView.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Draft d = (Draft) request.getSession().getAttribute("activeDraft");

        System.out.println(d.getId());
        //TODO Prune input and check current user
        System.out.println(request.getParameter("pick" + (d.getLastPick() + 2)));
        if(!d.makePick("test", request.getParameter("pick" + (d.getLastPick() + 2)))){
            //TODO Make error message
            //TODO Make error message specific
            System.out.println("Error");
        }
        doGet(request, response);
    }

}
