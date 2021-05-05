package com.demo.security.jwt;

import java.io.Serializable;

import com.demo.security.util.ZipUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class UserJwt implements Serializable {
    public static final String DELIM = "::";
    public static final String EMPTY_STRING = "<>";
    private Long accountId = -1L;
    private String username = EMPTY_STRING;
    private String permission = EMPTY_STRING;
    private String role = EMPTY_STRING;

    public String toClaim(){
        return ZipUtils.zipString(accountId+DELIM+username+DELIM+role+DELIM+permission);
    }

    public static UserJwt decode(String input){
        UserJwt result = null;
        try {
            String[] items = ZipUtils.unzipString(input).split(DELIM);
            if(items.length == 4){
                result = new UserJwt();
                result.setAccountId(parserLong(items[0]));                
                result.setUsername(checkString(items[1]));
                result.setRole(checkString(items[2]));
                result.setPermission(checkString(items[3]));
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return  result;
    }

    private static Long parserLong(String input){
        try{
            Long out = Long.parseLong(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static Integer parserInt(String input){
        try{
            Integer out = Integer.parseInt(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static String checkString(String input){
        if(!input.equals(EMPTY_STRING)){
            return  input;
        }
        return  null;
    }

    private static Boolean checkBoolean(String input){
        try{
            return Boolean.parseBoolean(input);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public String toString() {
        return "UserJwt{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", permission='" + permission + '\'' +  
                '}';
    }
}
