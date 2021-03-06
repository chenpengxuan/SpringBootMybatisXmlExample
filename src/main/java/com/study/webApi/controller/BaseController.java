package com.study.webApi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.study.infrastructure.extension.HttpRequestUtility;
import com.study.infrastructure.exception.ForbiddenException;
import com.study.infrastructure.extension.Base64DecodedMultipartFile;

public class BaseController {
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	protected byte[] getRequestPostBytes(HttpServletRequest request) {
		if(request == null) {
			return null;
		}
		byte[] content = null;
		
		try {
			content = HttpRequestUtility.getRequestPostBytes(request);
		} catch (IOException e) {
			log.error("An error occured while get post");
		}
		
		return content;
	}
	
	protected MultipartFile getMultipartFileFromRequest(HttpServletRequest request) {
		if(request == null) {
			return null;
		}
		byte[] content = this.getRequestPostBytes(request);
		
		if(content == null || content.length == 0) {
			return null;
		}
		MultipartFile file = new Base64DecodedMultipartFile(content);
		return file;
	}
	
	protected String getRequestPostString(HttpServletRequest request) throws IOException {
		if(request == null) {
			return null;
		}
		return HttpRequestUtility.getRequestPostStr(request);
	}
	
	protected String getRequestPostData(HttpServletRequest request) throws ForbiddenException{
		if(request == null) {
			throw new ForbiddenException();
		}
		Object bodyData = request.getAttribute("bodyData");
		String data = (bodyData == null)?null:(String)bodyData;
		
		if(data == null || data.isEmpty()) {
			try {
				data = this.getRequestPostString(request);
			} catch (IOException e) {
				log.error("An error occures while retriving post data " + e);
				throw new ForbiddenException();
			}
		}
		return data;
	}
	
	protected String getRemoteHost(HttpServletRequest request) {
		return HttpRequestUtility.getRemoteHost(request);
	}
}
