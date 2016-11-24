package bitcamp.java89.ems.server.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bitcamp.java89.ems.server.vo.Contact;

public class ContactDao {
  static ContactDao obj;
  private String filename = "contact-v1.8.data";
  private ArrayList<Contact> list;
  
  public static ContactDao getInstance() {
    if (obj == null) {
      obj = new ContactDao();
    }
    return obj;
  }
 
  private ContactDao() {
    this.load(); 
  }

  @SuppressWarnings("unchecked")
  private void load() {
    try (
        ObjectInputStream in = new ObjectInputStream(
                                new FileInputStream(this.filename));) {
        list = (ArrayList<Contact>)in.readObject();
     
    } catch (EOFException e) {
      // 파일을 모두 읽었다.
    } catch (Exception e) {
      System.out.println("연락처 데이터 로딩 중 오류 발생!");
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

  public ArrayList<Contact> getList() {
    return this.list;
  }
  
  public ArrayList<Contact> getListByName(String name) {
    ArrayList<Contact> results = new ArrayList<>();
    for (Contact contact : list) {
      if (contact.getName().equals(name)) {
        results.add(contact);
      }
    }
    return results;
  }
  
  synchronized public void insert(Contact contact) {
    list.add(contact);
    try {this.save();} catch (Exception e) {}
  }
  
  synchronized public void update(Contact contact) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(contact.getEmail())) {
        list.set(i, contact);
        try {this.save();} catch (Exception e) {}
        return;
      }
    }
  }
  
  synchronized public void delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getEmail().equals(email)) {
        list.remove(i);
        try {this.save();} catch (Exception e) {}
        return;
      }
    }
  }
  
  public boolean existEmail(String email) {
    for (Contact contact : list) {
      if (contact.getEmail().toLowerCase().equals(email.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}
