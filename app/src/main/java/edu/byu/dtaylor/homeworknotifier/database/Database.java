package edu.byu.dtaylor.homeworknotifier.database;

import java.util.ArrayList;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

/**
 * Created by longl on 4/3/2016.
 */
public class Database {
    ArrayList<Task> tasks;
    GsonDatabase db;

    public Database()
    {
        tasks = new ArrayList<>();
    }
}
