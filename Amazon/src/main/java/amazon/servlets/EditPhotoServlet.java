package amazon.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadPartRequest;



public class EditPhotoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String filename = request.getParameter("hd");
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("Acses_key", "sikret_Acses_key");
		AmazonS3Client s3Client = new AmazonS3Client(awsCreds);

		//This is where the downloaded file will be saved
		File localFile = new File(filename.replace("ivan.marchenko/", ""));

		//This returns an ObjectMetadata file but you don't have to use this if you don't want 
		s3Client.getObject(new GetObjectRequest("ivan-marchenko", filename), localFile);

		//Now your file will have your image saved 
		boolean success = localFile.exists() && localFile.canRead();
		String filePath = localFile.getPath();
//		response.sendRedirect("/BazaStudentow/StudentsListServlet");
	      // Actual logic goes here.
		request.setAttribute("title", "Edit Photo");
		request.setAttribute("filePath",filePath);
		request.setAttribute("filename", filename);
		request.getRequestDispatcher("/WEB-INF/EditPhoto.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filename = request.getParameter("hd");
		String action = request.getParameter("Delet");
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("Acses_key", "sikret_Acses_key");
		AmazonS3 s3client = new AmazonS3Client(awsCreds);
		if("Rotate".equals(action))
		{
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new File(filename.replace("ivan.marchenko/", "")));
			} catch (IOException e) {
			}
			if(img != null){
            int width = img.getWidth();
            int height = img.getHeight();
            BufferedImage newImage = new BufferedImage(width, height,img.getType());
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    newImage.setRGB( x, height-y-1, img.getRGB(x, y));
                }
        }
            img = newImage;
            File outputfile = new File("image.jpg");
            ImageIO.write(img, "jpg", outputfile);
            s3client.putObject(new  PutObjectRequest("ivan-marchenko/", "ivan.marchenko/", outputfile));
    }}
		       
//		s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
		if("Delet".equals(action))
		{
			s3client.deleteObject(new DeleteObjectRequest("ivan-marchenko",filename));
		}
		response.sendRedirect("/Amazon/");
	}

}
