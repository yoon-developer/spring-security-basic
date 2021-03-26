package com.spring.securitybasic.config.auth;

import com.spring.securitybasic.domain.User;
import com.spring.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
    User user = userRepository.findByAccount(account);
    if (user == null) {
      return null;
    }
    return new PrincipalDetails(user);
  }
}
