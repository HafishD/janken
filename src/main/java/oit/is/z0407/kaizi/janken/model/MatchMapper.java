package oit.is.z0407.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * FROM MATCHES where isActive = TRUE;")
  Match selectActiveMatch();

  @Select("SELECT count(*) FROM MATCHES where isActive = TRUE;")
  int findActiveMatch();

  @Select("SELECT ID FROM MATCHES where isActive = TRUE;")
  int findMatchId();

  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive});")
  void insertMatch(Match match);

  @Update("UPDATE MATCHES SET ISACTIVE = FALSE where ISACTIVE = TRUE;")
  void updateMatch();
}
