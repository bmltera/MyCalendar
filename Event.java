
public class Event implements Comparable<Event>{

	private String name;
	private boolean isRecurring;
	private TimeInterval interval;
	private boolean[] daysArray;
	private String daysOfWeek;
	private String dayString;
	
	/**
	 * Constructs an event.
	 * @param name
	 * @param interval
	 */
	public Event(String name, TimeInterval interval) {
		this.name = name;
		isRecurring = false;
		this.interval = interval;
		
	}
	
	
	/**
	 * Constructs a recurring event.
	 * @param name
	 * @param interval
	 * @param days
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
	
	public boolean[] getDaysArray() {
		return daysArray;
	}
	
	public String getDayString() {
		return dayString;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isRecurring() {
		return isRecurring;
	}
	
	public TimeInterval getInterval() {
		return interval;
	}


	@Override
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
	public int hashCode() {
		return name.hashCode() + daysArray.hashCode();
	}
}
