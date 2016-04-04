package edu.byu.dtaylor.homeworknotifier.database;

import java.util.ArrayList;
import java.util.Calendar;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;

/**
 * Created by longl on 4/3/2016.
 */
public class Assignment {
    private String courseId;
    private String name;
    private String description;
    private String category;
    private String categoryId;
    private int dueDate;
    private boolean graded;
    private double points;
    private double weight;
    private String type;
    private String refUrl;
    private String assignmentId;
    ArrayList<Calendar> dateAssigned = new ArrayList<>();
    boolean complete;

    public Assignment(GsonAssignment assignment)
    {
        this.courseId = assignment.getCourseID();
        this.name = assignment.getName();
        this.description = assignment.getDescription();
        this.category = assignment.getCategory();
        this.dueDate = assignment.getDueDate();
        this.graded = assignment.getGraded();
        this.points = assignment.getPoints();
        this.weight = assignment.getWeight();
        this.type = assignment.getType();
        this.refUrl = assignment.getUrl();
        this.assignmentId = assignment.getId();
        this.complete = false;
    }

    public Assignment(String assignmentId, String courseId, String name, String description, String category, String categoryId, String dueDate, String graded, String points, String weight, String type, String refUrl) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.categoryId = categoryId;
        this.dueDate = Integer.parseInt(dueDate);
        this.graded = Boolean.parseBoolean(graded);
        this.points = Double.parseDouble(points);
        this.weight = Double.parseDouble(weight);
        this.type = type;
        this.refUrl = refUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getDueDate() {
        return dueDate;
    }

    public boolean isGraded() {
        return graded;
    }

    public double getPoints() {
        return points;
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public ArrayList<Calendar> getDateAssigned() {
        return dateAssigned;
    }

    public boolean isComplete() {
        return complete;
    }
}
