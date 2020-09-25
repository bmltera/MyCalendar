import java.util.*;
public class CalendarManager {
	
	private MyCalendar cal;
	
	public CalendarManager(MyCalendar cal) {
		this.cal = cal;
	}
	
	public boolean addOne(String name, String date, String startTime, String endTime) {
		
		TimeInterval interval = new TimeInterval(date, startTime, endTime);
		Event event = new Event(name, interval);
		if(cal.addEvent(event) == true) {
			return true;
		}
		System.out.println("Error adding, there was a conflicting event");
		return false;
	}
	
	public static void addRecurring() {
		
	}
	
	
}
