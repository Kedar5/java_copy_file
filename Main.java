import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;
 
public class Main {
	static String var = new String();
	static String filepath = new String();
	static String from;
	static String to;
	static String date_1;
	static String date2;
	static boolean key;
    public static void main(String a[]){
       
		System.out.println("Enter the time from time and to time");
    	Scanner scanner = new Scanner(System.in);
    	from = scanner.next();
    	to = scanner.next();
    	System.out.println("Enter the time date");
    	date_1 = scanner.next();
    	
    	filepath = "C:\\ok\\";
        File file = new File(filepath);
        File[] files = file.listFiles();
        for(File f: files){
        	try {
        		var = f.getName();
//            System.out.println(f.getPath());
//            System.out.println(filepath+var);
        	  Process proc = Runtime.getRuntime().exec("cmd /c dir C:\\ok\\"+var+" /tc");
        	
        	    		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        	    		
        	    		String data ="";
        	    		
        	    		//it's quite stupid but work
        	    		for(int i=0; i<6; i++){
        	    			data = br.readLine();
        	    		}
        	    		
//        	    		System.out.println("Extracted value : " + data);
        	    		
        	    		//split by space
        	    		StringTokenizer st = new StringTokenizer(data);
        	    		String date = st.nextToken();//Get date
        	    		date2 = date;
        	    		String time = st.nextToken();//Get time

        	    		String input = time;
        	    	      //Format of the date defined in the input String
        	    	      DateFormat df = new SimpleDateFormat("hh:mm");
        	    	      //Desired format: 24 hour format: Change the pattern as per the need
        	    	      DateFormat outputformat = new SimpleDateFormat("HH:mm");
        	    	      java.util.Date date1 = null;
        	    	      String output = null;
        	    	      try{
        	    	         //Converting the input String to Date
        	    	    	 date1= df.parse(input);
        	    	         //Changing the format of date and storing it in String
        	    	    	 output = outputformat.format(date1);
        	    	         //Displaying the date
//        	    	    	 System.out.println(output);
        	    	      }catch(ParseException pe){
        	    	         pe.printStackTrace();
        	    	       }
        	    		
//        	    		System.out.println("Creation Date  : " + date1);
//        	    		System.out.println("Creation Time  : " + output);
        	    		
        	    		boolean key = isTimeBetweenTwoTime(from,to,output+":00");
//        	        	System.out.println(key);
//        	        	System.out.println(date_1);
//        	        	System.out.println(date);
//        	    		String var1 = var+date_1;
        	    		if ((key == true) & (date_1.contentEquals(date))) {
        	    			Path temp = Files.move(Paths.get("C:\\ok\\"+var),  Paths.get("C:\\ok1\\"+var)); 
        	    			        if(temp != null) 
        	    			        { 
        	    			            System.out.println("File moved successfully"); 
        	    			        } 
        	    			        else
        	    			        { 
        	    			            System.out.println("Failed to move the file"); 
        	    			        } 
        	    		}
        	    	}catch(Exception e){
        	
        	    		e.printStackTrace();
        	
        	    	}
        	System.out.println("-----------------------------");
        }
    }


public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {

	    String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
	    if (initialTime.matches(reg) && finalTime.matches(reg) &&  currentTime.matches(reg)) 
	    {
	        boolean valid = false;
	        //Start Time
	        //all times are from java.util.Date
	        java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
	        Calendar calendar1 = Calendar.getInstance();
	        calendar1.setTime(inTime);

	        //Current Time
	        java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
	        Calendar calendar3 = Calendar.getInstance();
	        calendar3.setTime(checkTime);

	        //End Time
	        java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
	        Calendar calendar2 = Calendar.getInstance();
	        calendar2.setTime(finTime);

	        if (finalTime.compareTo(initialTime) < 0) 
	        {
	            calendar2.add(Calendar.DATE, 1);
	            calendar3.add(Calendar.DATE, 1);
	        }

	        java.util.Date actualTime = calendar3.getTime();
	        if ((actualTime.after(calendar1.getTime()) || 
	             actualTime.compareTo(calendar1.getTime()) == 0) && 
	             actualTime.before(calendar2.getTime())) 
	        {
	            valid = true;
	            return valid;
	        }
	    }
		return false;
	}
}
