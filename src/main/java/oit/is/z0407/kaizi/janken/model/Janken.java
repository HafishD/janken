package oit.is.z0407.kaizi.janken.model;

import java.util.Random;

public class Janken {
  String cpuHand = "";

  public Janken() {
    Random rand = new Random();
    int val = rand.nextInt(1 + 9) + 1;
    if ((val % 3) == 0) {
      cpuHand = "Gu";
    } else if ((val % 3) == 1) {
      cpuHand = "Choki";
    } else {
      cpuHand = "Pa";
    }
  }

  public String getCpuHand() {
    return this.cpuHand;
  }

  public void setCpuHand(String cpu) {
    this.cpuHand = cpu;
  }

}
