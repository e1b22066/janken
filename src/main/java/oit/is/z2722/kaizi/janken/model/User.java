package oit.is.z2722.kaizi.janken.model;

public class User {
  int id;
  String name;

  public User() {

  }

  public User(int id2, String name2) {
    this.id = id2;
    this.name = name2;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
