package com.spring.securitybasic.config.oauth.provider;

import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo{

  private Map<String, Object> attributes;

  public NaverUserInfo(Map<String, Object> attributes) {
    this.attributes = (Map<String, Object>) attributes.get("response");
  }

  @Override
  public String getAccount() {
    return "naver_" + attributes.get("id");
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getProvider() {
    return "naver";
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }
}
