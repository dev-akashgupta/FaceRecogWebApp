package com.nwm.aws.webapp.action;

import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AgeRange;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.Beard;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Emotion;
import com.amazonaws.services.rekognition.model.EyeOpen;
import com.amazonaws.services.rekognition.model.Eyeglasses;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Gender;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.ImageQuality;
import com.amazonaws.services.rekognition.model.MouthOpen;
import com.amazonaws.services.rekognition.model.Mustache;
import com.amazonaws.services.rekognition.model.Smile;
import com.amazonaws.services.rekognition.model.Sunglasses;

public class DetectFaceDetails {

	public List<FaceDetail> run(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		AmazonRekognition rekognition = ClientFactory.createClient();
		DetectFacesRequest request = new DetectFacesRequest()
				.withImage(new Image().withBytes(byteBuffer))
				.withAttributes(Attribute.ALL);
		DetectFacesResult result = rekognition.detectFaces(request);
		return result.getFaceDetails();
	}

	public String getString(List<FaceDetail> fds) {
		StringBuilder res = new StringBuilder();
		fds.forEach(fd -> {
			append(res, "FaceMatch", fd.getConfidence());

			AgeRange ageRange = fd.getAgeRange();
			append(res, "AgeRange", ageRange.getLow()+ "-" + ageRange.getHigh());

			Beard beard = fd.getBeard();
			append(res, "Beard", beard.getValue(), beard.getConfidence());

			List<Emotion> emotions = fd.getEmotions();
			for (Emotion emotion : emotions) {
				append(res, "Emotion", emotion.getType(), emotion.getConfidence());
			}

			Eyeglasses eyeglasses = fd.getEyeglasses();
			append(res, "Eyeglasses", eyeglasses.getValue(), eyeglasses.getConfidence());

			EyeOpen eyesOpen = fd.getEyesOpen();
			append(res, "EyeOpen", eyesOpen.getValue(), eyesOpen.getConfidence());

			Gender gender = fd.getGender();
			append(res, "Gender", gender.getValue(), gender.getConfidence());

			MouthOpen mouthOpen = fd.getMouthOpen();
			append(res, "MouthOpen", mouthOpen.getValue(), mouthOpen.getConfidence());

			Mustache mustache = fd.getMustache();
			append(res, "Mustache", mustache.getValue(), mustache.getConfidence());

			ImageQuality quality = fd.getQuality();
			append(res, "ImageQuality", "bright-" + quality.getBrightness() + "/sharp-" + quality.getSharpness());

			Smile smile = fd.getSmile();
			append(res, "Smile", smile.getValue(), smile.getConfidence());

			Sunglasses sunglasses = fd.getSunglasses();
			append(res, "Sunglasses", sunglasses.getValue(), sunglasses.getConfidence());
		});
		return res.toString();
	}

	private void append(StringBuilder res, String feature, Float value) {
		res.append(feature).append(":").append(value).append("\\n");
	}

	private void append(StringBuilder res, String feature, String value) {
		res.append(feature).append(":").append(value).append("\\n");
	}

	private void append(StringBuilder res, String feature, String value, Float confidence) {
		res.append(feature).append(":").append(value).append("[").append(confidence).append("]\\n");
	}

	private void append(StringBuilder res, String feature, Boolean value, Float confidence) {
		res.append(feature).append(":").append(value).append("[").append(confidence).append("]\\n");
	}
}
