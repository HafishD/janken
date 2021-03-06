package oit.is.z0407.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

// import oit.is.z0407.kaizi.janken.model.Janken;
import oit.is.z0407.kaizi.janken.model.Entry;
import oit.is.z0407.kaizi.janken.model.User;
import oit.is.z0407.kaizi.janken.model.UserMapper;
import oit.is.z0407.kaizi.janken.model.Match;
import oit.is.z0407.kaizi.janken.model.MatchMapper;
import oit.is.z0407.kaizi.janken.model.MatchInfo;
import oit.is.z0407.kaizi.janken.model.MatchInfoMapper;
import oit.is.z0407.kaizi.janken.service.AsyncKekka;

@Controller
public class Lec02Controller {
  String you, eName;
  int yId, eId;
  String yHand = "user1Hand";
  String eHand;
  boolean flag = false;

  @Autowired
  private Entry entry;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka kekka;

  @GetMapping("/lec02")
  public String lec02(Principal prin, ModelMap model) {
    String loginPlayer = prin.getName();
    this.you = loginPlayer;
    User user = new User();
    user.setName(loginPlayer);
    this.yId = userMapper.selectByName(loginPlayer);
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectActiveMatchInfos();
    if (matchMapper.findActiveMatch() == 1) {
      // matchMapper.updateMatch();
      final SseEmitter sseEmitter = new SseEmitter();
      this.kekka.asyncDatabase(sseEmitter);
    }
    model.addAttribute("users", users);
    model.addAttribute("matchInfos", matchInfos);
    model.addAttribute("matches", matches);
    return "lec02.html";
  }

  /**
   * @GetMapping("/matchjanken")
   *
   * @Transactional public String matchJanken(Principal prin, @RequestParam String
   *                hand, @RequestParam String vs, ModelMap model) { String result
   *                = "NO GAME"; this.yHand = hand; Janken janken = new Janken();
   *                String cpuHand = janken.getCpuHand(); this.eHand = cpuHand;
   *                String playerName = prin.getName(); ArrayList<User> players =
   *                userMapper.selectAllUsers(); int p1Id = 1, p2Id = 2; Match
   *                match = new Match();
   *
   *                if (hand.equals(cpuHand)) { result = "It's a Draw."; } else {
   *                switch (hand) { case "Gu": if (cpuHand.equals("Choki")) {
   *                result = "You Win!"; } else if (cpuHand.equals("Pa")) { result
   *                = "You Lose..."; } break; case "Choki": if
   *                (cpuHand.equals("Pa")) { result = "You Win!"; } else if
   *                (cpuHand.equals("Gu")) { result = "You Lose..."; } break; case
   *                "Pa": if (cpuHand.equals("Gu")) { result = "You Win!"; } else
   *                if (cpuHand.equals("Choki")) { result = "You Lose..."; }
   *                break; } }
   *
   *                for (User p : players) { if (p.getName().equals(playerName)) {
   *                p1Id = p.getId(); break; } } for (User p : players) { if
   *                (p.getName().equals(vs)) { p2Id = p.getId(); break; } }
   *                match.setUser1(p1Id); match.setUser2(p2Id);
   *                match.setUser1Hand(hand); match.setUser2Hand(cpuHand);
   *                matchMapper.insertMatch(match); // ArrayList<Match> matches =
   *                matchMapper.selectAllMatches(); model.addAttribute("hand",
   *                hand); model.addAttribute("janken", janken);
   *                model.addAttribute("result", result); //
   *                model.addAttribute("matches", matches); return "match.html"; }
   **/

  @GetMapping("/match")
  @Transactional
  public String match(Principal prin, @RequestParam Integer id, ModelMap model) {
    this.flag = true;
    // User enemy = userMapper.selectById(id);
    String player = prin.getName();
    this.entry.setUser(player);
    ArrayList<User> users = userMapper.selectAllUsers();
    this.eId = id;
    String enemy = "CPU";
    for (User u : users) {
      if (u.getId() == id) {
        enemy = u.getName();
        break;
      }
    }
    this.eName = enemy;
    MatchInfo matchInfo = new MatchInfo(this.yId, this.eId, this.yHand, this.flag);
    matchInfoMapper.insertMatchInfo(matchInfo);
    model.addAttribute("entry", this.entry);
    model.addAttribute("enemy", enemy);
    return "match.html";
  }

  @GetMapping("/wait")
  @Transactional
  public String waitHand(@RequestParam String hand, ModelMap model) {
    this.yHand = hand;
    MatchInfo matchInfo = new MatchInfo(this.yId, this.eId, hand, this.flag);
    matchInfoMapper.updateById(matchInfo);
    Match newMatch = new Match();

    if (kekka.syncCheckActive(this.yId)) {
      newMatch = kekka.syncOnlineMatch(matchInfo);
      newMatch.setId(matchMapper.findMatchId());
      // matchInfoMapper.updateMatchInfo();
    } else {
      newMatch = kekka.syncShowActive();
    }

    model.addAttribute("newMatch", newMatch);

    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncInfoDatabase(sseEmitter);

    return "wait.html";
  }

  /**
   * @GetMapping("/wait/connect") public SseEmitter connect() { final SseEmitter
   * sseEmitter = new SseEmitter(); this.kekka.asyncDatabase(sseEmitter); return
   * sseEmitter; }
   **/
}
