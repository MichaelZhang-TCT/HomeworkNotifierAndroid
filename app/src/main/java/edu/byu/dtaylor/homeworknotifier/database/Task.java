package edu.byu.dtaylor.homeworknotifier.database;

import java.util.Calendar;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;

/**
 * Created by longl on 4/3/2016.
 */
public class Task {
    GsonAssignment assignment;
    Calendar dateAssigned;
    boolean complete;

    public Task(GsonAssignment assignment, Calendar dateAssigned)
    {
        this.assignment = assignment;
        this.dateAssigned = dateAssigned;
        this.complete = false;
    }
}
