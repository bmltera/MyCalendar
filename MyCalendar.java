import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * MyCalendar contains a HashMap holding all events. There are various methods for event modification and display. 
 * @author Bill Li
 *
 */
public class MyCalendar {

	private HashMap<Integer, ArrayList<Event>>  events;	
	
	/**
	 * Constructs an empty calendar HashMap.
	 */
	public MyCalendar() {
		events = new HashMap<>();
	}
	
	/**
	 * Adds a one time or recurring event to events.
	 * @param event - event
	 * @return true if successfully added, false if an error occurred
	 */
	public boolean addEvent(Event event ) {

		//case to add single event
		if(event.isRecurring() == false) {
			ArrayList<Event> recurringList = events.get(1);
			int hashCode = event.getInterval().getDate();
			int dayOfWeek = event.getInterval().getStartDate().getDayOfWeek().getValue();
			if(dayOfWeek == 7)
				dayOfWeek = 0;
			//System.out.println(dayOfWeek);
			if(recurringList!=null) {
				for(Event e: recurringList) {					 //check for conflict amongst recurring
					if(e.getDaysArray()[dayOfWeek] == true) {	// check the day is correct
						//System.out.println(e.getName());
						if(TimeInterval.isInRange(event.getInterval(), e.getInterval())==true) { //must be in the range of dates
							//System.out.println(e.getName() + " is in range");

							if(TimeInterval.checkOverlap(event.getInterval(), e.getInterval()) == true) {
								return false; //conflict found
							}
						}
					}
				}
			}
			if(!events.containsKey(hashCode)) { 		//events in day doesn't exist yet
				ArrayList<Event> eventList= new ArrayList<>();
				eventList.add(event);
				events.put(hashCode, eventList);
				return true;
			}
			
			else {			// compare for conflict in same day
				ArrayList<Event> eventsOfDay = events.get(hashCode);
				for(Event e: eventsOfDay) {
					if(TimeInterval.checkOverlap(event.getInterval(), e.getInterval()) == true) {
						return false; //conflict found
					}
				}
				eventsOfDay.add(event);
				return true;
			}
		}
		
		//case to add recurring event
		else {
			int hashCode = 1;
			if(!events.containsKey(hashCode)) { 	//recurring event doesn't exist yet
				ArrayList<Event> recurringList = new ArrayList<Event>();
				events.put(1, recurringList);
				recurringList.add(event);
				events.put(hashCode, recurringList);
				return true;
				
			}
			else {
				events.get(hashCode).add(event);
			}
		}
		return false;
	}
	
