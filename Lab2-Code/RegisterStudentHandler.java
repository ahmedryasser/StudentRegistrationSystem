/**
 * @(#)RegisterStudentHandler.java
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
 * "Register a student for a course" command event handler.
 */
public class RegisterStudentHandler  extends UnicastRemoteObject implements IActivity {

    /**
     * Construct "Register a student for a course"  handler.
     *
     */
    private DataBase db;
    public RegisterStudentHandler(DataBase db) throws RemoteException {
        super();
        this.db = db;
    }
    /**
     * Process "Register a student for a course" event.
     *
     * @param param a string
     * @return a string result of command processing
     */
    @Override
    public String execute(String param) throws RemoteException {
        // Parse the parameters.
        StringTokenizer objTokenizer = new StringTokenizer(param);
        String sSID     = objTokenizer.nextToken();
        String sCID     = objTokenizer.nextToken();
        String sSection = objTokenizer.nextToken();

        // Get the student and course records.
        Student objStudent = db.getStudentRecord(sSID);
        Course objCourse = db.getCourseRecord(sCID, sSection);
        if (objStudent == null) {
            return "Invalid student ID";
        }
        if (objCourse == null) {
            return "Invalid course ID or course section";
        }

        // Check if the given course conflicts with any of the courses the student has registered.
        ArrayList vCourse = objStudent.getRegisteredCourses();
//        for (int i=0; i<vCourse.size(); i++) {
//            if (((Course) vCourse.get(i)).conflicts(objCourse)) {
//                return "Registration conflicts";
//            }
//        }
        ConflictHandler conflictHandler = new ConflictHandler(objStudent, objCourse);
        String conflictResult = conflictHandler.checkConflict();
        if (conflictResult != null) {
            return conflictResult; //Return "Registration conflicts" if any conflict exists
        }
        Boolean overbooked = objCourse.vRegistered.size()>2?true:false;

        // Request validated. Proceed to register.
        db.makeARegistration(sSID, sCID, sSection);
        try {
            FileWriter myWriter = new FileWriter("Lab2-Code/output.txt", true);
            myWriter.write("Student "+sSID+" has been registered to class " + sCID+ " section " + sSection + System.lineSeparator() + System.lineSeparator());
            if(overbooked){
                myWriter.write("Warning: Class is overbooked");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in grabbing the output file in ListAllCoursesHandler.");
            e.printStackTrace();
        }
        return "Successful!";
    }
}