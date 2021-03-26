package com.spring.securitybasic.config.oauth;

import com.spring.securitybasic.config.auth.PrincipalDetails;
import com.spring.securitybasic.config.oauth.provider.FacebookUserInfo;
import com.spring.securitybasic.config.oauth.provider.GoogleUserInfo;
import com.spring.securitybasic.config.oauth.provider.NaverUserInfo;
import com.spring.securitybasic.config.oauth.provider.OAuth2UserInfo;
import com.spring.securitybasic.domain.User;
import com.spring.securitybasic.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

    String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

    OAuth2UserInfo oAuth2UserInfo = null;
    if (registrationId.equals("google")) {
      System.out.println("구글 로그인 요청");
      oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
    } else if (registrationId.equals("facebook")) {
      System.out.println("페이스북 로그인 요청");
      oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
    } else if (registrationId.equals("naver")) {
      System.out.println("네이버 로그인 요청");
      oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
    } else {
      System.out.println("우리는 구글, 페이스북, 네이버 로그인만 지원 합니다.");
    }

    String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
    String role = "ROLE_USER";

    User user = null;
    User findUser = userRepository.findByAccount(oAuth2UserInfo.getAccount());

    if (findUser == null) {
      user = User.builder()
          .account(oAuth2UserInfo.getAccount())
          .password(password)
          .name(oAuth2UserInfo.getName())
          .email(oAuth2UserInfo.getEmail())
          .role(role)
          .provider(oAuth2UserInfo.getProvider())
          .providerId(oAuth2UserInfo.getProviderId())
          .build();

      userRepository.save(user);
    } else {
      System.out.println("회원가입이 되어 있습니다.");
    }

    return new PrincipalDetails(user, oAuth2User.getAttributes());
  }
}
