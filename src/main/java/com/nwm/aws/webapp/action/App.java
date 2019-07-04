package com.nwm.aws.webapp.action;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

	public static final String GOOD_COLL_NAME = "goodCollection";
	public static final String BAD_COLL_NAME = "badCollection";
	public static final Float BAD_DETECTION_MARGIN = 50.0f;
	public static final Float GOOD_DETECTION_MARGIN = 40.0f;
	public static final String MSG_NOT_DETECTION = "Face is not detected";


	private static final String QUERY = "detect-labels";
	private static final String ARG_ONE = "img\\akash1.jpg";
	private static final String ARG_TWO = "img\\kamal.jpg";
	private static final String ARG_THREE = "";

	public static void main(String[] args) throws InterruptedException {
		//args = new String[]{"list-collections","goodCollection","img\\kamal.jpg"};

		switch (QUERY) {
		case "create-collection":
			CreateCollection cc = new CreateCollection();
			System.out.println(cc.run(ARG_ONE));
			break;
		case "list-collections":
			ListCollections lc = new ListCollections();
			System.out.println(lc.getString(lc.run()));
			break;
		case "delete-collection":
			DeleteCollection dc = new DeleteCollection();
			System.out.println(dc.run(ARG_ONE));
			break;
		case "describe-collection":
			DescribeCollection descc = new DescribeCollection();
			System.out.println(descc.run(ARG_ONE));
			break;
		case "add-face":
			AddFace agf = new AddFace();
			System.out.println(agf.getString(agf.run(ARG_ONE, ARG_TWO, getImageBytes(ARG_THREE))));
			break;
		case "detect-face":
			DetectFace df = new DetectFace();
			System.out.println(df.getString(df.detectFace(ARG_ONE, getImageBytes(ARG_TWO))));
			break;
		case "detect-labels":
			DetectLabels dl = new DetectLabels();
			System.out.println(dl.getString(dl.run(getImageBytes(ARG_ONE))));
			break;
		case "detect-face-details":
			DetectFaceDetails dfd = new DetectFaceDetails();
			System.out.println(dfd.getString(dfd.run(getImageBytes(ARG_ONE))));
			break;
			
			/*case "detect-labels-video":
            DetectLabelsVideo detectLabelsVideo = new DetectLabelsVideo();
            detectLabelsVideo.run(args);
            break;
			case "track-persons":
            TrackPersons trackPersons = new TrackPersons();
            trackPersons.run(args);
            break;*/
		default:
			System.err.println("Unknown argument: " + QUERY);
			return;
		}
	}

	private static byte[] getImageBytes(String imgPath) {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(imgPath));
		} catch (IOException e) {
			System.err.println("Failed to load image: " + e.getMessage());
		}
		return bytes;
	}
}
