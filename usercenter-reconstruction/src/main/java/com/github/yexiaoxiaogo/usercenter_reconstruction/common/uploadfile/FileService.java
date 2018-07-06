/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: uoAK3FAdGPsqONYCWV6xymkERDP8B29odbqVJEPK8/MGQACbOLJtiQ==
 */
package com.github.yexiaoxiaogo.usercenter_reconstruction.common.uploadfile;

import org.springframework.web.multipart.MultipartFile;


/**
 * Service - 文件
 * 
 * @author SHOP++ Team
 * @version 6.0
 */
public interface FileService {
	
	/** 
	 * @Description:  重要文件上传
	 * @param fileType
	 * @param multipartFile 
	 * @param async 是否异步
	 * @return
	 * String 
	 * @throws  
	 * @author LiuJiaPeng
	 * @date 2018年4月17日 下午1:54:45
	 */ 
	String uploadImportant(FileType fileType,UploadType uploadType,MultipartFile multipartFile);

}