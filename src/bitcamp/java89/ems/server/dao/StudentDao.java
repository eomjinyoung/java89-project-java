package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Student;

public class StudentDao {
  static StudentDao obj;
  
  private String filename = "student-v1.8.data";
  private ArrayList<Student> list;

  public static StudentDao getInstance() {
    if (obj == null) {
      obj = new StudentDao();
    }
    
    return obj;
  }
  
  public StudentDao() {
    this.load(); 
  }

  public ArrayList<Student> getList() {
    return this.list;
  }
  
  synchronized public Student getOne(String userId) {
    for (Student student : list) {
      if (student.getUserId().equals(userId)) {
        return student;
      }
    }
    return null;
  }
  
  public void insert(Student student) {
    list.add(student);
    try {this.save();} catch (Exception e) {}
  }
  
  synchronized public void update(Student student) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUserId().equals(student.getUserId())) {
        list.set(i, student);
        try {this.save();} catch (Exception e) {}
        return;
      }
    }
  }
  
  synchronized public void delete(String userId) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUserId().equals(userId)) {
        list.remove(i);
        try {this.save();} catch (Exception e) {}
        return;
      }
    }
  }
  
  public boolean existUserId(String userId) {
    for (Student student : list) {
      if (student.getUserId().toLowerCase().equals(userId)) {
        return true;
      }
    }
    return false;
  }
  
  @SuppressWarnings("unchecked")
  private void load() {
    try (
      ObjectInputStream in = new ObjectInputStream(
                              new FileInputStream(this.filename));) {
      list = (ArrayList<Student>)in.readObject();
   
    } catch (EOFException e) {
      // 파일을 모두 읽었다.
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("학생 데이터 로딩 중 오류 발생!");
      list = new ArrayList<>();
    } 
  }

  synchronized public void save() throws Exception {
    try (
      ObjectOutputStream out = new ObjectOutputStream(
                                new FileOutputStream(this.filename)); ) {
      out.writeObject(list);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







