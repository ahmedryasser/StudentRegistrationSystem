/**
 * @(#)ListStudentsRegisteredHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * "List students who registered for a course" command event handler.
 */
public class ListStudentsRegisteredHandler  extends UnicastRemoteObject implements IActivity {

    /**
     * Construct "List students who registered for a course" command event handler.
     *
     */
    private DataBase db;
    public ListStudentsRegisteredHandler(DataBase db) throws RemoteException {
        super();
        this.db = db;
    }
    /**
     * Process "List Courses Completed" event.
     *
     * @param param a string
     * @return a string result of command processing
     */
    @Override
    public String execute(String param) throws RemoteException {
        // Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the list of students who registered for the given course.
        Course objCourse = db.getCourseRecord(sCID, sSection);
        if (objCourse == null) {
            return "Invalid course ID or course section";
        }
        ArrayList vStudent = objCourse.getRegisteredStudents();

        // Construct a list of student information and return it.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        try {
            FileWriter myWriter = new FileWriter("Lab2-Code/output.txt", true);
            myWriter.write("Student registered for "+ objCourse.sCID+ System.lineSeparator() +sReturn + System.lineSeparator() + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in grabbing the output file in ListAllCoursesHandler.");
            e.printStackTrace();
        }
        return sReturn;
    }
}