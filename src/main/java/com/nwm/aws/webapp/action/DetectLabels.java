package com.nwm.aws.webapp.action;

import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;

public class DetectLabels {

    public List<Label> run(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        AmazonRekognition rekognition = ClientFactory.createClient();

        DetectLabelsRequest request = new DetectLabelsRequest().withImage(new Image().withBytes(byteBuffer)).withMaxLabels(10);
        DetectLabelsResult result = rekognition.detectLabels(request);

        return result.getLabels();
    }
    
    public String getString(List<Label> labels) {
    	StringBuilder sb = new StringBuilder();
    	labels.forEach(lb -> {
    		sb.append(lb.getName()).append("[").append(lb.getConfidence()).append("%]").append("\\n");
    	});
    	return sb.toString();
    }

}
