<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String baseUrl = getServletContext().getInitParameter("BaseUrl");%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FaceRecog-WebApp</title>
    
    <!-- Webcam.min.js -->
    <script type="text/javascript" src="webcamjs/webcam.min.js"></script>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    
	<script type="text/javascript">
	 // Configure a few settings and attach camera
	 $(document).ready(function() {
		 Webcam.set({
	      width: 320,
	      height: 240,
	      dest_width: 320,
	      dest_height: 240,
	      image_format: 'jpeg',
	      jpeg_quality: 90,
	      force_flash: false
	    });
	    Webcam.attach( '#my_camera' );
	 });
    
    
    // preload shutter audio clip
    var shutter = new Audio();
    shutter.autoplay = true;
    shutter.src = navigator.userAgent.match(/Firefox/) ? 'res/shutter.ogg' : 'res/shutter.mp3';
    
    function freeze_snapshot() {
    	Webcam.freeze();
    }
    
    function take_snapshot() {
        // play sound effect
        shutter.play();
        // take snapshot and get image data
        Webcam.snap( function(data_uri) {
            // display results in page
            document.getElementById('my_camera').innerHTML = '<img src="'+data_uri+'"/>';
            upload_snapshot(data_uri, '<%=baseUrl%>api/detectFace');
        } );
    }
    
    function describe_snapshot() {
    	// play sound effect
        shutter.play();
        // take snapshot and get image data
        Webcam.snap( function(data_uri) {
            // display results in page
            document.getElementById('my_camera').innerHTML = '<img src="'+data_uri+'"/>';
            upload_snapshot(data_uri, '<%=baseUrl%>api/describeFace');
        } );
    }
    
    function describe_pic() {
    	// play sound effect
        shutter.play();
        // take snapshot and get image data
        Webcam.snap( function(data_uri) {
            // display results in page
            document.getElementById('my_camera').innerHTML = '<img src="'+data_uri+'"/>';
            upload_snapshot(data_uri, '<%=baseUrl%>api/describePic');
        } );
    }
    
    function add_good_face() {
    	if(document.getElementById("face_name").innerHTML=='') {
    		document.getElementById('results').innerHTML = 'Enter Unique Name to proceed.';
   		} else {
   		// play sound effect
   	        shutter.play();
   	        // take snapshot and get image data
   	        Webcam.snap( function(data_uri) {
   	            // display results in page
   	            document.getElementById('my_camera').innerHTML = '<img src="'+data_uri+'"/>';
   	            upload_snapshot(data_uri, '<%=baseUrl%>api/addGoodFace');
   	        } )
   		}
    }
    
	function add_bad_face() {
		if(document.getElementById("face_name").innerHTML=='') {
    		document.getElementById('results').innerHTML = 'Enter Unique Name to proceed.';
   		} else {
   		// play sound effect
   	        shutter.play();
   	        // take snapshot and get image data
   	        Webcam.snap( function(data_uri) {
   	            // display results in page
   	            document.getElementById('my_camera').innerHTML = '<img src="'+data_uri+'"/>';
   	            upload_snapshot(data_uri, '<%=baseUrl%>api/addBadFace');
   	        } )
   		}
    }
    
    function upload_snapshot(data_uri, uri) {
    	document.getElementById('results').innerHTML = 'Processing Request, Please wait..';
    	var ajax = new XMLHttpRequest();
    	ajax.onreadystatechange = function() {
   		  if (this.readyState == 4 && this.status == 200) {
   			document.getElementById('results').innerHTML = this.responseText;
		  } else {
		    document.getElementById('results').innerHTML = 'Request Failed. Please Retry.';
		  }
   		  reset_snapshot();
		};
		ajax.open("POST",uri,false); //Pass true for async
		ajax.send(data_uri);
    }
    
    function reset_snapshot() {
    	Webcam.reset();
    	Webcam.attach( '#my_camera' );
    }
    
    function uploadWebCam() {
   	  /* Webcam.upload( data_uri, 'upload/upload.php', function(code, text) {
       // Upload complete!
       // 'code' will be the HTTP response code from the server, e.g. 200
       // 'text' will be the raw response content
   		document.getElementById('results').innerHTML = code;
    } );  */
    }
	</script>

</head>
<body>
	<div id="my_camera" style="width: 320px; height: 240px; border:1px solid black"></div>
	<input type=button value="Take Snap" onClick="freeze_snapshot()" />
	<input type=button value="Retry" onClick="reset_snapshot()" />
	<br/>
	<input type=button value="Detect Face" onClick="take_snapshot()" />
	<input type=button value="Describe Face" onClick="describe_snapshot()" />
	<input type=button value="What's in Pic?" onClick="describe_pic()" />
	<br/>
	Enter Name: <input type="text" name="Name" id="face_name"/>
	<input type=button value="Add Good Face" onClick="add_good_face()" />
	<input type=button value="Add Bad Face" onClick="add_bad_face()" />
	<br/>
    <div id="results" ></div>
</body>
</html>
