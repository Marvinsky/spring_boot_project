package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CredentialDTO {
  Integer credentialId;
  String url;
  String username;
  String key;
  String password;
  String decryptPassword;
}
