package oit.is.z0407.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from users;")
  ArrayList<User> selectAllUsers();

  // @Select("SELECT name from users where id = #{id}")
  // User selectById(int id);
}
