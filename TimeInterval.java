import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class TimeInterval {
	private int startDate;		 // date YYYY
	private int endDate;		 // date array		
	private int startTime;		 // start time array [hour, minute]
	private int endTime;		 // end time array [hour, minute]
	private LocalDateTime date1; // start date object
	private LocalDateTime date2; // end date object


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
	 * 
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
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
	}
	
	
	public LocalDateTime getStartDate() {
		return date1;
	}
	
	public LocalDateTime getEndDate() {
		return date2;
	}
	
	public int getDate() {
		return startDate;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}

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
		return false;	//non overlap
	}
	

}
