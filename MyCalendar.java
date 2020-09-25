import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyCalendar {
	
	private HashMap<Integer, ArrayList<Event>>  events;	
	
	public MyCalendar() {
		events = new HashMap<>();
		
	}
	
	
	public boolean addEvent(Event event ) {
		
		//case to add single event
		if(event.isRecurring() == false) {
			int hashCode = event.getInterval().getDate();
			if(!events.containsKey(hashCode)) { 		//event doesn't exist yet
				ArrayList<Event> eventList= new ArrayList<>();
				eventList.add(event);
				events.put(hashCode, eventList);
				return true;
			
			}
			else {			//add event to the ArrayList
				events.get(hashCode).add(event);
			}
		}
		
		//case to add recurring event
		else {
			int hashCode = 1;
			if(!events.containsKey(hashCode)) {		//recurring event doesn't exist yet
				ArrayList<Event> recurringList = new ArrayList<>();
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
	 * Returns all events in the calendar
	 * @return
	 */
	public Collection<ArrayList<Event>> getAllEvents() {
		return events.values();
	}
	
	
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
			System.out.println(" " + e.getInterval().getStartDate().getYear() + " " + e.getInterval().getStartDate().getDayOfWeek() + " " +
			e.getInterval().getStartDate().getMonth() + " " + e.getInterval().getStartDate().getDayOfMonth() + " " + MyCalendar.getHourMinutes(e) +
			" " + e.getName());
		}
		System.out.println();
		
		//displaying recurring events
		Collections.sort(recurring);
		System.out.println("RECURRING EVENTS\n****************");
		for(Event e: recurring) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
			System.out.println("" + e.getName());
			System.out.printf("%8s %12s %s %s\n", e.getDayString(), MyCalendar.getHourMinutes(e), formatter.format(e.getInterval().getStartDate()), 
					formatter.format(e.getInterval().getEndDate()));
		}
		System.out.println();
	}
	
	
	public void viewByDay() {
		Scanner in = new Scanner(System.in);
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
	
	private void viewByDayHelper(LocalDate date, int modifier) {
		String hashCodeString = "";
		hashCodeString += date.getYear();
		hashCodeString = hashCodeString.substring(2,4);
		if(date.getMonthValue()<10) {
			hashCodeString += "0" + date.getMonthValue();
		}
		else
			hashCodeString += date.getMonthValue();
		if(date.getDayOfMonth()<10) {
			hashCodeString += "0" + date.getDayOfMonth();
		}
		else
			hashCodeString += date.getDayOfMonth();
		int hashCode = Integer.parseInt(hashCodeString);
		//System.out.println(hashCode); //debug line
		
		//create newDate based on modifier
		LocalDate newDate = date;
		newDate = newDate.plusDays(modifier);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
			
		int dayOfWeek = newDate.getDayOfWeek().getValue();
		ArrayList<Event> list = new ArrayList<Event>();
		
		if(dayOfWeek == 7)
			dayOfWeek = 0;
		
		for(Event e: events.get(1)) {	//looking inside recurring events arraylist
			if(e.getDaysArray()[dayOfWeek] == true) {
				list.add(e);
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
	
	public void goTo(String dateString) {
		//get hashcode of day
		TimeInterval day = new TimeInterval(dateString, "0:00", "0:00");
		LocalDateTime dateTime = day.getStartDate();
		int hashCode = day.getDate();
		int dayOfWeek = dateTime.getDayOfWeek().getValue();
		ArrayList<Event> list = new ArrayList<Event>();
		
		for(Event e: events.get(1)) {	//looking inside recurring events arraylist
			if(e.getDaysArray()[dayOfWeek] == true) {
				list.add(e);
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

	
	public void printMonth() {
		Scanner in = new Scanner(System.in);
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
        	
        
        	if(date.getDayOfMonth() == x.getDayOfMonth() && date.getMonth().getValue() == x.getMonth().getValue()
        			&& date.getYear() == x.getYear())
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
	 * 
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
	 * 
	 * @param e
	 * @return
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
	
	public static String getDateString(LocalDateTime date) {
		String year = Integer.toString(date.getYear()).substring(2);
		String dateString = Integer.toString(date.getMonthValue()) + "/" + Integer.toString(date.getDayOfMonth()) + "/" + year;
	return dateString;	
	}
}
