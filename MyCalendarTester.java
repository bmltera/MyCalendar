import java.io.IOException;
/**
 * The Calendar Tester contains the main method for the program.
 * @author Bill Li
 * @version 1.0 9/28/2020
 */
import java.util.*;
public class MyCalendarTester {
	
	
	/**
	 * Main method for MyCalendar program.
	 * @param args - console input
	 * @throws IOException
	 */
	public static void main(String[]args) throws IOException {
		
		MyCalendar cal = new MyCalendar();
		cal.printFirst();
		CalendarManager manager = new CalendarManager(cal);
		EventReader.readEvents(cal); 		//read events.txt into MyCalendar
		
		Scanner in = new Scanner(System.in);
		
		//main loop of program
		String response = "";
		while(!response.equals("Q")) {
			System.out.println("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
			response = in.next();
			
			// view by
			if(response.equals("V")) {
				System.out.println("[D]ay view or [M]view ?");
				response = in.next();
				if(response.equals("D")) {
					cal.viewByDay(in);
				}
				if(response.equals("M")) {
					cal.printMonth(in);
				}
				response = ""; //clear response
			}
			
			// create new event
			if(response.equals("C")) {
				System.out.print("Name:");
				in.nextLine();
				String name = in.nextLine();
				System.out.print("Date (MM/DD/YYYY):");
				String date = in.next();
				System.out.print("Starting Time:");
				String startTime = in.next();
				System.out.print("Ending Time:");
				String endTime = in.next();
				manager.addOne(name, date, startTime, endTime);
			}
		
			// go to
			if(response.equals("G")) {
				System.out.print("Enter a date (MM/DD/YYYY):");
				String dateString = in.next();
				cal.goTo(dateString);
			}
			
			// event list
			if(response.equals("E")) {
				cal.eventList();
			}
			
			// delete an event
			if(response.equals("D")) {
				System.out.println("[S]elected  [A]ll   [DR] Delete Recurring ");
				response = in.next();
				if(response.equals("S")) {
					System.out.print("Enter date MM/DD/YYYY: ");
					String date = in.next();
					cal.deleteOneTime(date, in);
				}
				if(response.equals("A")) {
					System.out.print("Enter date MM/DD/YYYY: ");
					String date = in.next();
					cal.deleteAll(date);
				}
				if(response.equals("DR")) {
					System.out.print("Enter event name: ");
					in.nextLine();
					String eventName = in.nextLine();
					cal.deleteRecurring(eventName);
				}	
			}
		}
		System.out.println("Good Bye");
		EventWriter.writeEvents(cal);		//write events out 
	}
}
