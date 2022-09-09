package com.udacity.jwdnd.course1.cloudstorage.dto;

public class CredentialDTO {
  Integer credentialId;
  String url;
  String username;
  String key;
  String password;
  String decryptPassword;

  public CredentialDTO(Integer credentialId, String url, String username, String key, String password, String decryptPassword) {
    this.credentialId = credentialId;
    this.url = url;
    this.username = username;
    this.key = key;
    this.password = password;
    this.decryptPassword = decryptPassword;
  }

  public Integer getCredentialId() {
    return credentialId;
  }

  public void setCredentialId(Integer credentialId) {
    this.credentialId = credentialId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDecryptPassword() {
    return decryptPassword;
  }

  public void setDecryptPassword(String decryptPassword) {
    this.decryptPassword = decryptPassword;
  }
}
