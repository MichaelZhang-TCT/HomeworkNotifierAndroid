package edu.byu.dtaylor.homeworknotifier.schedule;

import android.graphics.Color;
import android.util.Log;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Tanner on 3/11/2016.
 */
public class ScheduleItem {

    private static final String TAG = "ScheduleItem";
    Date dueDate;
    String category;
    String courseID;
    boolean graded;
    double points;
    String url;
    double weight;
    String shortTitle;
    String title;

    private String name;
    private String description;
    private ScheduleItemType type;
    private HashSet<Date> plannedDates; //this is plannedDates time to work on item TODO: refactor to datetime?
    private boolean completed;
    private int color;
    private String assignmentId;

    public ScheduleItem(String name,
                        String description,
                        int color,
                        String category,
                        String courseID,
                        String shortTitle,
                        String title,
                        Date dueDate,
                        boolean graded,
                        double points,
                        String type,
                        String url,
                        double weight,
                        String assignmentId) {

        this.name = name;
        this.description = description;
        this.color = color;
        this.category = category;
        this.courseID = courseID;
        this.shortTitle = shortTitle;
        this.title = title;
        this.dueDate = dueDate;
        this.graded = graded;
        this.points = points;
        this.assignmentId = assignmentId;
        //TODO: Deside whether to base off of category or type.
        if (type.toLowerCase().matches(".*assignment.*"))
        {
            if (category.toLowerCase().matches(".*lab.*|.* project .*"))
            {
                this.type = ScheduleItemType.CUSTOM;
            } else if (category.toLowerCase().matches(".*read.*"))
            {
                this.type = ScheduleItemType.READING;
            }
            else {
                this.type = ScheduleItemType.HOMEWORK;
            }

        }
        else if (type.toLowerCase().matches(".*test.*|.*exam.*"))
        {
            if (category.toLowerCase().matches(".*quiz.*"))
            {
                this.type = ScheduleItemType.QUIZ;
            }
            else this.type = ScheduleItemType.TEST;
        }

        else {
            this.type = ScheduleItemType.OTHER;
        }
        Log.d(TAG, "Type was received as: " + type);
        this.url = url;
        this.weight = weight;

        //our custom stuff;
        plannedDates = new HashSet<>();
        completed = false;
    }


    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ScheduleItem() {
        plannedDates = new HashSet<>();
        completed = false;
        description = "";
        type = ScheduleItemType.CUSTOM;
        color = Color.RED;
    }
    public ScheduleItem(String description) {
        this.description = description;
        type = ScheduleItemType.CUSTOM;
        plannedDates = new HashSet<>();
        completed = false;
        this.color = Color.RED;
    }
    public ScheduleItem(String name, String description, int color) {
        this.name = name;
        this.description = description;
        type = ScheduleItemType.CUSTOM;
        plannedDates = new HashSet<>();
        completed = false;
        this.color = color;
    }
    public ScheduleItem(String description, ScheduleItemType type) {
        this.description = description;
        this.type = type;
        plannedDates = new HashSet<>();
        completed = false;
    }

    public void complete() {
        completed = true;
    }
    public void unComplete() {
        completed = false;
    }

    public HashSet<Date> getPlannedDates() {
        return plannedDates;
    }

    public void setPlannedDates(HashSet<Date> plannedDates) {
        this.plannedDates = plannedDates;
    }

    public void addPlannedDate(Date date)
    {
        plannedDates.add(date);
    }
    public void removePlannedDate(Date date)
    {
        plannedDates.remove(date);
    }

    public boolean isPlanned()
    {
        return plannedDates.size() != 0 ? true : false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScheduleItemType getType() {
        return type;
    }

    public int getColor()
    {
        return color;
    }

    public void setType(ScheduleItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Description: " + description + "; Type: " + type.name() + "; Planned: " + plannedDates + "; Completed: " + completed;
    }

    public String getCategory() {
        return category;
    }

    public String getCourseID() {
        return courseID;
    }

    public boolean isGraded() {
        return graded;
    }

    public double getPoints() {
        return points;
    }

    public String getUrl() {
        return url;
    }

    public double getWeight() {
        return weight;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getAssignmentId() {
        return assignmentId;
    }
}
