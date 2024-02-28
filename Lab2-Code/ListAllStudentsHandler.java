/**
 * @(#)ListAllStudentsHandler.java
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

/**
 * "List all students" command event handler.
 */
public class ListAllStudentsHandler extends UnicastRemoteObject implements IActivity {
    private DataBase db;
    /**
     * Construct "List all students" command event handler.
     *
     * * @param db a database
     */
    public ListAllStudentsHandler(DataBase db) throws RemoteException {
        super();
        this.db = db;
    }
    /**
     * Process "List all courses" event.
     *
     * @param param a string
     * @return a string result of command processing
     */
    @Override
    public String execute(String param) throws RemoteException {
        // Get all student records.
        ArrayList vStudent = db.getAllStudentRecords();

        // Construct a list of student information and return it.
        String sReturn = "";
        for (int i=0; i<vStudent.size(); i++) {
            sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
        }
        try {
            FileWriter myWriter = new FileWriter("Lab2-Code/output.txt", true);
            myWriter.write(sReturn + System.lineSeparator() + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in grabbing the output file in ListAllCoursesHandler.");
            e.printStackTrace();
        }
        return sReturn;
    }
}