package com.nwm.aws.webapp.model;

public class Face {

	public static FaceBuilder newBuilder() {
		Face face = new Face();
		return face.new FaceBuilder(face);
	}

	private String faceId;
	private String externalId;
	private String imageId;
	private Float confidence;
	
	private Face() {}
	public String getFaceId() {return faceId;}
	public String getExternalId() {return externalId;}
	public String getImageId() {return imageId;}
	public Float getConfidence() {return confidence;}
	public Face copy() {
		Face face = new Face();
		face.faceId = faceId;
		face.externalId = externalId;
		face.imageId = imageId;
		face.confidence = confidence;
		return face;
	}
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Face-Id:").append(faceId).append(";");
		res.append("Image-Id:").append(imageId).append(";");
		res.append("External-Image-Id:").append(externalId).append(";");
		res.append("Confidence:").append(confidence);
		return res.toString();
	}

	public class FaceBuilder {
		private final Face face;
		private FaceBuilder(Face face) {
			this.face = face;
		}
		public Face build() {
			return face.copy();
		}
		public FaceBuilder setFaceId(String val) {
			face.faceId = val;
			return this;
		}
		public FaceBuilder setExternalId(String val) {
			face.externalId = val;
			return this;
		}
		public FaceBuilder setImageId(String val) {
			face.imageId = val;
			return this;
		}
		public FaceBuilder setConfidence(Float val) {
			face.confidence = val;
			return this;
		}
	}
}
