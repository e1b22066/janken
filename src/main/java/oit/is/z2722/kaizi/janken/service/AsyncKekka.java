package oit.is.z2722.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2722.kaizi.janken.model.MatchInfo;
import oit.is.z2722.kaizi.janken.model.Match;
import oit.is.z2722.kaizi.janken.model.User;
import oit.is.z2722.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2722.kaizi.janken.model.MatchMapper;
import oit.is.z2722.kaizi.janken.model.UserMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;
  int id;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  UserMapper UserMapper;

  @Autowired
  MatchMapper MatchMapper;

  @Autowired
  MatchInfoMapper MatchInfoMapper;

  public Match syncShowMatchList(int id) {
    return MatchMapper.selectById(id);
  }

  @Transactional
  public void syncActiveMatch(Match match) {
    MatchMapper.insertMatch(match);

    // 非同期でDB更新したことを共有する際に利用する
    this.dbUpdated = true;
    this.id = match.getId();
  }

  @Async
  public void asyncShowMatchList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        Match Match = this.syncShowMatchList(id);
        emitter.send(Match);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
        // idがnullでない場合、対象のisActiveをfalseに更新
        Match endmatch = MatchMapper.selectById(id);
        endmatch.setIsActive(false);
        MatchMapper.updateById(endmatch);
        id++;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
