package bitcamp.java89.ems.server.dao;

import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Student;

public class StudentDao extends AbstractDao<Student> {

  public StudentDao() {
    this.setFilename("student-v1.9.data");
    try {this.load();} catch (Exception e) {}
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
}







