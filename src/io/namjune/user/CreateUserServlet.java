package io.namjune.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.namjune.support.MyValidatorFactory;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {
  static final Logger logger = LoggerFactory.getLogger(CreateUserServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String email = request.getParameter("email");

    User user = new User(userId, password, name, email);

    logger.debug("User : {}", user);

    Validator validator = MyValidatorFactory.createValidator();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
    if (constraintViolations.size() > 0) {
      request.setAttribute("user", user);

      Iterator<ConstraintViolation<User>> violations = constraintViolations.iterator();
      String errorMessage = "";
      while (violations.hasNext()) {
        ConstraintViolation<User> each = violations.next();
        errorMessage += each.getPropertyPath() + " : " + each.getMessage() + " ";
      }

      forwardJSP(request, response, errorMessage);
      return;
    }

    UserDao userDao = new UserDao();
    try {
      userDao.addUser(user);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    response.sendRedirect("/");
  }

  private void forwardJSP(HttpServletRequest request, HttpServletResponse response, String errorMessage)
      throws ServletException, IOException {
    request.setAttribute("errorMessage", errorMessage);
    RequestDispatcher rd = request.getRequestDispatcher("/form.jsp");
    rd.forward(request, response);
  }
}
