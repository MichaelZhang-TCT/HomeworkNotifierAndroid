package edu.byu.dtaylor.homeworknotifier.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonCourse;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonUser;

/**
 * Created by Tanner on 3/28/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, HomeworkNotifierContract.DATABASE_NAME, null, HomeworkNotifierContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HomeworkNotifierContract.SQL_CREATE_CLASSES);
        db.execSQL(HomeworkNotifierContract.SQL_CREATE_ASSIGNMENTS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(HomeworkNotifierContract.SQL_DELETE_CLASSES);
        db.execSQL(HomeworkNotifierContract.SQL_DELETE_ASSIGNMENTS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void updateDB(GsonDatabase gsonDb) {
        SQLiteDatabase db = this.getWritableDatabase();

        GsonUser user = gsonDb.getUser();
        List<GsonCourse> courses = user.getCourses();

        for(GsonCourse c : courses) {
            ContentValues courseValues = new ContentValues();
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_EXTERNAL_COURSE_ID,c.getId());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_TITLE,c.getTitle());
            courseValues.put(HomeworkNotifierContract.Classes.COLUMN_NAME_SHORT_TITLE, c.getShortTitle());

            long classId = db.insert(HomeworkNotifierContract.Classes.TABLE_NAME,null,courseValues);
            List<GsonAssignment> assignments = c.getAssignments();
            for(GsonAssignment a : assignments) {
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
    }

    public GsonDatabase getDatabase()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return null;
    }
}
