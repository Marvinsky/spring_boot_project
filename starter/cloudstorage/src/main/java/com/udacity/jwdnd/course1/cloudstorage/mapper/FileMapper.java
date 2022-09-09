package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

  @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
  File getFileByFileId(Integer fileId);

  @Select("SELECT * FROM FILES WHERE userid = #{userId}")
  List<File> getFileListByUserId(Integer userId);

  @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
  File existsFilename(String fileName);

  @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) " +
      "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  int insert(File file);

  @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
  boolean delete(int fileId);

}
