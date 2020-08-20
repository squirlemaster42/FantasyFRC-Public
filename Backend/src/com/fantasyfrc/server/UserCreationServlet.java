package com.fantasyfrc.server;

import com.fantasyfrc.user.UserAccount;
import com.fantasyfrc.user.UserDAO;
import com.fantasyfrc.user.UserDatabaseManager;
import com.fantasyfrc.utils.AppUtils;
import com.fantasyfrc.utils.PasswordStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userCreation")
public class UserCreationServlet extends HttpServlet {

  public UserCreationServlet(){
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userCreationView.jsp");

    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");

    if(userName == null || "".equals(userName) || password == null || "".equals(password)){
      System.out.println("acknowledged missing input");
      throw new ServletException("Mandatory Parameter missing");
    }

    try {
      password = PasswordStorage.createHash(password.toCharArray());
    } catch (PasswordStorage.CannotPerformOperationException cpoe) {
      System.out.println("Unsupported hashing operation");
      return;
    }

    UserDatabaseManager.getInstance().writeUser(userName, password);

    doGet(request, response);
  }
}
