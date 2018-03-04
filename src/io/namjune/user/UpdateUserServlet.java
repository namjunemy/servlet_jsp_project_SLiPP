package io.namjune.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String email = request.getParameter("email");

    User user = new User(userId, password, name, email);
    UserDao userDao = new UserDao();
    try {
      userDao.updateUser(user);
    } catch (Exception e) {
    }
    response.sendRedirect("/");
  }
}