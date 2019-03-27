import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {
	static String fname;
	static int count; 
	public static void main(String[] args) {
		Properties properties = new Properties();
		String properties_path = System.getProperty("user.dir");
		FileInputStream input;
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		try {
			input = new FileInputStream(properties_path+File.separator+"propfile.properties");
			properties.load(input);
			
			Date date = new Date();	
			String date1 = dateFormat.format(date);
			
			String destinationPath = properties.getProperty("destinationFilePath");
			String sourcePath = properties.getProperty("sourceFilePath");
			String fromTime = properties.getProperty("fromTime");
			String toTime = properties.getProperty("toTime");
			
			String from1 = date1+" "+fromTime;
			String to1 = date1+" "+toTime;
			
			Date from = format2.parse(from1);
			Date to = format2.parse(to1);

	    	File files = new File(sourcePath);
	    	boolean a=false;
	    	for (File f:files.listFiles()) {
	    		if (f.isFile()) {
	    			fname = f.getName();
	    			a=false;
	    			String mod = format2.format(f.lastModified());
	    			Date modDate = format2.parse(mod);
	   
	    			if(modDate.equals(from) || modDate.equals(to)) {
	    				a=true;
	    			}
	    			else if(modDate.after(from) && modDate.before(to)) {
	    				a=true;
	    			}
	    			if(a){
		    			File tempf= new File(destinationPath+File.separator+fname);
						if(tempf.exists()) {
							tempf.delete();
							System.out.println("done");
						}
						Path temp = Files.copy(Paths.get(sourcePath+File.separator+fname), Paths.get(destinationPath+File.separator+fname));
						if (temp != null) {
							System.out.println("File "+fname+" copied to destination folder successfully");
							count = count+1;
						} else {
							System.out.println("Failed to copy the file");
						}
		    		}
	    		}
	    	}
	    	System.out.println("Count: "+count);
	    	String sub = "Daily Task|"+count+" files copied";
			String msg = count+" files copied";
			Mail(sub,msg);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static void Mail(String subject1,String message1){
	  Properties prop = new Properties();
	  InputStream input = null;
	  String propPath = System.getProperty("user.dir");
	  try {
		input = new FileInputStream(propPath+File.separator+"propfile.properties");
		prop.load(input);
	  } catch (Exception e) {
		e.printStackTrace();
	  }
		
	  String host = prop.getProperty("mail.smtp.host");
	  String to = prop.getProperty("smtp.toEmail");
	  String from = prop.getProperty("smtp.fromEmail");
	  
	  Properties property = new	Properties();
	  property.put("mail.smtp.host",host);
	  
      Session session = Session.getDefaultInstance(property);  
  
     //compose the message  
      try{  
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));  
         message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
         message.setSubject(subject1);  
         message.setText(message1);  
  
         // Send message  
         Transport.send(message);  
         System.out.println("message sent successfully....");  
      }catch (MessagingException mex) {
    	  mex.printStackTrace();
      }  
   }
}
