/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: uLN3ZftudyVI5FyT0ukdZjZMp5HuRseJPFwUeFEP26G6uY035kTyfw==
 */
package com.github.yexiaoxiaogo.usercenter_reconstruction.common.uploadfile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * Service - 文件
 * 
 * @author SHOP++ Team
 * @version 6.0
 */
@Service
public class FileServiceImpl implements FileService {

	public void upload(String path, File file, String contentType) {

		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		String accessId = "LTAIpRjAKycpZWpS";
		String accessKey = "J25dX5NZ3EIOiTElRnGNi0m76fJIfx";
		String bucketName = "15kong-upload";
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentLength(file.length());
			ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	public String getUrl(String path) {

		String urlPrefix = "http://upload.15kong.com";
		return urlPrefix + path;

	}

	@Override
	public String uploadImportant(FileType fileType, UploadType uploadType, MultipartFile multipartFile) {
		Assert.notNull(fileType, "[Assertion failed] - fileType is required; it must not be null");
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");

		StringBuffer uploadPath = new StringBuffer("/" + uploadType.name().toLowerCase());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String datePath = sdf.format(new Date());
		Map<String, Object> model = new HashMap<>();
		model.put("uuid", String.valueOf(UUID.randomUUID()));
		switch (fileType) {
		case MEDIA:
			uploadPath.append("/media/");
			break;
		case FILE:
			uploadPath.append("/file/");
			break;
		default:
			uploadPath.append("/image/");
			break;
		}
		uploadPath.append(datePath).append("/").append(UUID.randomUUID()).append(".")
				.append(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		try {
			String destPath = uploadPath.toString();
			File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
			multipartFile.transferTo(tempFile);
			String contentType = multipartFile.getContentType();
			upload(destPath, tempFile, contentType);
			
			return getUrl(destPath);

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}

}