package amazon.servlets;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PhotoDownloadServlet  extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String aws_secret_key = "sikret_Acses_key";
		String policy_document = "{'expiration': '2015-03-01T00:00:00Z',"+
				  "'conditions': ["+ 
				    "{'bucket': 'ivan-marchenko'},"+ 
				    "['starts-with', '$key', 'ivan.marchenko/'],"+
				    "{'acl': 'private'},"+
				    "{'success_action_redirect': 'http://localhost:8080/Amazon'},"+
				    "['starts-with', '$Content-Type', ''],"+
				    "['content-length-range', 0, 1048576]  ]}";
						policy_document.replaceAll("'", "\"");
					    String policy = (new BASE64Encoder()).encode(
					    	    policy_document.getBytes("UTF-8")).replaceAll("\n","").replaceAll("\r","");

							Mac hmac = null;
							try {
								hmac = Mac.getInstance("HmacSHA1");
								hmac.init(new SecretKeySpec(
									    aws_secret_key.getBytes("UTF-8"), "HmacSHA1"));
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							String signature = (new BASE64Encoder()).encode(
							    hmac.doFinal(policy.getBytes("UTF-8")))
							    .replaceAll("\n", "");
					      
						request.setAttribute("policy", policy);
						request.setAttribute("signature", signature);
						request.setAttribute("filename", "test.png");
						
						request.getRequestDispatcher("/WEB-INF/PhotoDownload.jsp").forward(request, response);
					}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
