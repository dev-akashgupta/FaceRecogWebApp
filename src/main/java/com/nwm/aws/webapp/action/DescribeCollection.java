package com.nwm.aws.webapp.action;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DescribeCollectionRequest;
import com.amazonaws.services.rekognition.model.DescribeCollectionResult;
import com.nwm.aws.webapp.model.Collection;

public class DescribeCollection {

	public Collection good() {
		return run(App.GOOD_COLL_NAME);
	}
	
	public Collection bad() {
		return run(App.BAD_COLL_NAME);
	}
	
	public Collection run(String collectionId) {

		DescribeCollectionRequest request = new DescribeCollectionRequest()
				.withCollectionId(collectionId);

		AmazonRekognition rekognition = ClientFactory.createClient();
		DescribeCollectionResult result = rekognition.describeCollection(request);

		return Collection.newBuilder()
				.setVersion(result.getFaceModelVersion())
				.setArn(result.getCollectionARN())
				.setCount(result.getFaceCount())
				.setDate(result.getCreationTimestamp())
				.build();
	}
}
