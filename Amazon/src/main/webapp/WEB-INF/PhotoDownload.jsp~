<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <h1>Download your photo</h1>
   ${title}
   
   <body> 
    <form action="https://ivan-marchenko.s3.amazonaws.com/" method="post" enctype="multipart/form-data">
      <input type="hidden" id="filename" name="key" value="ivan.marchenko/${filename1}">
      <input type="hidden" name="AWSAccessKeyId" value="AKIAJ6VZXB5TVY23KREQ"> 
      <input type="hidden" name="acl" value="private"> 
      <input type="hidden" name="success_action_redirect" value="http://localhost:8080/Amazon">
      <input type="hidden" name="policy" value="${policy}">
      <input type="hidden" name="signature" value="${signature}">
      <input type="hidden" name="Content-Type" value="image/jpeg">
      <!-- Include any additional input fields here -->

      File to upload to S3: 
      <input name="file" id="file1" type="file"> 
      
     <script type="text/javascript">
     window.onload = function(){
    document.getElementById('file1').onchange = function(){
        var filename1 = this.value.substr(this.value.lastIndexOf('\\')+1);
        document.getElementById('filename').value = 'ivan.marchenko/' + filename1;        
     };
  };
    </script>      
      <br> 
      <input type="submit" value="Upload File to S3"> 
    </form> 
</body> 
</html>