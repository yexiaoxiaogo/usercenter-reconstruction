package com.github.yexiaoxiaogo.usercenter_reconstruction.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description: 打印异常信息
 * @author LiuJiaPeng
 * @date 2017年11月28日 上午11:26:03
 */
public class DealException {
	
	private static Log logger = LogFactory.getLog(DealException.class);
	/**
	 * @Description: 打印异常堆栈信息
	 * @author LiuJiaPeng
	 * @date 2017年11月28日 上午11:27:23
	 * @param e
	 */
	public static void printStackTrace(Exception e){
		StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw =  new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        }finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
            logger.error(sw.toString());
        }
	}
	
	/**
	 * @Description: 打印错误信息
	 * @author LiuJiaPeng
	 * @date 2017年11月28日 上午11:28:12
	 * @param e
	 */
	public static void printMessage(Exception e){
		logger.error(e.getMessage());
	}
	
}
