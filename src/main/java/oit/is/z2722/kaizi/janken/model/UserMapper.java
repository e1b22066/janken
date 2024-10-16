package oit.is.z2722.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

  @Select("SELECT ID, NAME FROM USERS")
  ArrayList<User> selectAllByUSERS();

  @Select("select ID, NAME from USERS WHERE ID = #{id}")
  User selectById(int id);

  /**
   * update, insert, deleteでは抽象メソッドの返り値は (1)boolean:テーブルに1レコード以上何らかの変更が合ったかどうか
   * (2)void:返り値なし (3)int:テーブルに変更があったときの変更があったレコードの件数, のいずれかを設定できる
   *
   * @param id
   * @return
   */
  @Delete("DELETE FROM USERS WHERE ID =#{id}")
  boolean deleteById(int id);

  /**
   * 入力されたFruit Beanの値でDBを更新する {}内のフィールド名指定時には大文字小文字を間違えないようにすること （カラム名はOK）
   *
   * @param fruit
   */
  @Update("UPDATE USERS SET NAME=#{name}, WHERE ID = #{id}")
  void updateById(User users);

}
