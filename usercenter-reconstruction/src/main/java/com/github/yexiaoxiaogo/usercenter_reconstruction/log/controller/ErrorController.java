package com.github.yexiaoxiaogo.usercenter_reconstruction.log.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaokong.core.base.Result;

/**
 * @Description controller面向用户统一异常处理
 * @Date 2018年6月11日 下午11:37:26
 * @Author LiuJiaPeng
 */
@RequestMapping(value="${server.error.path:${error.path:/error}}")
@Controller
public class ErrorController extends AbstractErrorController{
	
	@Value("${server.error.path}")
	private String errorPath="/error";
	private Log log = LogFactory.getLog(ErrorController.class);
	
	public ErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return errorPath;
	}
	
	@RequestMapping
	@ResponseBody
	public Result errorData(HttpServletRequest req,HttpServletResponse res){
		HttpStatus status = getStatus(req);
		String msg = "[HttpStatus Code : " + status.value() + " , reasonPhrase : " + status.getReasonPhrase() + "]";
		log.warn(msg);
//		Map<String,Object> errorMsg = getErrorAttributes(req, true);
//		log.error(FastjsonUtils.obj2JsonString(errorMsg));
		return new Result<>(msg);
	}
}
