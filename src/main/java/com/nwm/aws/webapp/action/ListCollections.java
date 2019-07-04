package com.nwm.aws.webapp.action;

import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;

public class ListCollections {

    public List<String> run() {
        ListCollectionsRequest request = new ListCollectionsRequest()
                .withMaxResults(100);
        
        AmazonRekognition rekognition = ClientFactory.createClient();
        ListCollectionsResult result = rekognition.listCollections(request);
        
		return result.getCollectionIds();
    }
    
    public String getString(List<String> list) {
    	StringBuilder res = new StringBuilder();
    	list.forEach(l -> res.append(l).append(";"));
    	return res.toString();
    }
    
}
