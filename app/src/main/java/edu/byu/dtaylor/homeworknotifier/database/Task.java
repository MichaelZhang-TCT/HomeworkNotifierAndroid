package edu.byu.dtaylor.homeworknotifier.database;

import java.util.ArrayList;
import java.util.Calendar;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;

/**
 * Created by longl on 4/3/2016.
 */
public class Task {
    GsonAssignment assignment;
    ArrayList<Calendar> dateAssigned = new ArrayList<>();
    boolean complete;

    public Task(GsonAssignment assignment)
    {
        this.assignment = assignment;
        this.complete = false;
    }
}
