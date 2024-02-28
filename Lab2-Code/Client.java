import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {

    /**
     * Thread body of client input components. It continuously gets user input and announces command
     * events.  It announces show events to request the display of usage prompts.
     */
    public static void main(String args[]) {
        try {
                // Create a buffered reader using system input stream.
                BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

                while (true) {
                    // Show available commands and get a choice.
                    System.out.println("\nStudent Registration System\n");
                    System.out.println( "1) List all students");
                    System.out.println( "2) List all courses");
                    System.out.println( "3) List students who registered for a course");
                    System.out.println( "4) List courses a student has registered for");
                    System.out.println( "5) List courses a student has completed");
                    System.out.println( "6) Register a student for a course");
                    System.out.println( "x) Exit");
                    System.out.println( "\nEnter your choice and press return >> ");
                    String sChoice = objReader.readLine().trim();

                    // Execute command 1: List all students.
                    if (sChoice.equals("1")) {
                        // Announce the command event #1.
                        System.out.println( "\n");
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_LIST_ALL_STUDENTS");
                        System.out.println(stub.execute(""));
                        continue;
                    }

                    // Execute command 2: List all courses.
                    if (sChoice.equals("2")) {
                        // Announce the command event #2.
                        System.out.println( "\n");
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_LIST_ALL_COURSES");
                        System.out.println(stub.execute(""));
                        continue;
                    }

                    // Execute command 3: List students registered for a course.
                    if (sChoice.equals("3")) {
                        // Get course ID and course section from user.
                        System.out.println( "\nEnter course ID and press return >> ");
                        String sCID = objReader.readLine().trim();
                        System.out.println( "\nEnter course section and press return >> ");
                        String sSection = objReader.readLine().trim();

                        // Announce the command event #3 with course ID and course section.
                        System.out.println( "\n");
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_LIST_STUDENTS_REGISTERED");
                        System.out.println(stub.execute(sCID + " " + sSection));
                        //EV_LIST_STUDENTS_REGISTERED, sCID + " " + sSection);
                        continue;
                    }

                    // Execute command 4: List courses a student has registered for.
                    if (sChoice.equals("4")) {
                        // Get student ID from user.
                        System.out.println("\nEnter student ID and press return >> ");
                        String sSID = objReader.readLine().trim();

                        // Announce the command event #4 with student ID.
                        System.out.println( "\n");
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_LIST_COURSES_REGISTERED");
                        System.out.println(stub.execute(sSID));

                        continue;
                    }

                    // Execute command 5: List courses a student has completed.
                    if (sChoice.equals("5")) {
                        // Get student ID from user.
                        System.out.println( "\nEnter student ID and press return >> ");
                        String sSID = objReader.readLine().trim();

                        // Announce the command event #5 with student ID.
                        System.out.println( "\n");
                        //EV_LIST_COURSES_COMPLETED, sSID);
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_LIST_COURSES_COMPLETED");
                        System.out.println(stub.execute(sSID));
                        continue;
                    }

                    // Execute command 6: Register a student for a course.
                    if (sChoice.equals("6")) {
                        // Get student ID, course ID, and course section from user.
                        System.out.println( "\nEnter student ID and press return >> ");
                        String sSID = objReader.readLine().trim();
                        System.out.println( "\nEnter course ID and press return >> ");
                        String sCID = objReader.readLine().trim();
                        System.out.println( "\nEnter course section and press return >> ");
                        String sSection = objReader.readLine().trim();

                        // Announce the command event #5 with student ID, course ID, and course section.
                        System.out.println( "\n");
                        //EV_REGISTER_STUDENT, sSID + " " + sCID + " " + sSection);
                        IActivity stub = (IActivity) Naming.lookup("rmi://localhost:1900/EV_REGISTER_STUDENT");
                        System.out.println(stub.execute(sSID + " " + sCID + " " + sSection));
                        continue;
                    }

                    // Terminate this client.
                    if (sChoice.equalsIgnoreCase("X")) {
                        break;
                    }
                }

                // Clean up the resources.
                objReader.close();
        }
        catch (Exception e) {
            // Dump the exception information for debugging.
            e.printStackTrace();
            System.exit(1);
        }
    }
}