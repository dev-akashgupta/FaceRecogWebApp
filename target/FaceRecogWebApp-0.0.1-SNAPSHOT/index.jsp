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
            upload_snapshot();
        } );
    }
    
    function upload_snapshot() {
    	document.getElementById('results').innerHTML = '<%=baseUrl%>api/startFaceRecognition';
    	
		$.ajax({
            url: '<%=baseUrl%>api/startFaceRecognition',
            type: "POST",
            data: new FormData(document.getElementById("imageForm")),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false
          }).done(function(data) {
        	  document.getElementById('results').innerHTML = data;
          }).fail(function(jqXHR, textStatus) {
        	  //alert(jqXHR.responseText);
        	  // document.getElementById('results').innerHTML = textStatus;
          });
		
    	/*  Webcam.upload( data_uri, 'upload/upload.php', function(code, text) {
             // Upload complete!
             // 'code' will be the HTTP response code from the server, e.g. 200
             // 'text' will be the raw response content
         	document.getElementById('results').innerHTML = code;
         } ); */
    }
    
    function reset_snapshot() {
    	//document.getElementById('my_camera').innerHTML = '';
    	Webcam.reset();
    	Webcam.attach( '#my_camera' );
    }
	</script>

</head>
<body>
  <form id="imageForm">
	<div id="my_camera" style="width: 320px; height: 240px; border:1px solid black"></div>
	<input type=button value="Freeze" onClick="freeze_snapshot()" />
	<input type=button value="Recognize" onClick="take_snapshot()" />
	<input type=button value="Reset" onClick="reset_snapshot()">
    <div id="results" ></div>
  </form>
</body>
</html>
