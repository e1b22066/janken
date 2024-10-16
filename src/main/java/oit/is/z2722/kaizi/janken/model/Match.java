package oit.is.z2722.kaizi.janken.model;

public class Match {
  int id;
  int user1;
  int user2;
  String user1Hand;
  String user2Hand;

  public Match() {

  }

  public Match(int id2, int user1_2, int user2_2, String user1Hand2, String user2hand2) {
    this.id = id2;
    this.user1 = user1_2;
    this.user2 = user2_2;
    this.user1Hand = user1Hand2;
    this.user2Hand = user2hand2;
  }

  public int getUser1() {
    return user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public String getUser2Hand() {
    return user2Hand;
  }

  public void setUser2Hand(String user2Hand) {
    this.user2Hand = user2Hand;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
