package bitcamp.java89.ems.server.vo;

import java.io.Serializable;

public class Contact implements Serializable {
  private static final long serialVersionUID = 1L;
  
  protected String name;
  protected String position;
  protected String tel;
  protected String email;
  
  public Contact() {
    super();
  }

  public Contact(String name, String position, String tel, String email) {
    this.name = name;
    this.position = position;
    this.tel = tel;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Contact [name=" + name + ", position=" + position + ", tel=" + tel + ", email=" + email + "]";
  }
}
