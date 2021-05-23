package com.demo.security.interceptor;

import java.util.Collection;
import java.util.Collections;

import com.demo.security.jwt.UserJwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAuthentication implements Authentication {
    private UserJwt userJwt;

    public MyAuthentication(){

    }

    public MyAuthentication(UserJwt userJwt){ 
        this.userJwt= userJwt;
    }
    public void setUserJwt(UserJwt userJwt) {
        this.userJwt = userJwt;
    }


    public UserJwt getUserJwt() {
        return userJwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) {
        // Do nothing because of X and Y.
    }

    @Override
    public String getName() {
        if(userJwt!=null){
            return userJwt.getUsername();
        }
        return null;
    }

}
