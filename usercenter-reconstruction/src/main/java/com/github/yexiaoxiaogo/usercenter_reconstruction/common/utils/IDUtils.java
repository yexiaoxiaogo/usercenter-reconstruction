package com.github.yexiaoxiaogo.usercenter_reconstruction.common.utils;

import java.util.UUID;

/**
 * uuid生成工具
 * 
 * @author hervoo
 *
 */
public class IDUtils {

    public static String getUUID() {
        // 获取32位长度uuid
        return UUID.randomUUID().toString().replace("-", "");
    }
}
