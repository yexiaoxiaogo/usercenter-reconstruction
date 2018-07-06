package com.github.yexiaoxiaogo.usercenter_reconstruction.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.yexiaoxiaogo.usercenter_reconstruction.common.utils.DealException;




/**
 * @Description AOP - controller异常日志处理切面
 * @Date 2018年6月11日 下午10:07:39
 * @Author LiuJiaPeng
 */
@Aspect
@Component
public class GlobalException {
	
	/** 
	 * Description:  controller包下的类的方法被调用的切点
	 * void 
	 * @throws  
	 * @Author LiuJiaPeng
	 * @date 2018年6月11日 下午10:11:21
	 */ 
	@Pointcut("execution(* com.dp.springboot.controller..*.*(..))")
	public void pointcut(){} 
	
	@Around(value="pointcut()")
	public Object around(ProceedingJoinPoint p) throws Throwable{
		Object obj = null;
		try {
			//执行前
			obj = p.proceed();
			//执行后
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			//抛异常
			DealException.printStackTrace((Exception)e);
			throw e;
		}
		return obj;
	}
	
}
