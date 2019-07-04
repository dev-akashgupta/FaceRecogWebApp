package com.nwm.aws.webapp.action;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest;
import com.amazonaws.services.rekognition.model.DeleteCollectionResult;

public class DeleteCollection {

    public Integer run(String collectionId) {
        
        DeleteCollectionRequest request = new DeleteCollectionRequest()
                .withCollectionId(collectionId);
        AmazonRekognition rekognition = ClientFactory.createClient();
        DeleteCollectionResult result = rekognition.deleteCollection(request);
        
        return result.getStatusCode();
    }
}
