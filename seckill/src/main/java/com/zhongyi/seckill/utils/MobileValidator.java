package com.zhongyi.seckill.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MobileValidator {
    
    private static final Pattern MOBIL_PATTERN = Pattern.compile("[1][3-9][0-9]{9}$");
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        
        Matcher matcher = MOBIL_PATTERN.matcher(mobile);
        return matcher.matches();
    }
}
