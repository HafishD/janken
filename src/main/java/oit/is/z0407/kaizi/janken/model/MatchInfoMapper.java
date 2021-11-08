package oit.is.z0407.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Insert("INSERT INTO matchInfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);

  // @Select("SELECT * FROM MATCHINFO;")
  // ArrayList<MatchInfo> selectMatchInfos();

  @Select("SELECT id from MATCHINFO where isActive = TRUE;")
  int activeMatchId();

  @Select("SELECT * FROM MATCHINFO where isActive = TRUE;")
  ArrayList<MatchInfo> selectActiveMatchInfos();

  @Select("SELECT count(*) FROM MATCHINFO where user2 = #{find} and isActive = TRUE;")
  int findMyMatch(int find);

  @Select("SELECT count(*) FROM MATCHINFO where ISACTIVE = TRUE;")
  int findActive();

  // @Select("SELECT * FROM MATCHINFO where user2 = #{id} and isActive = TRUE;")
  // ArrayList<MatchInfo> selectMyMatch(int id);

  @Select("SELECT user1Hand FROM MATCHINFO where user2 = #{id} and isActive = TRUE;")
  String selectUser2Hand(int id);

  @Update("UPDATE MATCHINFO SET USER1HAND=#{user1Hand} WHERE ISACTIVE = #{isActive} and user1 = #{user1}")
  void updateById(MatchInfo matchInfo);

  @Update("UPDATE MATCHINFO SET ISACTIVE = FALSE where ISACTIVE = TRUE")
  void updateMatchInfo();
}
