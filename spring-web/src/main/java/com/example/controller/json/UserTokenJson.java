package com.example.controller.json;

public class UserTokenJson {
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "UserTokenJson{" + "token='" + token + '\'' + '}';
  }
}
