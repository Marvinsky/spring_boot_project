package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
  private final CredentialMapper credentialMapper;
  private final UserService userService;
  private final EncryptionService encryptionService;

  public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.userService = userService;
    this.encryptionService = encryptionService;
  }

  public List<CredentialDTO> getCredentialListByUserId() {
    List<Credential> credentialList = credentialMapper.getCredentialListByUserId(userService.getUserId());

    return credentialList.stream().map(credential -> new CredentialDTO(credential.getCredentialId(),
        credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(),
        encryptionService.decryptValue(credential.getPassword(), credential.getKey()))).collect(Collectors.toList());
  }

  public void createCredentials(Credential credential) {
    credential.setUserId(userService.getUserId());
    credential.setKey(getRandomKey());
    credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
    credentialMapper.insert(credential);
  }

  public void updateCredentials(Credential credential) {
    credential.setUserId(userService.getUserId());
    credential.setKey(getRandomKey());
    credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
    credentialMapper.update(credential);
  }

  public boolean deleteCredentials(Integer credentialId) {
    return credentialMapper.delete(credentialId);
  }

  public String getRandomKey() {
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    return Base64.getEncoder().encodeToString(key);
  }

}
