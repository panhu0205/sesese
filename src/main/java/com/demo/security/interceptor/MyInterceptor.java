package com.demo.security.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.dto.ApiMessageDto;
import com.demo.security.jwt.JWTUtil;
import com.demo.security.jwt.UserJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class MyInterceptor implements HandlerInterceptor{
	ObjectMapper mapper = new ObjectMapper();
	@Override
	public boolean preHandle
	   (HttpServletRequest request, HttpServletResponse response, Object o) 
	   throws Exception {
	   
		if ( !request.getDispatcherType().name().equals("REQUEST") ) {
            return true;
        }
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        boolean validSign = checkHeader(request);
        if (!validSign) {
            ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Invalid token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, mapper.writeValueAsString(apiMessageDto));
        }

        return validSign;
	}
	private Boolean checkHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }

        String authToken = header.substring(7);
        DecodedJWT decodedJWT = JWTUtil.verifierJWT(authToken);
        if(decodedJWT == null){
            return false;
        }    

        //lay ra quyen
        UserJwt qrJwt = JWTUtil.getSessionFromToken(decodedJWT);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new MyAuthentication(qrJwt));

        log.info("jwt user verify ne: {}", qrJwt);

        //check permission here
        String requestUri = request.getRequestURI();
        String[] uriByPassAuth = qrJwt.getPermission().split(",");

        return Arrays.stream(uriByPassAuth).anyMatch(requestUri::contains);
    }
    /**
     * get full url request
     *
     * @param req
     * @return
     */
    private static String getUrl(HttpServletRequest req) {
        String reqUrl = req.getRequestURL().toString();
        String queryString = req.getQueryString();   // d=789
        if (!StringUtils.isEmpty(queryString)) {
            reqUrl += "?" + queryString;
        }
        return reqUrl;
    }
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
	   Object handler, ModelAndView modelAndView) throws Exception {
	   
	   System.out.println("Post Handle method is Calling");
	}
    
	@Override
	public void afterCompletion
	   (HttpServletRequest request, HttpServletResponse response, Object 
	   handler, Exception exception) throws Exception {
	   
	    long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        log.debug("[" + getUrl(request) + "] executeTime : " + executeTime + "ms");

        if (exception != null) {
            log.error("afterCompletion>> " + exception.getMessage());

        }
	}
}
