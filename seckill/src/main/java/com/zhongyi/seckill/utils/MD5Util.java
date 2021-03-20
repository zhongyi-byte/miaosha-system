package com.zhongyi.seckill.utils;



import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



@Component
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt= "1a2b3c4d";
    
    public static String inputPassToFormPass(String inputPass){
        String str ="" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str = salt.charAt(1) + salt.charAt(4) + formPass + salt.charAt(2);
        return md5(str);
    }

    public static String inputPassToDBPass(String pass,String salt){
        String formPass = inputPassToFormPass(pass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("1234567"));
        System.out.println(formPassToDBPass("fc42dc9ec7e2f3828ecd8eb1f23a45f3", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("1234567","1a2b3c4d"));


    }
}
