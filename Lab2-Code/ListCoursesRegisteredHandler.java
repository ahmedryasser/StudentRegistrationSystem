/**
 * @(#)ListCoursesRegisteredHandler.java
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
 * "List courses a student has registered for" command event handler.
 */
public class ListCoursesRegisteredHandler extends UnicastRemoteObject implements IActivity {

    /**
     * Construct "List courses a student has registered for" command event handler.
     *
     */
    private DataBase db;
    public ListCoursesRegisteredHandler(DataBase db) throws RemoteException {
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
        String sSID = objTokenizer.nextToken();

        // Get the list of courses the given student has registered for.
        Student objStudent = db.getStudentRecord(sSID);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        ArrayList vCourse = objStudent.getRegisteredCourses();

        // Construct a list of course information and return it.
        String sReturn = "";
        for (int i=0; i<vCourse.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
        }
        try {
            FileWriter myWriter = new FileWriter("Lab2-Code/output.txt", true);
            myWriter.write("Courses registered for "+ objStudent.sName + System.lineSeparator() +sReturn + System.lineSeparator() + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in grabbing the output file in ListAllCoursesHandler.");
            e.printStackTrace();
        }
        return sReturn;
    }
}