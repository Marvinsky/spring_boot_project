package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {
  private final UserService userService;
  private final FileMapper fileMapper;

  public FileService(UserService userService,
      FileMapper fileMapper) {
    this.userService = userService;
    this.fileMapper = fileMapper;
  }

  public File getFileByFileId(Integer fileId) {
    return fileMapper.getFileByFileId(fileId);
  }

  public boolean existsFileName(String fileName) {
    return fileMapper.existsFilename(fileName) != null;
  }

  public List<File> getFileListByUserId() {
    return fileMapper.getFileListByUserId(userService.getUserId());
  }

  public void createFile(File file) {
    fileMapper.insert(file);
  }

  public boolean deleteFile(Integer fileId) {
    return fileMapper.delete(fileId);
  }
}
