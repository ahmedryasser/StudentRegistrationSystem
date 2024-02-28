import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.Naming;

public class Server { 
   public static void main(String args[]) { 
      try {
         String studentFileName, courseFileName;
         // Check the number of parameters.
         if (args.length == 2) {
            studentFileName = args[0];
            courseFileName = args[1];
         } else {
            studentFileName = "Lab2-code/Students.txt";
            courseFileName = "Lab2-code/Courses.txt";
         }

         // Check if input files exists.
         if (new File(studentFileName).exists() == false) {
            System.err.println("Could not find " + studentFileName);
            System.exit(1);
         }
         if (new File(courseFileName).exists() == false) {
            System.err.println("Could not find " + courseFileName);
            System.exit(1);
         }

         // Create components.
         try {
            DataBase db;
            db = new DataBase(studentFileName, courseFileName);
            //EV_LIST_ALL_COURSES
            ListAllCoursesHandler listCourses = new ListAllCoursesHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_LIST_ALL_COURSES",listCourses);
            //EV_LIST_ALL_STUDENTS
            ListAllStudentsHandler listStudents = new ListAllStudentsHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_LIST_ALL_STUDENTS",listStudents);

            //EV_LIST_STUDENTS_REGISTERED
            ListStudentsRegisteredHandler studentsRegistered = new ListStudentsRegisteredHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_LIST_STUDENTS_REGISTERED",studentsRegistered);

            //EV_LIST_ALL_STUDENTS
            ListCoursesRegisteredHandler coursesRegisteredHandler = new ListCoursesRegisteredHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_LIST_COURSES_REGISTERED",coursesRegisteredHandler);

            //EV_LIST_ALL_STUDENTS
            ListCoursesCompletedHandler listCoursesCompletedHandler = new ListCoursesCompletedHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_LIST_COURSES_COMPLETED",listCoursesCompletedHandler);

            //EV_LIST_ALL_STUDENTS
            RegisterStudentHandler registerStudentHandler = new RegisterStudentHandler(db);
            Naming.rebind("rmi://localhost:1900/EV_REGISTER_STUDENT",registerStudentHandler);
            System.err.println("Server ready");
         }
         catch (FileNotFoundException e) {
            // Dump the exception information for debugging.
            e.printStackTrace();
            System.exit(1);
         }
         } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
      } 
   } 
} 