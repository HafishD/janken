package oit.is.z0407.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0407.kaizi.janken.model.Janken;
import oit.is.z0407.kaizi.janken.model.Entry;
import oit.is.z0407.kaizi.janken.model.User;
import oit.is.z0407.kaizi.janken.model.UserMapper;
import oit.is.z0407.kaizi.janken.model.Match;
import oit.is.z0407.kaizi.janken.model.MatchMapper;

@Controller
public class Lec02Controller {

  @Autowired
  private Entry entry;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/lec02")
  public String lec02(Principal prin, ModelMap model) {
    String loginPlayer = prin.getName();
    this.entry.addUser(loginPlayer);
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);
    model.addAttribute("entry", entry);
    return "lec02.html";
  }

  @GetMapping("/lec02janken")
  public String lec02Janken(@RequestParam String hand, ModelMap model) {
    String result = "";
    Janken janken = new Janken();
    if (hand.equals(janken.getCpuHand())) {
      result = "It's a Draw.";
    } else {
      switch (hand) {
        case "Gu":
          if (janken.getCpuHand().equals("Choki")) {
            result = "You Win!";
          } else if (janken.getCpuHand().equals("Pa")) {
            result = "You Lose...";
          }
          break;
        case "Choki":
          if (janken.getCpuHand().equals("Pa")) {
            result = "You Win!";
          } else if (janken.getCpuHand().equals("Gu")) {
            result = "You Lose...";
          }
          break;
        case "Pa":
          if (janken.getCpuHand().equals("Gu")) {
            result = "You Win!";
          } else if (janken.getCpuHand().equals("Choki")) {
            result = "You Lose...";
          }
          break;
      }
    }
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("hand", hand);
    model.addAttribute("janken", janken);
    model.addAttribute("result", result);
    model.addAttribute("matches", matches);
    return "lec02.html";
  }

}
