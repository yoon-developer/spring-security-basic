package com.spring.securitybasic.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

  @Builder
  public User(String account, String password, String email, String name, String role,
      String provider, String providerId) {
    this.account = account;
    this.password = password;
    this.email = email;
    this.name = name;
    this.role = role;
    this.provider = provider;
    this.providerId = providerId;
  }


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String account;
  private String password;
  private String name;
  private String email;
  private String role;

  private String provider;
  private String providerId;

  @CreationTimestamp
  private Timestamp createDate;
}
