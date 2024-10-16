package oit.is.z2722.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

  @Select("SELECT ID,USER1,USER2,USER1HAND,USER2HAND FROM MATCHES")
  ArrayList<Match> selectAllByMatch();

  @Select("SELECT ID,USER1,USER2,USER1HAND,USER2HAND FROM MATCHES WHERE ID = #{id}")
  Match selectById(int id);

}
