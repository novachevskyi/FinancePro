package com.novachevskyi.expenseslite.data.models.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity {
  public String objectId;
  public String username;
  public String password;
  public String email;
  public String sessionToken;

  public UserEntity() {
  }

  public UserEntity(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
