package edu.byu.dtaylor.homeworknotifier.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.Utils;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

/**
 * Created by longl on 4/3/2016.
 */
public class Database {
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();

    HashMap<String,List<Task>> tasksByAssignment = new HashMap<>();
    HashMap<String,Course> courseById = new HashMap<>();
    HashMap<String,Assignment> assignmentById = new HashMap<>();
    HashMap<Date,List<String>> assignmentIdsByDueDate = new HashMap<>();

    public Database(GsonDatabase db)
    {
        for(Course course : db.getUser().getCourses())
        {
            courses.add(course);
            courseById.put(course.getId(),course);
            for(Assignment assignment : course.getAssignments())
            {
                assignmentById.put(assignment.getId(), assignment);
                Date dueDate = Utils.normalizeDate(new Date(assignment.getDueDate()));
                if(!assignmentIdsByDueDate.containsKey(dueDate))
                    assignmentIdsByDueDate.put(dueDate,new ArrayList<String>());
                assignmentIdsByDueDate.get(dueDate).add(assignment.getId());
            }
        }
    }

    public Database(ArrayList<Course> courses, ArrayList<Task> tasks) {
        this.courses = courses;
        this.tasks = tasks;
        courseById.clear();
        assignmentById.clear();
        for(Course course : courses)
        {
            courseById.put(course.getId(), course);
            for (Assignment assignment : course.getAssignments()) {
                assignmentById.put(assignment.getId(), assignment);
                Date dueDate = Utils.normalizeDate(new Date(assignment.getDueDate()));
                if(!assignmentIdsByDueDate.containsKey(dueDate))
                    assignmentIdsByDueDate.put(dueDate,new ArrayList<String>());
                assignmentIdsByDueDate.get(dueDate).add(assignment.getId());
            }
        }
        tasksByAssignment.clear();
        for(Task task : tasks)
        {
            if(!tasksByAssignment.containsKey(task.getAssignmentId()))
                tasksByAssignment.put(task.getAssignmentId(), new ArrayList<Task>());
            tasksByAssignment.get(task.getAssignmentId()).add(task);
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addTask(Task task, Context context)
    {
        tasks.add(task);
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.insertTask(task);
        dbHelper.close();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTaskByAssignmentId(String assignmentId)
    {
        return tasksByAssignment.get(assignmentId);
    }

    public Course getCourseById(String courseId)
    {
        return courseById.get(courseId);
    }

    public Assignment getAssignmentById(String assignmentId)
    {
        return assignmentById.get(assignmentId);
    }

    public List<Assignment> getAssignmentsByDueDate(Date dueDate)
    {
        Date normalized = Utils.normalizeDate(dueDate);
        List<String> ids = assignmentIdsByDueDate.get(normalized);
        ArrayList<Assignment> assignments = new ArrayList<>();
        if (ids != null) {
            for (String id : ids) {
                Assignment assignment = assignmentById.get(id);
                if (assignment != null)
                    assignments.add(assignment);
            }
        }
        return assignments;
    }


}
