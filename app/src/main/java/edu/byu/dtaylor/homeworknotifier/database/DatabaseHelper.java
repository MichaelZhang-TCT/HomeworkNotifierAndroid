package edu.byu.dtaylor.homeworknotifier.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.MainActivity;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonUser;

/**
 * Created by Tanner on 3/28/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private ArrayList<Course> courses;

    public DatabaseHelper(Context context) {
        super(context, HomeworkNotifierContract.DATABASE_NAME, null, HomeworkNotifierContract.DATABASE_VERSION);
        //onUpgrade(this.getWritableDatabase(),-1,-1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HomeworkNotifierContract.SQL_CREATE_CLASSES);
        db.execSQL(HomeworkNotifierContract.SQL_CREATE_ASSIGNMENTS);
        db.execSQL(HomeworkNotifierContract.SQL_CREATE_TASKS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(HomeworkNotifierContract.SQL_DELETE_CLASSES);
        db.execSQL(HomeworkNotifierContract.SQL_DELETE_ASSIGNMENTS);
        db.execSQL(HomeworkNotifierContract.SQL_DELETE_TASKS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void setDBfromGson(GsonDatabase gsonDb) {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, -1, -1);
        GsonUser user = gsonDb.getUser();
        List<Course> courses = user.getCourses();

        for(Course c : courses) {
            ContentValues courseValues = new ContentValues();
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_EXTERNAL_COURSE_ID,c.getId());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_TITLE,c.getTitle());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_SHORT_TITLE, c.getShortTitle());

            long classId = db.insert(HomeworkNotifierContract.Classes.TABLE_NAME,null,courseValues);
            List<Assignment> assignments = c.getAssignments();
            for(Assignment a : assignments) {
                ContentValues assignmentValues = new ContentValues();
                Log.d("Tanner", a.getName());
                if(a.getId() == "?") {
                    continue;
                }

                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CLASS_ID,classId);
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_ASSIGNMENT_ID,a.getId());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_COURSE_ID,a.getCourseID());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_NAME,a.getName());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_DESCRIPTION,a.getDescription());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY,a.getCategory());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY_ID,a.getCategoryID());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_DUE_DATE,a.getDueDate());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_GRADED,a.getGraded());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_POINTS,a.getPoints());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_WEIGHT,a.getWeight());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_TYPE,a.getType());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_REF_URL,a.getUrl());

                db.insert(HomeworkNotifierContract.Assignments.TABLE_NAME,null,assignmentValues);
            }
        }
        MainActivity.database = new Database(gsonDb);
    }

    public void insertTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues courseValues = new ContentValues();
        courseValues.put(HomeworkNotifierContract.Tasks.COLUMN_NAME_ASSIGNMENT_ID,task.getAssignmentId());
        courseValues.put(HomeworkNotifierContract.Tasks.COLUMN_NAME_COURSE_ID, task.getCourseId());
        courseValues.put(HomeworkNotifierContract.Tasks.COLUMN_NAME_COMPLETED, task.isCompleted());
        courseValues.put(HomeworkNotifierContract.Tasks.COLUMN_NAME_ASSIGNED_DATE, task.getAssignedDate());
        courseValues.put(HomeworkNotifierContract.Tasks.COLUMN_NAME_COLOR, task.getColor());

        long taskId = db.insert(HomeworkNotifierContract.Tasks.TABLE_NAME, null, courseValues);
    }

    public Database getDatabaseFromSql()
    {
        ArrayList<Course> courses = getCourses();
        ArrayList<Task> tasks = getTasks();
        if(courses != null && tasks != null)
            return new Database(courses, tasks);
        else
        {
            Log.e("DatabaseHelper","Failed to load database");
            return null;
        }
    }

    public boolean updateDatabase(GsonDatabase gsonDb) {
        ArrayList<Task> tasks = MainActivity.database.getTasks();
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, -1, -1);
        GsonUser user = gsonDb.getUser();
        List<Course> courses = user.getCourses();

        for(Course c : courses) {
            ContentValues courseValues = new ContentValues();
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_EXTERNAL_COURSE_ID,c.getId());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_TITLE,c.getTitle());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_SHORT_TITLE, c.getShortTitle());

            long classId = db.insert(HomeworkNotifierContract.Classes.TABLE_NAME,null,courseValues);
            List<Assignment> assignments = c.getAssignments();
            for(Assignment a : assignments) {
                ContentValues assignmentValues = new ContentValues();
                Log.d("Tanner", a.getName());
                if(a.getId() == "?") {
                    continue;
                }

                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CLASS_ID,classId);
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_ASSIGNMENT_ID,a.getId());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_COURSE_ID,a.getCourseID());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_NAME,a.getName());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_DESCRIPTION,a.getDescription());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY,a.getCategory());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY_ID,a.getCategoryID());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_DUE_DATE,a.getDueDate());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_GRADED,a.getGraded());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_POINTS,a.getPoints());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_WEIGHT,a.getWeight());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_TYPE,a.getType());
                assignmentValues.put(HomeworkNotifierContract.Assignments.COLUMN_NAME_REF_URL,a.getUrl());

                db.insert(HomeworkNotifierContract.Assignments.TABLE_NAME,null,assignmentValues);
            }
        }
        for(Task task : tasks)
        {
            insertTask(task);
        }
        Database data = getDatabaseFromSql();
        if(MainActivity.database != null)
        {
            MainActivity.database = data;
            return true;
        }
        return false;
    }

    public ArrayList<Course> getCourses() {

        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HomeworkNotifierContract.Classes.COLUMN_NAME_EXTERNAL_COURSE_ID,
                HomeworkNotifierContract.Classes.COLUMN_NAME_TITLE,
                HomeworkNotifierContract.Classes.COLUMN_NAME_SHORT_TITLE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                HomeworkNotifierContract.Classes.COLUMN_NAME_TITLE + " DESC";


        Cursor courseQuery = db.query(
                true,
                HomeworkNotifierContract.Classes.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                /*selection*/null,                                // The columns for the WHERE clause
                /*selectionArgs*/null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,
                null// The sort order
        );
        ArrayList<Course> courses = new ArrayList<>();
        if(courseQuery.getCount() != 0) {
            courseQuery.moveToFirst();
            for (int i = 0; i < courseQuery.getCount(); i++) {
                String courseId = courseQuery.getString(0);
                String courseTitle = courseQuery.getString(1);
                String courseShortTitle = courseQuery.getString(2);
                courses.add(new Course(courseId, courseTitle, courseShortTitle));
                courseQuery.moveToNext();
            }
            courseQuery.close();
            for (Course course : courses) {
                ArrayList<Assignment> assignments = getAssignments(course);
                if(assignments != null)
                    course.setAssignments(assignments);
            }
            return courses;
        }
        return null;
    }

    private ArrayList<Task> getTasks()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HomeworkNotifierContract.Tasks.COLUMN_NAME_ASSIGNMENT_ID,
                HomeworkNotifierContract.Tasks.COLUMN_NAME_COURSE_ID,
                HomeworkNotifierContract.Tasks.COLUMN_NAME_COMPLETED,
                HomeworkNotifierContract.Tasks.COLUMN_NAME_ASSIGNED_DATE,
                HomeworkNotifierContract.Tasks.COLUMN_NAME_COLOR
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                HomeworkNotifierContract.Tasks.COLUMN_NAME_ASSIGNED_DATE + " DESC";

        Cursor tasksQuery = db.query(
                HomeworkNotifierContract.Tasks.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                /*selection*/null,                                // The columns for the WHERE clause
                /*selectionArgs*/null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ArrayList<Task> tasks = new ArrayList<>();
        if(tasksQuery.getCount() != 0) {
            tasksQuery.moveToFirst();
            for (int i = 0; i < tasksQuery.getCount(); i++) {
                String assignmentId = tasksQuery.getString(0);
                String courseId = tasksQuery.getString(1);
                String completed = tasksQuery.getString(2);
                String assignedDate = tasksQuery.getString(3);
                String color = tasksQuery.getString(4);
                tasks.add(new Task(assignmentId, courseId, completed, assignedDate, color));
                tasksQuery.moveToNext();
            }
            tasksQuery.close();
            return tasks;
        }
        return null;
    }

    private ArrayList<Assignment> getAssignments(Course course) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HomeworkNotifierContract.Assignments.COLUMN_NAME_CLASS_ID,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_ASSIGNMENT_ID,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_COURSE_ID,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_NAME,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_DESCRIPTION,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_CATEGORY_ID,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_DUE_DATE,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_GRADED,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_POINTS,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_WEIGHT,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_TYPE,
                HomeworkNotifierContract.Assignments.COLUMN_NAME_REF_URL
        };

        String[] selection = {
                HomeworkNotifierContract.Assignments.COLUMN_NAME_EXTERNAL_COURSE_ID
        };

        String[] selectionArgs = {
                course.getId()
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                HomeworkNotifierContract.Assignments.COLUMN_NAME_DUE_DATE + " DESC";

        Cursor assignmentsQuery = db.query(
                true,
                HomeworkNotifierContract.Assignments.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                /*selection*/null,                                // The columns for the WHERE clause
                /*selectionArgs*/null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                null
        );
        ArrayList<Assignment> assignments = new ArrayList<>();
        if(assignmentsQuery.getCount() != 0) {
            assignmentsQuery.moveToFirst();
            for (int i = 0; i < assignmentsQuery.getCount(); i++) {
                String classId = assignmentsQuery.getString(0);
                String assignmentId = assignmentsQuery.getString(1);
                String courseId = assignmentsQuery.getString(2);
                String name = assignmentsQuery.getString(3);
                String description = assignmentsQuery.getString(4);
                String category = assignmentsQuery.getString(5);
                String categoryId = assignmentsQuery.getString(6);
                String dueDate = assignmentsQuery.getString(7);
                String graded = assignmentsQuery.getString(8);
                String points = assignmentsQuery.getString(9);
                String weight = assignmentsQuery.getString(10);
                String type = assignmentsQuery.getString(11);
                String refUrl = assignmentsQuery.getString(12);
                assignments.add(new Assignment(assignmentId, courseId, name, description, category, categoryId, dueDate, graded, points, weight, type, refUrl));
                assignmentsQuery.moveToNext();
            }
            assignmentsQuery.close();
            return assignments;
        }
        return null;
    }
}
