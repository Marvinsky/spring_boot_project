package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final HashService hashService;

  public UserService(UserMapper userMapper,
      HashService hashService) {
    this.userMapper = userMapper;
    this.hashService = hashService;
  }

  public boolean isUserNameAvailable(String userName) {
    return userMapper.getUser(userName) == null;
  }

  public int createUser(User user) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);
    String encodeSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodeSalt);
    return userMapper.insert(new User(null, user.getUserName(), encodeSalt, hashedPassword, user.getFirstName(), user.getLastName()));
  }

  public User getUser(String userName) {
    return userMapper.getUser(userName);
  }

  public Integer getUserId() {
    return getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
  }



}
