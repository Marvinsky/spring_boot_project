package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final HashService hashService;

  public UserService(UserMapper userMapper, HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  public boolean isUsernameAvailable(String username) {
    return userMapper.getUser(username) == null;
  }


  public int createUser(User users) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);
    String encodeSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(users.getPassword(), encodeSalt);
    return userMapper.insert(new User(null, users.getUsername(), encodeSalt, hashedPassword, users.getFirstName(), users.getLastName()));
  }

  public User getUser(String username) {
    return userMapper.getUser(username);
  }

  public Integer getUserId() {
    return getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
  }
}
