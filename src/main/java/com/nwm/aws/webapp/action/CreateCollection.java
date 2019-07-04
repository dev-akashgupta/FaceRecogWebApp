package com.nwm.aws.webapp.action;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.nwm.aws.webapp.model.Collection;

public class CreateCollection {

	public Collection run(String collectionName) {
		CreateCollectionRequest request = new CreateCollectionRequest()
				.withCollectionId(collectionName);

		AmazonRekognition rekognition = ClientFactory.createClient();
		CreateCollectionResult result = rekognition.createCollection(request);

		return Collection.newBuilder()
				.setStatusCode(result.getStatusCode())
				.setArn(result.getCollectionArn())
				.setVersion(result.getFaceModelVersion())
				.build();
	}
}
