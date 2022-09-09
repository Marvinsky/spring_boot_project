package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class File {
  private Integer fileId;
  private String fileName;
  private String contentType;
  private String fileSize;
  private Integer userId;
  private byte[] fileData;
}
