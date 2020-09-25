import java.util.*;
/**
 * 
 * @author bill
 * Code referenced from https://stackoverflow.com/questions/5245093/how-do-i-use-comparator-to-define-a-custom-sort-order
 */
public class TimeComparator implements Comparator<Event>{

	private List<Event> eventOrder;
	
	/**
	 * 
	 * @param eventOrder
	 */
	public TimeComparator (List<Event>eventOrder) {
		this.eventOrder = eventOrder;
	}
	
	/**
	 * 
	 */
	@Override
	public int compare(Event o1, Event o2) {
		return o1.getInterval().getStartTime()-o2.getInterval().getStartTime();
	}
	

}


