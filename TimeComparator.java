import java.util.*;
/**
 * TimeComparator contains the method for comparing event start times in the same day.
 * @author Bill Li
 */
public class TimeComparator implements Comparator<Event>{

	private List<Event> eventOrder;
	
	/**
	 * Constructs TimeComparator class with given eventOrder.
	 * @param eventOrder - list with events
	 */
	public TimeComparator (List<Event>eventOrder) {
		this.eventOrder = eventOrder;
	}
	
	/**
	 * Compares start times of two events.
	 * @return negative integer if o1 comes before o2, positive integer if o1 comes after o2, and zero if both start times are equal
	 */
	@Override
	public int compare(Event o1, Event o2) {
		return o1.getInterval().getStartTime()-o2.getInterval().getStartTime();
	}
	

}


