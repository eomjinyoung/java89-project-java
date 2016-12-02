package bitcamp.java89.ems.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.dao.StudentDao;
import bitcamp.java89.ems.server.util.DataSource;
import bitcamp.java89.ems.server.vo.Student;

@Component // ApplicationContext가 관리하는 클래스임을 표시하기 위해 태그를 단다.
public class StudentMysqlDao implements StudentDao {
  DataSource ds;
  
  public void setDataSource(DataSource dataSource) {
    this.ds = dataSource;
  }
  
  public ArrayList<Student> getList() throws Exception {
    ArrayList<Student> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select uid, pwd, name, tel, email, work, byear, schl from ex_students");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Student student = new Student();
        student.setUserId(rs.getString("uid"));
        student.setPassword(rs.getString("pwd"));
        student.setName(rs.getString("name"));
        student.setTel(rs.getString("tel"));
        student.setEmail(rs.getString("email"));
        student.setWorking(rs.getString("work").equals("Y") ? true : false);
        student.setBirthYear(rs.getInt("byear"));
        student.setSchool(rs.getString("schl"));
        list.add(student);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public Student getOne(String userId) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select uid, pwd, name, tel, email, work, byear, schl "
          + " from ex_students"
          + " where uid=?");) {

      stmt.setString(1, userId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Student student = new Student();
        student.setUserId(rs.getString("uid"));
        student.setPassword(rs.getString("pwd"));
        student.setName(rs.getString("name"));
        student.setTel(rs.getString("tel"));
        student.setEmail(rs.getString("email"));
        student.setWorking(rs.getString("work").equals("Y") ? true : false);
        student.setBirthYear(rs.getInt("byear"));
        student.setSchool(rs.getString("schl"));
        rs.close();
        return student;
        
      } else {
        rs.close();
        return null;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void insert(Student student) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into ex_students(uid,pwd,name,tel,email,work,byear,schl) "
          + " values(?,?,?,?,?,?,?,?)"); ) {
      
      stmt.setString(1, student.getUserId());
      stmt.setString(2, student.getPassword());
      stmt.setString(3, student.getName());
      stmt.setString(4, student.getTel());
      stmt.setString(5, student.getEmail());
      stmt.setString(6, student.isWorking() ? "Y" : "NO");
      stmt.setInt(7, student.getBirthYear());
      stmt.setString(8, student.getSchool());
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Student student) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update ex_students set "
          + " pwd=?,name=?,tel=?,email=?,work=?,byear=?,schl=?"
          + " where uid=?"); ) {
      
      stmt.setString(1, student.getPassword());
      stmt.setString(2, student.getName());
      stmt.setString(3, student.getTel());
      stmt.setString(4, student.getEmail());
      stmt.setString(5, student.isWorking() ? "Y" : "NO");
      stmt.setInt(6, student.getBirthYear());
      stmt.setString(7, student.getSchool());
      stmt.setString(8, student.getUserId());
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void delete(String userId) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from ex_students where uid=?"); ) {
      
      stmt.setString(1, userId);
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public boolean existUserId(String userId) throws Exception {
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select * from ex_students where uid=?"); ) {
      
      stmt.setString(1, userId);
      ResultSet rs = stmt.executeQuery();
      
      if (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        rs.close();
        return true;
      } else {
        rs.close();
        return false;
      }
    } finally {
      ds.returnConnection(con);
    }
  }
}







