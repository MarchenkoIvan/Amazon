package amazon.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AllPhotosServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		List<String>filelist = new ArrayList<String>();
//		ProfileCredentialsProvider pcp = new ProfileCredentialsProvider();
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("Acses_key", "sikret_Acses_key");
		AmazonS3 s3client = new AmazonS3Client(awsCreds);        

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
		    .withBucketName("ivan-marchenko")
		    .withPrefix("ivan.marchenko");
		ObjectListing objectListing;

		do {
		        objectListing = s3client.listObjects(listObjectsRequest);
		        for (S3ObjectSummary objectSummary : 
		            objectListing.getObjectSummaries()) {
		        	filelist.add(objectSummary.getKey());		            
		        }
		        listObjectsRequest.setMarker(objectListing.getNextMarker());
		} while (objectListing.isTruncated());
		
		

	      // Actual logic goes here.
		request.setAttribute("title", "AllPhotos");
		request.setAttribute("filelist", filelist);
		request.getRequestDispatcher("/WEB-INF/AllPhotos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}
