package com.nwm.aws.webapp.service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.nwm.aws.webapp.action.AddFace;
import com.nwm.aws.webapp.action.App;
import com.nwm.aws.webapp.action.DescribeCollection;
import com.nwm.aws.webapp.action.DetectFace;
import com.nwm.aws.webapp.action.DetectFaceDetails;
import com.nwm.aws.webapp.action.DetectLabels;
import com.nwm.aws.webapp.action.ListCollections;
import com.nwm.aws.webapp.model.Face;

public class FaceDetectService {
	
	private static AtomicReference<FaceDetectService> instance = new AtomicReference<>();
	
	public static FaceDetectService getInstance() {
		instance.compareAndSet(null, new FaceDetectService());
		return instance.get();
	}
	
	public String describeGoodCollection () {
		DescribeCollection cc = new DescribeCollection();
		return cc.good().toString();
	}
	
	public String describeBadCollection () {
		DescribeCollection cc = new DescribeCollection();
		return cc.good().toString();
	}
	
	public String listCollections () {
		ListCollections cc = new ListCollections();
		return cc.getString(cc.run());
	}
	
	public String addGoodImage (String imageName, byte[] image) {
		AddFace cc = new AddFace();
		return cc.getString(cc.addGood(imageName, image));
	}
	
	public String addBadImage (String imageName, byte[] image) {
		AddFace cc = new AddFace();
		return cc.getString(cc.addBad(imageName, image));
	}
	
	public String detectFace (byte[] image) {
		DetectFace cc = new DetectFace();
		StringBuilder ret = new StringBuilder();
		
		Map<Face,Float> badResults = cc.detectBadFace(image);
		if(!badResults.isEmpty()) {
			ret.append("Alert: Blocked User Detected\\n");
			badResults.forEach((f,sm) -> {
				ret.append("Similarity:").append(sm).append("%;");
				ret.append(f.toString()).append("\\n");
			});
			return ret.toString();
		}
		
		Map<Face,Float> goodResults = cc.detectGoodFace(image);
		if(!goodResults.isEmpty()) {
			ret.append("Face Detected\\n");
			goodResults.forEach((f,sm) -> {
				ret.append("Similarity:").append(sm).append("%;");
				ret.append(f.toString()).append("\\n");
			});
			return ret.toString();
		}
		
		return App.MSG_NOT_DETECTION;
	}
	
	public String detectFaceDetails (byte[] image) {
		DetectFaceDetails cc = new DetectFaceDetails();
		return cc.getString(cc.run(image));
	}
	
	public String detectLabels (byte[] image) {
		DetectLabels cc = new DetectLabels();
		return cc.getString(cc.run(image));
	}
	
}
