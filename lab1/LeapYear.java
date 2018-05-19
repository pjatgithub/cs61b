/** Class that determines whether or not a year is a leap year.
 *  @author YOUR NAME HERE
 */
public class LeapYear {

    /** Calls isLeapYear to print correct statement.
     *  @param  year to be analyzed
     */
    private static void checkLeapYear(int year) {
        if (isLeapYear(year)) {
            System.out.printf("%d is a leap year.\n", year);
        } else {
            System.out.printf("%d is not a leap year.\n", year);
        }
    }

    /** Judge if the specified year is a leap year.
     *
     *  @param year a year represented by an integer
     *  @return true if {@code year} representing a leap year, false otherwise
     *  @see <a href='https://sp18.datastructur.es/materials/lab/lab1/lab1#f-leap-year'>Leap Year</a>
     */
    public static boolean isLeapYear(int year) {
        if (year <= 0) {
            return false;
        } else if (year % 4 != 0) {
            return false;
        } else if ((year % 100) == 0 && (year % 400) != 0) {
            return false;
        }

        return true;
    }

    /** Must be provided an integer as a command line argument ARGS.
     *  
     *  @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }
}

