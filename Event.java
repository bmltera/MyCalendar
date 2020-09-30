/**
 * An event is used in the MyCalendar data structure. It includes variables for name, isRecurring, interval, daysArray, and dayString.
 * @author Bill Li
 *
 */
public class Event implements Comparable<Event>{

	private String name;
	private boolean isRecurring;
	private TimeInterval interval;
	private boolean[] daysArray;
	//private String daysOfWeek;
	private String dayString;
	
	
	/**
	 * Constructs a one time event.
	 * @param name - name of event
	 * @param interval - TimeInterval of event
	 */
	public Event(String name, TimeInterval interval) {
		this.name = name;
		isRecurring = false;
		this.interval = interval;
		
	}
	
	
	/**
	 * Constructs a recurring event.
	 * @param name - name of event
	 * @param interval - TimeInterval of event
	 * @param days - days of the week the event exists in
	 */
	public Event(String name, TimeInterval interval, String days) {
		dayString = days;
		this.name = name;
		isRecurring = true;
		this.interval = interval;
		this.daysArray = new boolean[7];
		char[] daysArray = days.toCharArray();
		for(char c: daysArray) {
			if(c == 'S') {
				this.daysArray[0] = true;
			}
			if(c == 'M') {
				this.daysArray[1] = true;
			}
			if(c == 'T') {
				this.daysArray[2] = true;
			}
			if(c == 'W') {
				this.daysArray[3] = true;
			}
			if(c == 'R') {
				this.daysArray[4] = true;
			}
			if(c == 'F') {
				this.daysArray[5] = true;
			}
			if(c == 'A') {
				this.daysArray[6] = true;
			}
		}
	}
	
	
	/**
	 * Returns boolean array of days with events for recurring event.
	 * @return array of days indicating which days the recurring event exists in
	 */
	public boolean[] getDaysArray() {
		return daysArray;
	}
	
	
	/**
	 * Returns string of days of week of recurring event.
	 * @return string of days of week
	 */
	public String getDayString() {
		return dayString;
	}
	
	
	/**
	 * Returns name of event.
	 * @return event name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Sets the event name.
	 * @param name - event name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Returns if the event is recurring.
	 * @return true if recurring, false if not recurring
	 */
	public boolean isRecurring() {
		return isRecurring;
	}
	
	
	/**
	 * Returns TimeInterval of event.
	 * @return TImeInterval of event
	 */
	public TimeInterval getInterval() {
		return interval;
	}


	@Override
	/**
	 * CompareTo method for sorting.
	 * @return compareTo order
	 */
	public int compareTo(Event that) {
		if(this.interval.getDate() != that.getInterval().getDate()) {
			return this.getInterval().getDate() - that.getInterval().getDate();
		}
		if(this.interval.getStartTime() != that.getInterval().getStartTime()) {
			return this.getInterval().getStartTime() - that.getInterval().getStartTime();
		}
		return 0;
	}
	
	
	@Override
	/**
	 * Returns hashcode of event.
	 * @return event hashcode
	 */
	public int hashCode() {
		return name.hashCode() + daysArray.hashCode();
	}
}
