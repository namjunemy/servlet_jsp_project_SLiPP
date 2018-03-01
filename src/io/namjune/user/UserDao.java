package io.namjune.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
  public Connection getConnection() {
    String url = "jdbc:mysql://localhost:3306/slipp";
    String id = "njkim";
    String pw = "skawns123";

    try {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager.getConnection(url, id, pw);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public void insert(User user) throws SQLException {
    String sql = "insert into USERS values(?, ?, ?, ?)";
    PreparedStatement pstmt = getConnection().prepareStatement(sql);
    pstmt.setString(1, user.getUserId());
    pstmt.setString(2, user.getPassword());
    pstmt.setString(3, user.getName());
    pstmt.setString(4, user.getEmail());
    pstmt.executeUpdate();
  }
}