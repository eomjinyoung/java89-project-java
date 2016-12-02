package bitcamp.java89.ems.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bitcamp.java89.ems.server.annotation.Component;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.util.DataSource;
import bitcamp.java89.ems.server.vo.Contact;

@Component // ApplicationContext가 관리하는 클래스임을 표시하기 위해 태그를 단다.
public class ContactMysqlDao implements ContactDao {
  DataSource ds;
  
  public void setDataSource(DataSource dataSource) {
    this.ds = dataSource;
  }

  public ArrayList<Contact> getList() throws Exception {
    ArrayList<Contact> list = new ArrayList<>();
    Connection con = ds.getConnection(); // 커넥션풀에서 한 개의 Connection 객체를 임대한다.
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select posi, name, tel, email from ex_contacts");
      ResultSet rs = stmt.executeQuery(); ){
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Contact contact = new Contact(); 
        contact.setName(rs.getString("name"));
        contact.setPosition(rs.getString("posi"));
        contact.setTel(rs.getString("tel"));
        contact.setEmail(rs.getString("email"));
        list.add(contact);
      }
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public ArrayList<Contact> getListByName(String name) throws Exception {
    ArrayList<Contact> list = new ArrayList<>();
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select posi, name, tel, email from ex_contacts where name=?"); ) {
      
      stmt.setString(1, name);
      ResultSet rs = stmt.executeQuery();
      
      while (rs.next()) { // 서버에서 레코드 한 개를 가져왔다면,
        Contact contact = new Contact(); 
        contact.setName(rs.getString("name"));
        contact.setPosition(rs.getString("posi"));
        contact.setTel(rs.getString("tel"));
        contact.setEmail(rs.getString("email"));
        list.add(contact);
      }
      
      rs.close();
    } finally {
      ds.returnConnection(con);
    }
    return list;
  }
  
  public void insert(Contact contact) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "insert into ex_contacts(name,email,tel,posi) values(?,?,?,?)"); ) {
      
      stmt.setString(1, contact.getName());
      stmt.setString(2, contact.getEmail());
      stmt.setString(3, contact.getTel());
      stmt.setString(4, contact.getPosition());
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    }
  }
  
  public void update(Contact contact) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "update ex_contacts set name=?, tel=?, posi=? where email=?"); ) {
      
      stmt.setString(1, contact.getName());
      stmt.setString(2, contact.getTel());
      stmt.setString(3, contact.getPosition());
      stmt.setString(4, contact.getEmail());
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    } 
  }
  
  public void delete(String email) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "delete from ex_contacts where email=?"); ) {
      
      stmt.setString(1, email);
      
      stmt.executeUpdate();
    } finally {
      ds.returnConnection(con);
    } 
  }
  
  public boolean existEmail(String email) throws Exception {
    Connection con = ds.getConnection();
    try (
      PreparedStatement stmt = con.prepareStatement(
          "select * from ex_contacts where email=?"); ) {
      
      stmt.setString(1, email);
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
