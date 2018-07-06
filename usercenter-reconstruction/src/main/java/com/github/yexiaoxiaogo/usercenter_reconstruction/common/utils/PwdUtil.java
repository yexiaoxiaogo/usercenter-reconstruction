package com.github.yexiaoxiaogo.usercenter_reconstruction.common.utils;

/**
 * 随机密码生成工具(包含英文大小写、数字)
 * 
 * @author hervoo
 *
 */
public class PwdUtil {

    public static String getPwd(int length) {
        char[] pwd = new char[length];
        int i = 0;
        while (i < length) {
            int f = (int) (Math.random() * 3);
            if (f == 0)
                pwd[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                pwd[i] = (char) ('a' + Math.random() * 26);
            else
                pwd[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        return new String(pwd);
    }
}
