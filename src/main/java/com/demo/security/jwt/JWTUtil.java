package com.demo.security.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.security.interceptor.MyAuthentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {
    
	private JWTUtil(){

	}
	public static String secretKey = "mySecretKeyAhihi";

    public static String getJWTToken (String id, UserJwt userJwt){

		// return   Jwts
		// 		.builder()
		// 		.setId("softtekJWT")
		// 		.claim("data",userJwt.toClaim())
		// 		.setIssuedAt(new Date(System.currentTimeMillis()))
		// 		.setExpiration(new Date(System.currentTimeMillis() + 36000000))
		// 		.signWith(SignatureAlgorithm.HS512,
		// 				secretKey.getBytes()).compact();
        Algorithm algorithm = Algorithm.HMAC512(secretKey);

        return JWT.create().withJWTId(id).withKeyId(id)
        .withIssuedAt(new Date(System.currentTimeMillis()))
        .withNotBefore(new Date(System.currentTimeMillis()))
        .withExpiresAt(new Date(System.currentTimeMillis() + 36000000))
        .withClaim("data",userJwt.toClaim()).sign(algorithm);
	}

	public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        if(authentication!=null){
            return  authentication.getName();
        }
        return null;
    }
	
   
    public static UserJwt getSessionFromToken(DecodedJWT decodedJWT){
        if(decodedJWT == null){
            return null;
        }
        return UserJwt.decode(decodedJWT.getClaim("data").asString());
    }

	public static DecodedJWT verifierJWT(String token) {
        try {
                Algorithm algorithm = Algorithm.HMAC512(secretKey);
                JWTVerifier verifier = JWT.require(algorithm)
                        .acceptLeeway(1) //1 sec for nbf and iat
                        .acceptExpiresAt(5) //5 secs for exp
                        .build();
                return verifier.verify(token);
        } catch (Exception e) {
            log.error("verifierJWT>>"+e.getMessage(), e);
        }
        return null;
    }
}
