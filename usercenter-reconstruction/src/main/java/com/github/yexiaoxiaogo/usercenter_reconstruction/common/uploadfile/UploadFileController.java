package com.github.yexiaoxiaogo.usercenter_reconstruction.common.uploadfile;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaokong.core.base.Result;

@RestController
@RequestMapping("/file")
public class UploadFileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public Result uploadImportant(FileType fileType,UploadType uploadType,MultipartFile file){
		Map<String, Object> data = new HashMap<>();
		String url = fileService.uploadImportant(fileType,uploadType, file);
		if (StringUtils.isEmpty(url)) {
			return new Result<>().setData("common.upload.error");
		}

		data.put("url", url);
		System.out.println("图片返回地址："+url);
		return new Result<>().setData(data);
	}

}
