package bitcamp.java89.ems.server.dao;

import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Student;

public interface StudentDao {
  ArrayList<Student> getList() throws Exception;
  Student getOne(String userId) throws Exception;
  void insert(Student student) throws Exception;
  void update(Student student) throws Exception;
  void delete(String userId) throws Exception;
  boolean existUserId(String userId) throws Exception;
}
