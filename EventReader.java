import java.util.*;
import java.io.*;
public class EventReader {
	
	public static void readEvents(MyCalendar cal) throws IOException{
		File data = new File("events.txt");
		FileReader fr = new FileReader(data);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		
		//read file and add data to data structure
		while((line = br.readLine())!=null) {
			String name = line;
			String[] info = br.readLine().split("\\s+"); 	// split line by whitespace
			if(info.length == 3) {							// 3 entries, one time event
				String date = info[0];
				String startTime = info[1];
				String endTime = info[2];
				TimeInterval interval = new TimeInterval(date, startTime, endTime);
				Event event = new Event(name, interval);
				cal.addEvent(event);
			}
			else{						// recurring event 
				String daysOfWeek = info[0];
				String startTime = info[1];
				String endTime = info[2];
				String startDate = info[3];
				String endDate = info[4];
				
				TimeInterval interval = new TimeInterval(startDate, endDate, startTime, endTime);
				Event event = new Event(name, interval, daysOfWeek);
				cal.addEvent(event);
			}
			
			
		}
		br.close();
		fr.close();
	}
}
