package com.spring.securitybasic.config.oauth.provider;

public interface OAuth2UserInfo {

  String getAccount();
  String getProviderId();
  String getProvider();
  String getEmail();
  String getName();
}
