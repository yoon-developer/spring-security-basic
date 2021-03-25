package com.spring.securitybasic.controller;

import com.spring.securitybasic.domain.User;
import com.spring.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
  public @ResponseBody String user() {
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
    String rawPassword = user.getPassword();
    String encPassword = bCryptPasswordEncoder.encode(rawPassword);
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
