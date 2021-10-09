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
}
