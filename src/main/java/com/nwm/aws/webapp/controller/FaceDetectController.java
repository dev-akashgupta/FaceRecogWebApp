package com.nwm.aws.webapp.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaceDetectController {

	@RequestMapping(value = "/detectFace", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String detectFace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("detectFace()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
        InputStream stream = wrappedRequest.getInputStream();
        byte[] bytes = IOUtils.toByteArray(stream);
        
        // send bytes over webapps
        
        System.out.println(new String(Base64Utils.encode(bytes)));
        /*StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer, "UTF-8");
        String imageString = writer.toString();*/
        
		return "success";
	}
}
