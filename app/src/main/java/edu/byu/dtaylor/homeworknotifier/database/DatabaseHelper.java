package edu.byu.dtaylor.homeworknotifier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
