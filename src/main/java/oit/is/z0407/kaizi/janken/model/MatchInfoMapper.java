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

  @Select("SELECT id from MATCHINFO where isActive = TRUE;")
  int activeMatchId();

  @Select("SELECT * FROM MATCHINFO where isActive = TRUE;")
  ArrayList<MatchInfo> selectActiveMatchInfos();

  @Update("UPDATE MATCHINFO SET USER1HAND=#{user1Hand} WHERE ISACTIVE = #{isActive}")
  void updateById(MatchInfo matchInfo);
}
