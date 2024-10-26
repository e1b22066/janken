package oit.is.z2722.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2722.kaizi.janken.model.Entry;
import oit.is.z2722.kaizi.janken.model.User;
import oit.is.z2722.kaizi.janken.model.UserMapper;
import oit.is.z2722.kaizi.janken.service.AsyncKekka;
import oit.is.z2722.kaizi.janken.model.Match;
import oit.is.z2722.kaizi.janken.model.MatchMapper;
import oit.is.z2722.kaizi.janken.model.MatchInfo;
import oit.is.z2722.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  private final Entry room;

  @Autowired
  public JankenController(Entry room) {
    this.room = room;
  }

  @Autowired
  UserMapper UserMapper;

  @Autowired
  MatchMapper MatchMapper;

  @Autowired
  MatchInfoMapper MatchInfoMapper;

  @Autowired
  AsyncKekka Kekka;

  @GetMapping("/janken")
  public String janken(Principal prin, @RequestParam(required = false) String username,
      @RequestParam(required = false) Integer id, ModelMap model) {
    ArrayList<User> user = UserMapper.selectAllByUSERS();
    model.addAttribute("user", user);
    ArrayList<Match> match = MatchMapper.selectAllByMatch();
    model.addAttribute("match", match);
    ArrayList<MatchInfo> matchinfo = MatchInfoMapper.selectAllByMatchInfo();
    model.addAttribute("matchinfo", matchinfo);
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
    if (hand.equals("Choki")) {
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

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    User user1 = UserMapper.selectByName(loginUser);
    model.addAttribute("user2", user1);
    User user2 = UserMapper.selectById(id);
    model.addAttribute("user1", user2);

    return "match.html";
  }

  @GetMapping("/fight")
  public String jankengame_1(@RequestParam Integer id, @RequestParam String hand, ModelMap model, Principal prin) {
    int flag = 0;
    String loginUser = prin.getName();
    ArrayList<Match> match = MatchMapper.selectAllByMatch();
    Match match_fight = new Match();
    Match match_tmp = new Match();
    MatchInfo matchinfo_wait = new MatchInfo();

    User player = UserMapper.selectByName(loginUser);
    model.addAttribute("username", loginUser);

    ArrayList<MatchInfo> matchInfo = MatchInfoMapper.selectAllByMatchInfo();
    for (int i = 0; i < matchInfo.size(); i++) {
      MatchInfo matchinfo = matchInfo.get(i);
      if (matchinfo.getIsActive() && matchinfo.getUser2() == player.getId()) {
        flag = 1;
        matchinfo_wait.setId(matchinfo.getId());
        match_tmp.setUser2Hand(matchinfo.getUser1Hand());
      }
    }
    if (flag == 0) {
      matchinfo_wait.setUser1(player.getId());
      matchinfo_wait.setUser2(id);
      matchinfo_wait.setUser1Hand(hand);
      matchinfo_wait.setIsActive(true);
      MatchInfoMapper.insertMatchInfo(matchinfo_wait);
    } else {
      match_fight.setUser1(player.getId());
      match_fight.setUser2(id);
      match_fight.setUser1Hand(hand);
      match_fight.setUser2Hand(match_tmp.getUser2Hand());
      match_fight.setIsActive(true);
      this.Kekka.syncActiveMatch(match_fight);
      MatchInfo endmatchinfo = MatchInfoMapper.selectById(matchinfo_wait.getId());
      endmatchinfo.setIsActive(false);
      MatchInfoMapper.updateById(endmatchinfo);
    }

    model.addAttribute("match", match);

    return "wait.html";
  }

  @GetMapping("/Update")
  public SseEmitter Update() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.Kekka.asyncShowMatchList(sseEmitter);
    return sseEmitter;
  }

}
