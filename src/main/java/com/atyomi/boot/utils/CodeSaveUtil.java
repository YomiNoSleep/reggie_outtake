package com.atyomi.boot.utils;

import java.util.HashMap;
import java.util.Map;

public class CodeSaveUtil {
    private static Map<Long,Integer> codes = new HashMap<>();
    public static void saveCode(Long phone,Integer code){
        codes.put(phone,code);
    }
    public static Integer getCode(Long phone){
        return codes.get(phone);
    }
}
