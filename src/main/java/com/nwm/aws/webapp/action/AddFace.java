package com.nwm.aws.webapp.action;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.nwm.aws.webapp.model.Face;

public class AddFace {

	public List<Face> addGood(String imageName, byte[] bytes) {
		return run(App.GOOD_COLL_NAME, imageName, bytes);
	}
	
	public List<Face> addBad(String imageName, byte[] bytes) {
		return run(App.BAD_COLL_NAME, imageName, bytes);
	}
	
	public List<Face> run(String collectionId, String imageName, byte[] bytes) {
		AmazonRekognition rekognition = ClientFactory.createClient();
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

		IndexFacesRequest request = new IndexFacesRequest()
				.withCollectionId(collectionId)
				.withDetectionAttributes("ALL")
				.withImage(new Image().withBytes(byteBuffer))
				.withExternalImageId(imageName);
		
		IndexFacesResult result = rekognition.indexFaces(request);

		List<Face> faces = new ArrayList<>();
		result.getFaceRecords().stream().map(FaceRecord::getFace)
		.forEach(f -> {
			faces.add(
				Face.newBuilder()
				.setFaceId(f.getFaceId())
				.setExternalId(f.getExternalImageId())
				.setImageId(f.getImageId())
				.setConfidence(f.getConfidence())
				.build());
		});;
		
		return faces;
	}
	
	public String getString(List<Face> faces) {
    	StringBuilder sb = new StringBuilder();
    	faces.forEach(f -> {
    		sb.append(f.toString()).append("\\n");
    	});
    	return sb.toString();
    }
}
