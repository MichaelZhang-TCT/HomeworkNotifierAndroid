package edu.byu.dtaylor.homeworknotifier;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import edu.byu.dtaylor.homeworknotifier.database.DatabaseHelper;
import edu.byu.dtaylor.homeworknotifier.database.HomeworkNotifierContract;
import edu.byu.dtaylor.homeworknotifier.gsontools.Assignment;
import edu.byu.dtaylor.homeworknotifier.gsontools.Course;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;
import edu.byu.dtaylor.homeworknotifier.gsontools.User;

public class SplashScreenActivity extends AppCompatActivity implements CustomTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new CustomAsyncTask(this).execute("daviddt2","davidpaseo3");
    }

    @Override
    public void onPostExecute(Object object) {
        MainActivity.database = (GsonDatabase)object;
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    public Object doInBackground(Object[] params) {
        String netID = getIntent().getStringExtra("netID");
        String password = getIntent().getStringExtra("password");

        GsonDatabase gsonDb = Utils.getAllInfo(this, netID, password);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        User user = gsonDb.getUser();
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
                Log.d("Tanner",a.getName());
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

        return gsonDb;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Object[] values) {

    }
}
