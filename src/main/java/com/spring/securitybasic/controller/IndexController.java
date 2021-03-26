package com.spring.securitybasic.controller;

import com.spring.securitybasic.config.auth.PrincipalDetails;
import com.spring.securitybasic.domain.User;
import com.spring.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/test/login")
  public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal
      PrincipalDetails userDetails) {

    System.out.println("userDetails.getUser() = " + userDetails.getUser());

    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    System.out.println("principalDetails.getUser() = " + principalDetails.getUser());

    return "세션 정보 확인";
  }

  @GetMapping("/test/oauth/login")
  public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {

    System.out.println("oauth = " + oauth.getAttributes());

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

    return "Oauth 세션 정보 확인";
  }

  @GetMapping({"","/"})
  public String index() {
    return "index";
  }

  @GetMapping("/admin")
  public @ResponseBody String admin() {
    return "admin";
  }

  @GetMapping("/manager")
  public @ResponseBody String manager() {
    return "manager";
  }

  @GetMapping("/user")
  public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
    return "user";
  }

  @GetMapping("/loginForm")
  public String loginForm() {
    return "loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm() {
    return "joinForm";
  }

  @PostMapping("/join")
  public String join(User user) {
    user.setRole("ROLE_USER");
    String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encPassword);
    userRepository.save(user);
    return "redirect:/loginForm";
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/info")
  public @ResponseBody String info() {
    return "개인정보";
  }

  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  @GetMapping("/data")
  public @ResponseBody String data() {
    return "데이터정보";
  }
}
