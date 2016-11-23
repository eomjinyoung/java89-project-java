package bitcamp.java89.ems.server.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import bitcamp.java89.ems.server.Command;
import bitcamp.java89.ems.server.dao.ContactDao;
import bitcamp.java89.ems.server.vo.Contact;

public class ContactListController implements Command {
  private ContactDao contactDao;

  public ContactListController() {
    contactDao = ContactDao.getInstance();
  }

  public void service(HashMap<String,String> paramMap, PrintStream out) {
    ArrayList<Contact> list = contactDao.getList();
    for (Contact contact : list) {
      out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }
}




