package com.demo.storage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

// TODO: think about this and design things
@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar_path")
    private String avatarPath;

    @Column(name = "reset_pwd_code")
    private String resetPwdCode;

    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;

    @Column(name = "reset_pwd_log")
    private Integer resetPwdLog;

    @Column(name = "role")
    private String role;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "attempt_login_date")
    private Date attemptLoginDate;

    @Column(name = "attempt_login_count")
    private Integer attemptLoginCount;

    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    
    @Column(name= "lang")
    private String lang;

    private Integer status;
    private Integer otp;
}
