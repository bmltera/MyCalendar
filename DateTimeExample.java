/**
 * Example to show the manipulation of Date and Time information through 
 * Java 8.0 API. 
 * @author: Suneuy Kim
 */


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateTimeExample
{
        public static void main(String [] args)
        {
                LocalDate cal = LocalDate.now(); // capture today
                Scanner sc = new Scanner(System.in);
                System.out.print("Today: ");
                System.out.println(cal.getDayOfWeek().getValue());
                printCalendar(cal);

                System.out.println(cal.getMonth().getValue());
                while (sc.hasNextLine())
                {
                        String input = sc.nextLine();
                        if (input.equals("p"))
                        {
                                cal = cal.minusMonths(1); // LocalDateTime is immutable
                                printCalendar(cal);
                        }
                        else if (input.equals("n"))
                        {
                                cal = cal.plusMonths(1); // LocalDateTime is immutable
                                printCalendar(cal);
                                
                        }
                }
                System.out.println("Bye!");
        }
        public static void printCalendar(LocalDate c)
        {  
            System.out.print(c.getDayOfWeek());
            System.out.print(" ");
            System.out.print(c.getDayOfMonth());
            System.out.print(" ");
            System.out.println(c.getMonth());
             

            // To print a calendar in a specified format. 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
            System.out.println(" " + formatter.format(c));

            // To figure out the day of week of the 1st day of the given month
            LocalDate x = LocalDate.of(c.getYear(), c.getMonth(), 1);
            x = x.plusDays(6);
            System.out.println(x.getDayOfWeek() + " is the day of " + c.getMonth() + " 1."); // enum value as it is
            System.out.println(x.getDayOfWeek().getValue() + " is an integer value corresponding "
            		+ " to " + x.getDayOfWeek()); // int value corresponding to the enum value
            
        }
        
        

        }