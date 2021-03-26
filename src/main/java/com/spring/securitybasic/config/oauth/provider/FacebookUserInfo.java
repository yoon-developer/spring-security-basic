package com.spring.securitybasic.config.oauth.provider;

import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FacebookUserInfo implements OAuth2UserInfo{

  private Map<String, Object> attributes;

  public FacebookUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getAccount() {
    return "facebook_" + attributes.get("id");
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getProvider() {
    return "facebook";
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
