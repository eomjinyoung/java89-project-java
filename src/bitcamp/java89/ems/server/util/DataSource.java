package bitcamp.java89.ems.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;

@Component
public class DataSource {
  ArrayList<Connection> conPool = new ArrayList<>();

  public DataSource() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() throws Exception {
    if (conPool.size() == 0) {
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/java89db", 
          "java89", "1111");
    } else {
      return conPool.remove(0);
    }
  }

  public void returnConnection(Connection con) {
    try {
      if (!con.isClosed() && con.isValid(5)) {
        conPool.add(con);
      }
    } catch (Exception e) {}
  }
}






