import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
/**
 * TimeInterval contains the date and time information of an event, and contains methods for checking event conflicts in MyCalendar.
 * @author Bill Li
 */
public class TimeInterval {
	private int startDate;		 // date YYYY aka hashCode getDate
	private int endDate;		 // date array		
	private int startTime;		 // start time array [hour, minute]
	private int endTime;		 // end time array [hour, minute]
	private LocalDateTime date1; // start date object
	private LocalDateTime date2; // end date object


	/**
	 * Constructs TimeInterval for one time event.
	 * @param date - date of event
	 * @param startTime - start time of event
	 * @param endTime - end time of event
	 */
	public TimeInterval(String date, String startTime, String endTime) {
		String string = "";
		String[]dateArray = date.split("\\/");
		
		if(dateArray[2].length() == 4)
			string += dateArray[2].substring(2,4);
		else
			string += dateArray[2];
		if(dateArray[0].length() < 2)
			string += "0" + dateArray[0];
		else
			string += dateArray[0];
		if(dateArray[1].length() < 2)
			string += "0" + dateArray[1];
		else
			string += dateArray[1];
		this.startDate = Integer.parseInt(string);
		
		string = "";
		String[]startArray = startTime.split("\\:");
		string += startArray[0];
		string += startArray[1];
		this.startTime = Integer.parseInt(string);
		
		string = "";
		String[]endArray = endTime.split("\\:");
		string += endArray[0];
		string += endArray[1];
		this.endTime = Integer.parseInt(string);
		
		//to get start year
		int year;
		if(dateArray[2].length() == 4)
			year = Integer.parseInt(dateArray[2]);
		else {
			string = "20" + dateArray[2];
			year = Integer.parseInt(string);
		}
		
		//to get start month
		int month = Integer.parseInt(dateArray[0]);
		int dayOfMonth = Integer.parseInt(dateArray[1]);
		int hour = Integer.parseInt(startArray[0]);
		int minute = Integer.parseInt(startArray[1]);
		//create LocalDateTime object
		this.date1 = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		hour = Integer.parseInt(endArray[0]);
		minute = Integer.parseInt(endArray[1]);
		//create LocalDateTime object
		this.date2 = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}
	
	/**
	 * Constructs TimeInterval for recurring event.
	 * @param startDate - start date of recurring event
	 * @param endDate - end date of recurring event
	 * @param startTime - start time of recurring event
	 * @param endTime - end time of recurring event
	 */
	public TimeInterval(String startDate, String endDate, String startTime, String endTime) {
		String string = "";
		String[]dateArray = startDate.split("\\/");
		//System.out.println(startDate);						//debug line
		//System.out.println(Arrays.toString(dateArray));		//debug line
		if(dateArray[2].length() == 4)
			string += dateArray[2].substring(2,4);
		else
			string += dateArray[2];
		if(dateArray[0].length() < 2)
			string += "0" + dateArray[0];
		else
			string += dateArray[0];
		if(dateArray[1].length() < 2)
			string += "0" + dateArray[1];
		else
			string += dateArray[1];
		this.startDate = Integer.parseInt(string);
		
		string = "";
		String[]startArray = startTime.split("\\:");
		string += startArray[0];
		string += startArray[1];
		this.startTime = Integer.parseInt(string);
		
		string = "";
		String[]endArray = endTime.split("\\:");
		string += endArray[0];
		string += endArray[1];
		this.endTime = Integer.parseInt(string);
		
		//to get start year
		int year;
		if(dateArray[2].length() == 4)
			year = Integer.parseInt(dateArray[2]);
		else {
			string = "20" + dateArray[2];
			year = Integer.parseInt(string);
		}
		
		int month = Integer.parseInt(dateArray[0]);
		int dayOfMonth = Integer.parseInt(dateArray[1]);
		int hour = Integer.parseInt(startArray[0]);
		int minute = Integer.parseInt(startArray[1]);
		//create LocalDateTime object
		this.date1 = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		
		dateArray = endDate.split("\\/");
		//System.out.println(startDate);						//debug line
		//System.out.println(Arrays.toString(dateArray));		//debug line
		string = "";
		if(dateArray[2].length() == 4)
			string += dateArray[2].substring(2,4);
		else
			string += dateArray[2];
		if(dateArray[0].length() < 2)
			string += "0" + dateArray[0];
		else
			string += dateArray[0];
		if(dateArray[1].length() < 2)
			string += "0" + dateArray[1];
		else
			string += dateArray[1];
		this.endDate = Integer.parseInt(string);
		
		//for second date
		dateArray = endDate.split("\\/");
		if(dateArray[2].length() == 4)
			year = Integer.parseInt(dateArray[2]);
		else {
			string = "20" + dateArray[2];
			year = Integer.parseInt(string);
		}
		month = Integer.parseInt(dateArray[0]);
		dayOfMonth = Integer.parseInt(dateArray[1]);

		hour = Integer.parseInt(endArray[0]);
		minute = Integer.parseInt(endArray[1]);
		//create LocalDateTime object
		this.date2 = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		//System.out.println(this.getDate());
	}
	
	
	/**
	 * Returns LocalDateTime of event start date.
	 * @return LocalDateTime of event start date
	 */
	public LocalDateTime getStartDate() {
		return date1;
	}
	
	
	/**
	 * Returns LocalDateTime of event end date.
	 * @return LocalDateTime of event end date
	 */
	public LocalDateTime getEndDate() {
		return date2;
	}
	
	
	/**
	 * Returns event start date integer.
	 * @return integer representing event start date
	 */
	public int getDate() {
		return startDate;
	}
	
	
	/**
	 * Returns last date of recurring event. 
	 * @return integer representing last date of recurring event
	 */
	public int getLastDate() {
		return endDate;
	}
	
	
	/**
	 * Returns start time of event.
	 * @return start time of event
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * Returns end time of event.
	 * @return end time of event
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Checks if two events overlap on the same day.
	 * @param one - TimeInterval of first event
	 * @param two - TimeInterval of second event
	 * @return true if there is overlap, false if there is no overlap
	 */
	public static boolean checkOverlap(TimeInterval one, TimeInterval two) {
	//	if(one.getDate() != two.getDate()) {	//ignore if different date
	//		return false;	
	//	}
		if((one.getStartTime() > two.getStartTime()) && (one.getStartTime() < two.getEndTime())) {	//overlaps if start or end time overlaps the other
			return true;
		}
		if((one.getEndTime() > two.getStartTime()) && (one.getEndTime() < two.getEndTime())) {
			return true;
		}
		if((one.getStartTime() < two.getStartTime()) && (one.getEndTime() > two.getEndTime())) { //encapsulates entire event
			return true;
		}
		return false;	//non overlap
	}
	
	/**
	 * Checks if one time event is in the date range of recurring event.
	 * @param oneTimeEventInterval - TimeInterval of one time event
	 * @param recurringEventInterval - Time Interval of recurring event
	 * @return true if one time event is in date range, false if one time event is out of date range
	 */
	public static boolean isInRange(TimeInterval oneTimeEventInterval, TimeInterval recurringEventInterval) {
		if(oneTimeEventInterval.getDate() >= recurringEventInterval.getDate() && oneTimeEventInterval.getDate() <= recurringEventInterval.getLastDate()) {
			return true;
			}
		return false;
	}
	

}
