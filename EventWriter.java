import java.util.*;
/**
 * The EventWriter writes events from MyCalendar onto output file.
 * @author Bill Li
 */
import java.io.*;
public class EventWriter {
	
	/**
	 * Writes events from MyCalendar to output file.
	 * @param cal - MyCalendar data structure
	 * @throws IOException if file is invalid
	 */
	public static void writeEvents(MyCalendar cal) throws IOException {
		File file = new File("output.txt");
		FileWriter fr = new FileWriter(file);
		BufferedWriter br = new BufferedWriter(fr);
		PrintWriter pr = new PrintWriter(br);
		
		for(ArrayList<Event> eventList: cal.getAllEvents()) {
			for(Event e: eventList) {
				pr.println(e.getName());
				if(e.isRecurring()) {	//for recurring events
					pr.print(e.getDayString() + " " + getHourMinutes(e) + " " + 
							MyCalendar.getDateString(e.getInterval().getStartDate()) + " " + MyCalendar.getDateString(e.getInterval().getEndDate()));
					
					
					pr.println();
				}
				else { 	//for one time events
					pr.print(MyCalendar.getDateString(e.getInterval().getStartDate()) + " " + getHourMinutes(e));

					
					pr.println();
				}
			}
		}
		
		pr.close();
		br.close();
		fr.close();
		
	}
	
	/**
	 * Returns string representing start and end time of event
	 * @param e - event
	 * @return String representing start and end time of event
	 */
	private static String getHourMinutes(Event e) {
		String startMinutes, endMinutes;
		if(e.getInterval().getStartDate().getMinute() == 0){
			startMinutes = "00";
		}
		else
			startMinutes = Integer.toString(e.getInterval().getStartDate().getMinute());
		if(e.getInterval().getEndDate().getMinute() == 0){
			endMinutes = "00";
		}
		else
			endMinutes = Integer.toString(e.getInterval().getEndDate().getMinute());
		return (e.getInterval().getStartDate().getHour() + ":" + startMinutes + " " + 
				e.getInterval().getEndDate().getHour() + ":" + endMinutes);
	}

}