	/**
	 * Deletes specified one time date.
	 * @param date - date to delete event from
	 */
	public void deleteOneTime(String date, Scanner in) {
		TimeInterval day = new TimeInterval(date, "0:00", "0:00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

		goTo(date);
		
		System.out.println("Enter the name of the event to delete:");
		in.nextLine();
		String name = in.nextLine();
		ArrayList<Event> toDelete = events.get(day.getDate());
		int indexToDelete = -1;
		if(toDelete!=null) {
			for(int i = 0; i < toDelete.size(); i++) {
				if(toDelete.get(i).getName().equals(name)) {
					indexToDelete = i;
					break;
				}
			}
		}
		//found index to delete
		if(indexToDelete != -1) {
			toDelete.remove(indexToDelete);
			System.out.printf("Event %s on %s deleted. \n", name, formatter.format(day.getStartDate()));
			return;
		}
		
		//name not matched 
		System.out.printf("No event called %s on %s. \n", name, formatter.format(day.getStartDate()));
	}
	
	
	/**
	 * Deletes all one time events from given date.
	 * @param date - date to delete all events
	 */
	public void deleteAll(String date) {
		TimeInterval day = new TimeInterval(date, "0:00", "0:00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
		//grab hashCode and clear list of events
		ArrayList<Event> toDelete = events.get(day.getDate());
		
		if(toDelete == null) {
			System.out.printf("There were no one time events to delete on %s.\n", formatter.format(day.getStartDate()));
			return;
		}
			
		toDelete.clear();
		System.out.printf("Deleted all one time events on %s.\n", formatter.format(day.getStartDate()));
	}
	
	
	/**
	 * Deletes recurring event from given name.
	 * @param eventName - name of recurring event
	 */
	public void deleteRecurring(String eventName) {
		ArrayList<Event> recurring = events.get(1);
		//find event to delete
		if(recurring!=null) {
			int indexToDelete = -1;
			for(int i = 0; i< recurring.size(); i++) {
				if(recurring.get(i).getName().equals(eventName)) {
					indexToDelete = i;
					break;
				}
			}
			//if index to delete exists
			if(indexToDelete != -1) {
				recurring.remove(indexToDelete);
				System.out.printf("Recurring event %s deleted. \n", eventName);
				return;
			}	
		}
		//if name not found or arraylist null
		System.out.printf("Recurring event %s does not exist.\n ", eventName);
	}
	
	
	/**
	 * Returns all events in the calendar.
	 * @return all events in a collection of ArrayLists
	 */
	public Collection<ArrayList<Event>> getAllEvents() {
		return events.values();
	}
	
	
	/**
	 * Prints all events in the calendar.
	 */
	public void eventList() {
		
		//displaying one time events
		System.out.println("ONE TIME EVENTS\n***************");
		ArrayList<Event> oneTime = new ArrayList<>();
		ArrayList<Event> recurring = new ArrayList<>();

		for(ArrayList<Event> dayList: events.values()) {
			for(Event e: dayList) {
				if(e.isRecurring() == false)
					oneTime.add(e);
				else
					recurring.add(e);
			}
		}
		Collections.sort(oneTime);
		for(Event e: oneTime) {
			//System.out.println(" " + e.getInterval().getStartDate().getYear() + " " + e.getInterval().getStartDate().getDayOfWeek() + " " +
			//e.getInterval().getStartDate().getMonth() + " " + e.getInterval().getStartDate().getDayOfMonth() + " " + MyCalendar.getHourMinutes(e) +
			//" " + e.getName());
			System.out.printf("%5s %-8s %-8s %2s %11s %10s\n", e.getInterval().getStartDate().getYear(), e.getInterval().getStartDate().getDayOfWeek(), e.getInterval().getStartDate().getMonth(),
					e.getInterval().getStartDate().getDayOfMonth(),MyCalendar.getHourMinutes(e),e.getName() );
		}
		System.out.println();
		
		//displaying recurring events
		Collections.sort(recurring);
		System.out.println("RECURRING EVENTS\n****************");
		for(Event e: recurring) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
			System.out.println(" " + e.getName());
			System.out.printf("%8s %12s %s %s\n", e.getDayString(), MyCalendar.getHourMinutes(e), formatter.format(e.getInterval().getStartDate()), 
					formatter.format(e.getInterval().getEndDate()));
		}
		System.out.println();
	}
	
	
	/**
	 * Prints day view of MyCalendar.
	 * @param in - input scanner
	 */
	public void viewByDay(Scanner in) {
		LocalDate date = LocalDate.now();
		viewByDayHelper(date, 0);
		String line = "";
		int dateModifier = 0;
		while(true){
			System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
			line = in.next();
			if(line.equals("G"))
				return;
			if(line.equals("N"))
				dateModifier++;
			if(line.equals("P"))
				dateModifier--;
			viewByDayHelper(date, dateModifier);
		}
			
	}
	
	
	/**
	 * Helper method for viewByDay, prints all events in a given day.
	 * @param date - today's LocalDate
	 * @param modifier - integer difference in days from today and the date to print
	 */
	private void viewByDayHelper(LocalDate date, int modifier) {
		
		//create newDate based on modifier
		LocalDate newDate = date;
		newDate = newDate.plusDays(modifier);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
			
		String hashCodeString = "";
		hashCodeString += newDate.getYear();
		hashCodeString = hashCodeString.substring(2,4);
		if(newDate.getMonthValue()<10) {
			hashCodeString += "0" + newDate.getMonthValue();
		}
		else
			hashCodeString += newDate.getMonthValue();
		if(newDate.getDayOfMonth()<10) {
			hashCodeString += "0" + newDate.getDayOfMonth();
		}
		else
			hashCodeString += newDate.getDayOfMonth();
		int hashCode = Integer.parseInt(hashCodeString);
		//System.out.println(hashCode); //debug line
		
		int dayOfWeek = newDate.getDayOfWeek().getValue();
		ArrayList<Event> list = new ArrayList<Event>();
		
		//create TimeInterval to compare with recurring events
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		TimeInterval thisDay = new TimeInterval(format.format(newDate), "00:00", "00:00");
		
		if(dayOfWeek == 7)
			dayOfWeek = 0;
		if(events.get(1) != null) {
			for(Event e: events.get(1)) {	//looking inside recurring events arraylist
				if(e.getDaysArray()[dayOfWeek] == true) {
					if(TimeInterval.isInRange(thisDay, e.getInterval()))
						list.add(e);
				}
			}
		}
		if(events.get(hashCode) != null) {
			for(Event e: events.get(hashCode)) {
				list.add(e);
			}
		}
		//Collections.sort(list);
		Collections.sort(list, new TimeComparator(list));

		
		//print events
		System.out.println("\n" + formatter.format(newDate));
		for(Event e: list) {
			System.out.printf(" %-13s %-15s\n", MyCalendar.getHourMinutes(e), e.getName());
		}
		System.out.println();

	}
	
	
	/**
	 * Prints events at given date
	 * @param dateString
	 */
	public void goTo(String dateString) {
		//get hashcode of day
		TimeInterval day = new TimeInterval(dateString, "0:00", "0:00");
		LocalDateTime dateTime = day.getStartDate();
		int hashCode = day.getDate();
		int dayOfWeek = dateTime.getDayOfWeek().getValue();
		if(dayOfWeek == 7)
			dayOfWeek = 0;
	
		ArrayList<Event> list = new ArrayList<Event>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		TimeInterval thisDay = new TimeInterval(format.format(dateTime), "00:00", "00:00");
		
		if(events.get(1)!=null) {
			for(Event e: events.get(1)) {	//looking inside recurring events ArrayList
					if(e.getDaysArray()[dayOfWeek] == true) {
						if(TimeInterval.isInRange(thisDay, e.getInterval()))
							list.add(e);
					}
			}
		}
		if(events.get(hashCode) != null) {
			for(Event e: events.get(hashCode)) {
				list.add(e);
			}
		}
		//Collections.sort(list);
		Collections.sort(list, new TimeComparator(list));

		
		//print go to
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
		System.out.println("\n" + formatter.format(dateTime));
		for(Event e: list) {
			System.out.printf(" %-13s %-15s\n", MyCalendar.getHourMinutes(e), e.getName());
		}
		System.out.println();
	}

	
	/**
	 * Prints month view.
	 * @param in - input scanner
	 */
	public void printMonth(Scanner in) {
		LocalDate date = LocalDate.now();
		printMonthHelper(date, 0);
		String line = "";
		int dateModifier = 0;
		while(true){
			System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
			line = in.next();
			if(line.equals("G"))
				return;
			if(line.equals("N"))
				dateModifier++;
			if(line.equals("P"))
				dateModifier--;
			printMonthHelper(date, dateModifier);
		}
		
	}
	
	
	/**
	 * Helper method for month view, prints days of the month.
	 * @param date - today's LocalDate
	 * @param modifier - integer difference of the month to print
	 */
	private void printMonthHelper(LocalDate date, int modifier) {
		
		//get first day of month
		LocalDate newDate = date.plusMonths(modifier);
        LocalDate x = LocalDate.of(newDate.getYear(), newDate.getMonth(), 1); 
        
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		System.out.println("\n" + formatter.format(newDate));
		System.out.println("Su Mo Tu We Th Fr Sa");
		
		//making spaces for first day
		if(x.getDayOfWeek().getValue() != 7) {
			for(int i = 0; i < x.getDayOfWeek().getValue(); i++) {
        		System.out.print("   ");
        	}
		}
		
        while(newDate.getMonthValue() == x.getMonthValue()) {
        	boolean hasEvent = false;
    		int dayOfWeek = x.getDayOfWeek().getValue();
    		ArrayList<Event> list = new ArrayList<Event>();
    		
    		if(dayOfWeek == 7)
    			dayOfWeek = 0;
    		
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/YYYY");
			TimeInterval thisDay = new TimeInterval(format.format(x), "00:00", "00:00");
			if((events.get(thisDay.getDate()) != null) && (events.get(thisDay.getDate()).size() > 0))
				hasEvent = true;
    		//checking if date is within the recurring events
    		if(events.get(1)!=null) {
	    		for(Event e: events.get(1)) {	//looking inside recurring events arraylist
	    			
	    			//System.out.println(format.format(x));
	    			if(e.getDaysArray()[dayOfWeek] == true) {
	    				if(TimeInterval.isInRange(thisDay, e.getInterval()))
	    					hasEvent = true;
	    			}
	    		}
    		}
        	
        	if(hasEvent)
        		System.out.printf("{%2d}", x.getDayOfMonth());
   
        	else
        		System.out.printf("%2d", x.getDayOfMonth());
        	System.out.print(" ");
        	x = x.plusDays(1);
        	if(x.getDayOfWeek().getValue() == 7) {
        		System.out.println();
        	}
        }
		System.out.println("\n");
	}
	
	
	/**
	 * Prints initial calendar.
	 */
	public void printFirst() {
		LocalDate date = LocalDate.now();
		//calendar heading
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		System.out.println("  " + formatter.format(date));
		System.out.println("Su Mo Tu We Th Fr Sa");
		
		//get first day of month
        LocalDate x = LocalDate.of(date.getYear(), date.getMonth(), 1); 
        
		//making spaces for first day
		if(x.getDayOfWeek().getValue() != 7) {
			for(int i = 0; i < x.getDayOfWeek().getValue(); i++) {
        		System.out.print("   ");
        	}
		}
        
        while(date.getMonthValue() == x.getMonthValue()) {
        	
        
        	if(date.getDayOfMonth() == x.getDayOfMonth())
        		System.out.printf("[%2d]", x.getDayOfMonth());
   
        	else
        		System.out.printf("%2d", x.getDayOfMonth());
        	System.out.print(" ");
        	x = x.plusDays(1);
        	if(x.getDayOfWeek().getValue() == 7) {
        		System.out.println();
        	}
        }
        
		System.out.println("\n");
	}
	
	
	/**
	 * Returns string representing the start time and end time of given event.
	 * @param e - event
	 * @return String representing start time and end time of event
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
		return (e.getInterval().getStartDate().getHour() + ":" + startMinutes + "-" + 
				e.getInterval().getEndDate().getHour() + ":" + endMinutes);
	}
	
	
	/**
	 * Returns string representing date in MM/DD/YYY format.
	 * @param date - LocalDateTime date
	 * @return string representing date in MM/DD/YYYY format
	 */
	public static String getDateString(LocalDateTime date) {
		String year = Integer.toString(date.getYear()).substring(2);
		String dateString = Integer.toString(date.getMonthValue()) + "/" + Integer.toString(date.getDayOfMonth()) + "/" + year;
	return dateString;	
	}
}
