package oit.is.z0407.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Lec02Controller {

  @PostMapping("/lec02")
  public String userName(@RequestParam(name = "name") String name, ModelMap model) {
    model.addAttribute("name", name);
    return "lec02.html";
  }

  @GetMapping("/lec02")
  public String lec02() {
    return "lec02.html";
  }

  @GetMapping("/lec02janken")
  public String lec02Janken(@RequestParam String hand, ModelMap model) {
    String result;
    if (hand.equals("Pa")) {
      result = "You Win!";
    } else if (hand.equals("Choki")) {
      result = "You Lose...";
    } else if (hand.equals("Gu")) {
      result = "It's a Draw.";
    } else {
      result = "NO MATCH";
    }
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);
    return "lec02.html";
  }

}
