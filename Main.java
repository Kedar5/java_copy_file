import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileExistsException;

public class Main {
	static String var = new String();
	static String filepath = new String();
	static String from;
	static String var1;
	static String to;
	static String date_1;
	static String date2;
	static String path123;
	static boolean key;

	public static void main(String a[]) throws IOException, ParseException {

		System.out.println("Enter the time from time and to time");
		Scanner scanner = new Scanner(System.in);
		from = scanner.next();//hh:mm:ss
		to = scanner.next();//hh:mm:ss
		System.out.println("Enter the time date");
		date_1 = scanner.next();//mon/day/year 

		filepath = "C:\\ok\\";
		File file = new File(filepath);
		File[] files = file.listFiles();
		File directory = new File("C:\\ok\\");
		int fileCount = directory.list().length;
		System.out.println("File Count: " + fileCount);
		int m = fileCount;
		// for repeated file names
		while (m != 0) {
			filefunc(files);
			files = file.listFiles();
			m = directory.list().length;
		}

	}
	//    }        

	public static void filefunc(File[] files1) throws IOException, ParseException {
		for (File f : files1) {
			try {
				var1 = f.getName();
				var = var1.replaceAll(" ", "");
				
				
				File file2 = new File("C:\\ok\\" + var1);
				File newFile2 = new File("C:\\ok\\" + var);
				
				file2.renameTo(newFile2);
				
				//        System.out.println(f.getPath());
				//        System.out.p2qrintln(filepath+var);
				Process proc = Runtime.getRuntime().exec("cmd /c dir C:\\ok\\" + var + " /tc");

				BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				String data = "";

				
				for (int i = 0; i < 6; i++) {
					data = br.readLine();
				}

				//System.out.println("Extracted value : " + data);

				// split by space
				StringTokenizer st = new StringTokenizer(data);
				String date = st.nextToken();// Get date
				date2 = date;
				String time = st.nextToken();// Get time

				System.out.println(date + "    " + time);

				String input = time;
				// Format of the date defined in the input String
				DateFormat df = new SimpleDateFormat("hh:mm");
				// Desired format: 24 hour format: Change the pattern as per the need
				DateFormat outputformat = new SimpleDateFormat("HH:mm");
				java.util.Date date1 = null;
				String output = null;
				try {
					// Converting the input String to Date
					date1 = df.parse(input);
					// Changing the format of date and storing it in String
					output = outputformat.format(date1);
					// Displaying the date
					//    	    	    	 System.out.println(output);
				} catch (ParseException pe) {
					// Do nothing

				}
				boolean key = isTimeBetweenTwoTime(from, to, output + ":00");
				
				if ((key == true) & (date_1.contentEquals(date))) {
					Path temp = Files.move(Paths.get("C:\\ok\\" + var), Paths.get("C:\\ok1\\" + var));
					if (temp != null) {
						System.out.println("File moved successfully");
					} else {
						System.out.println("Failed to move the file");
					}
				}
			} 
			
			catch(Exception e) {
					System.out.println(e);
					System.out.println(var + " : File name already exists in destination folder");					
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter the new file name: ");
					path123 = scanner.next();
					File file1 = new File("C:\\ok\\" + var);
					File newFile = new File("C:\\ok\\" + path123);
					if (file1.renameTo(newFile)) {
						System.out.println("File rename success");
						//    	    	            File file11 = new File(filepath);
						//    	    	            files = file11.listFiles();        	    	            
					} else {
						System.out.println("File rename failed");
					}
				
			}
			System.out.println("-----------------------------");

		}
	}

	public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime)
			throws ParseException {

		String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
		if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
			boolean valid = false;
			// Start Time
			// all times are from java.util.Date
			java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(inTime);

			// Current Time
			java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(checkTime);

			// End Time
			java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(finTime);

			if (finalTime.compareTo(initialTime) < 0) {
				calendar2.add(Calendar.DATE, 1);
				calendar3.add(Calendar.DATE, 1);
			}

			java.util.Date actualTime = calendar3.getTime();
			if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
					&& actualTime.before(calendar2.getTime())) {
				valid = true;
				return valid;
			}
		}
		return false;
	}
}
