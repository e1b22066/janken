package oit.is.z2722.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT ID,USER1,USER2,USER1HAND,ISACTIVE FROM MATCHINFO")
  ArrayList<MatchInfo> selectAllByMatchInfo();

  @Insert("INSERT INTO MATCHINFO (USER1,USER2,USER1HAND,ISACTIVE) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo match);

}
