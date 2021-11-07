package oit.is.z0407.kaizi.janken.model;

public class MatchInfo {
  int id;
  int user1, user2;
  String user1Hand;
  boolean isActive;

  public MatchInfo() {

  }

  public MatchInfo(int pId, int eId, String hand, boolean act) {
    this.user1 = pId;
    this.user2 = eId;
    this.user1Hand = hand;
    this.isActive = act;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser1() {
    return this.user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return this.user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return this.user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public boolean getIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }
}
