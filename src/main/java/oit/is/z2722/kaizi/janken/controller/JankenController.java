package oit.is.z2722.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2722.kaizi.janken.mode.Entry;

@Controller
public class JankenController {

  private final Entry room;

  @Autowired
  public JankenController(Entry room) {
    this.room = room;
  }

  @GetMapping("/janken")
  public String janken(Principal prin, @RequestParam(required = false) String username, ModelMap model) {

    String loginUser = prin.getName();
    model.addAttribute("username", loginUser);
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    return "janken"; // "janken.html" は自動で補完されます
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    String result = "結果";
    if (hand.equals("Gu")) {
      result = result + " Draw";
    }
    if (hand.equals("tyoki")) {
      result = result + " You Lose";
    }
    if (hand.equals("Pa")) {
      result = result + " You Win!";
    }
    model.addAttribute("result", result);
    model.addAttribute("Player_hand", "あなたの手 " + hand);
    model.addAttribute("Com_hand", "相手の手 " + "Gu");
    return "janken.html";

  }
}
