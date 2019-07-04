package com.nwm.aws.webapp.action;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.nwm.aws.webapp.model.Face;

public class DetectFace {
	
	private static final String DEFAULT_COLL_NAME = App.GOOD_COLL_NAME;
	
	public Map<Face,Float> detectGoodFace(byte[] bytes) {
		return run(App.GOOD_COLL_NAME, bytes, App.GOOD_DETECTION_MARGIN);
	}
	
	public Map<Face,Float> detectBadFace(byte[] bytes) {
		return run(App.BAD_COLL_NAME, bytes, App.BAD_DETECTION_MARGIN);
	}
	
	public Map<Face,Float> detectFace(byte[] bytes) {
		return run(DEFAULT_COLL_NAME, bytes, 0.0f);
	}
	
	public Map<Face,Float> detectFace(String collectionId, byte[] bytes) {
		return run(collectionId, bytes, 0.0f);
	}

    private Map<Face,Float> run(String collectionId, byte[] bytes, Float margin) {
        ByteBuffer byteBuffer= ByteBuffer.wrap(bytes);
        SearchFacesByImageRequest request = new SearchFacesByImageRequest()
                .withCollectionId(collectionId)
                .withImage(new Image().withBytes(byteBuffer));
        
        AmazonRekognition rekognition = ClientFactory.createClient();
        SearchFacesByImageResult result = rekognition.searchFacesByImage(request);
        
        Map<Face,Float> resultFaces = new HashMap<>();
        result.getFaceMatches()
        .stream()
        .filter(fm -> fm.getSimilarity()>=margin)
        .forEach(fm -> {
        	Face f = Face.newBuilder()
        	.setFaceId(fm.getFace().getFaceId())
        	.setExternalId(fm.getFace().getExternalImageId())
        	.setImageId(fm.getFace().getImageId())
        	.setConfidence(fm.getFace().getConfidence()).build();
        	resultFaces.put(f,fm.getSimilarity());
        });
        
		return resultFaces;
    }
    
    public String getString(Map<Face,Float> resultFaces) {
    	StringBuilder ret = new StringBuilder();
    	resultFaces.forEach((f,sm) -> {
			ret.append("Similarity:").append(sm).append("%;");
			ret.append(f.toString()).append("\\n");
		});
    	return ret.toString();
    }

}
