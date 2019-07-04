package com.nwm.aws.webapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nwm.aws.webapp.service.FaceDetectService;

@Controller
public class FaceDetectController {

	public static final String MSG_FAIL = "Request cannot be proceessed, Please try again.";
	
	@RequestMapping(value = "/detectFace", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String detectFace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("detectFace()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
		
		byte[] bytes = getByteFromStream(wrappedRequest.getInputStream());
		if(bytes == null) {
			return MSG_FAIL;
		}
		
        String ret = FaceDetectService.getInstance().detectFace(bytes); 
        if(ret==null || ret.isEmpty()) {
        	ret = MSG_FAIL;
        }
        
        System.out.println("detectFace() ret:"+ ret);
		return ret;
	}
	
	@RequestMapping(value = "/describeFace", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String describeFace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("describeFace()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
		
		byte[] bytes = getByteFromStream(wrappedRequest.getInputStream());
		if(bytes == null) {
			return MSG_FAIL;
		}
		
        String ret = FaceDetectService.getInstance().detectFaceDetails(bytes); 
        if(ret==null || ret.isEmpty()) {
        	return MSG_FAIL;
        }

        System.out.println("describeFace() ret:"+ ret);
		return ret;
	}
	
	@RequestMapping(value = "/describePic", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String describePic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("describePic()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
		
		byte[] bytes = getByteFromStream(wrappedRequest.getInputStream());
		if(bytes == null) {
			return MSG_FAIL;
		}
		
        String ret = FaceDetectService.getInstance().detectLabels(bytes); 
        if(ret==null || ret.isEmpty()) {
        	return MSG_FAIL;
        }
        
        System.out.println("describePic() ret:"+ ret);
		return ret;
	}
	
	@RequestMapping(value = "/addGoodFace", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String addGoodFace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("addGoodFace()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
		
		byte[] bytes = getByteFromStream(wrappedRequest.getInputStream());
		if(bytes == null) {
			return MSG_FAIL;
		}
		
        String ret = FaceDetectService.getInstance().addGoodImage("RANDOM"+Math.random(), bytes); 
        if(ret==null || ret.isEmpty()) {
        	return MSG_FAIL;
        }
        
        System.out.println("addGoodFace() ret:"+ ret);
		return ret;
	}
	
	@RequestMapping(value = "/addBadFace", method = RequestMethod.POST, produces = {"application/json"})
	public @ResponseBody String addBadFace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("addBadFace()" );
		
		HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
		
		byte[] bytes = getByteFromStream(wrappedRequest.getInputStream());
		if(bytes == null) {
			return MSG_FAIL;
		}
		
        String ret = FaceDetectService.getInstance().addBadImage("RANDOM"+Math.random(), bytes); 
        if(ret==null || ret.isEmpty()) {
        	return MSG_FAIL;
        }
        
        System.out.println("addBadFace() ret:"+ ret);
		return ret;
	}
	
	
	private byte[] getByteFromStream(InputStream stream) {
		StringWriter writer = new StringWriter();
        try {
			IOUtils.copy(stream, writer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        String imageString = writer.toString();
        imageString = imageString.split(",")[1];
        
        return Base64Utils.decodeFromString(imageString);
	}
	
}
