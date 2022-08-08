package com.example.drive.utills;


import com.example.drive.common.CustomException;

import java.util.Random;

//生成验证码工具
public class ValidateCodeUtils {

    public static Integer generateValidateCode(int lengh){
        Integer code = null;

        if (lengh == 4){
            code = new Random().nextInt(9999);
            if (code < 1000)
                code = code + 1000;
        }else if (lengh == 6){
            code = new Random().nextInt(999999);
            if (code < 100000)
                code = code + 100000;
        }else
            throw new CustomException("只能生成4位或6位验证码");

        return code;
    }

    //随机生成指定产长度字符串验证码
    public static String generateValidate4String(int length){

        Random random = new Random();
        String hash = Integer.toHexString(random.nextInt());
        String capstr = hash.substring(0, length);

        return capstr;
    }
}
