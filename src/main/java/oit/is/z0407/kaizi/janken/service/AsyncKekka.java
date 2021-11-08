package oit.is.z0407.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0407.kaizi.janken.model.Match;
import oit.is.z0407.kaizi.janken.model.MatchInfo;
import oit.is.z0407.kaizi.janken.model.MatchMapper;
import oit.is.z0407.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  public ArrayList<Match> syncShowMatches() {
    return matchMapper.selectAllMatches();
  }

  public Match syncShowActive() {
    return matchMapper.selectActiveMatch();
  }

  public boolean syncCheckActive(int find) {
    if (matchInfoMapper.findMyMatch(find) == 1) {
      return true;
    }
    return false;
  }

  @Transactional
  public Match syncOnlineMatch(MatchInfo info) {
    Match online = new Match();
    online.setUser1(info.getUser2());
    online.setUser2(info.getUser1());
    online.setUser2Hand(info.getUser1Hand());
    online.setUser1Hand(matchInfoMapper.selectUser2Hand(info.getUser1()));
    online.setIsActive(true);
    matchMapper.insertMatch(online);
    this.dbUpdated = true;
    return online;
  }

  @Async
  public void asyncInfoDatabase(SseEmitter emitter) {
    try {
      while (true) {
        ArrayList<Match> matches = this.syncShowMatches();
        // 試合終了
        if (matchMapper.findActiveMatch() == 1) {
          matchInfoMapper.updateMatchInfo();
        }
        emitter.send(matches);
        TimeUnit.MILLISECONDS.sleep(1000);
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asynDatabase complete");
  }

  @Async
  public void asyncDatabase(SseEmitter emitter) {
    try {
      while (true) {
        if (false == this.dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        ArrayList<Match> matches = this.syncShowMatches();
        if (matchMapper.findActiveMatch() == 1) {
          matchMapper.updateMatch();
        }
        emitter.send(matches);
        TimeUnit.MILLISECONDS.sleep(1000);
        this.dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asynDatabase complete");
  }
}
