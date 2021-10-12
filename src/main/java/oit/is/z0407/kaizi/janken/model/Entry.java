package oit.is.z0407.kaizi.janken.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Entry {
  ArrayList<String> players = new ArrayList<>();

  public void addUser(String name) {
    for (String s : this.players) {
      if (s.equals(name)) {
        return;
      }
    }
    this.players.add(name);
  }

  public ArrayList<String> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<String> players) {
    this.players = players;
  }

}
