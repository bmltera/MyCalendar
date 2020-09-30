import java.util.*;
/**
 * The Calendar Manager handles adding user generated events to MyCalendar.
 * @author Bill Li
 *
 */
public class CalendarManager {
	
	private MyCalendar cal;
	
	/**
	 * Constructs a calendar manager.
	 * @param cal - MyCalendar data structure
	 */
	public CalendarManager(MyCalendar cal) {
		this.cal = cal;
	}
	
	/**
	 * Attempts to add a one time event to MyCalendar
	 * @param name - event name
	 * @param date - event time
	 * @param startTime - event start time
	 * @param endTime - event end time
	 * @return boolean - true if successfully added, false if an error occurred
	 */
	public boolean addOne(String name, String date, String startTime, String endTime) {
		
		TimeInterval interval = new TimeInterval(date, startTime, endTime);
		Event event = new Event(name, interval);
		if(cal.addEvent(event) == true) {
			System.out.printf("\nEvent %s added\n\n", event.getName());
			return true;
		}
		System.out.println("\nError adding, there was a conflicting event\n");
		return false;
	}
	
	
	
}
