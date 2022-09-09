package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

  private final CredentialMapper credentialMapper;
  private final UserService userService;
  private final EncryptionService encryptionService;

  public CredentialService(
      CredentialMapper credentialMapper,
      UserService userService,
      EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.userService = userService;
    this.encryptionService = encryptionService;
  }

  public List<CredentialDTO> getCredentialListByUserId() {
    List<Credential> credentialList = credentialMapper.getCredentialListByUserId(
        userService.getUserId());

    return credentialList.stream().map(credential -> new CredentialDTO(
        credential.getCredentialId(),
        credential.getUrl(),
        credential.getUserName(),
        credential.getKey(),
        credential.getPassword(),
        encryptionService.decryptValue(credential.getPassword(),
            credential.getKey()))).collect(Collectors.toList());
  }

  public void createCredential(Credential credential) {
    credential.setUserId(userService.getUserId());
    credential.setKey(credential.getKey());
    credential.setPassword(encryptionService.encryptValue(credential.getPassword(),
        credential.getKey()));
    credentialMapper.insert(credential);
  }


  public void updateCredential(Credential credential) {
    credential.setUserId(userService.getUserId());
    credential.setKey(credential.getKey());
    credential.setPassword(encryptionService.encryptValue(credential.getPassword(),
        credential.getKey()));
    credentialMapper.update(credential);
  }


  public boolean deleteCredential(Integer credentialId) {
    return credentialMapper.delete(credentialId);
  }

  public String getRandomKey() {
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    return Base64.getEncoder().encodeToString(key);
  }


}
