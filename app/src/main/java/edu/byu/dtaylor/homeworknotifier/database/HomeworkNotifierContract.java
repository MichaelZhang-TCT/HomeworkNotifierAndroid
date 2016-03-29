package edu.byu.dtaylor.homeworknotifier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Tanner on 3/26/2016.
 */
public final class HomeworkNotifierContract {

    public HomeworkNotifierContract(){}

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HomeworkNotifier.db";

    public static abstract class Classes implements BaseColumns {
        public static final String TABLE_NAME = "classes";
        public static final String COLUMN_NAME_COURSE_ID = "course_id";
        public static final String COLUMN_NAME_COURSE_NAME = "course_name";
    }

    public static abstract class Assignments implements BaseColumns {
        public static final String TABLE_NAME = "assignments";
        public static final String COLUMN_NAME_ASSIGNMENT_ID = "assignment_id";
        public static final String COLUMN_NAME_ASSIGNMENT_NAME = "assignment_name";
        public static final String COLUMN_NAME_DUE_DATE = "due_date";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_CLASSES =
            "CREATE TABLE " + Classes.TABLE_NAME + " (" +
                    Classes._ID + " INTEGER PRIMARY KEY," +
                    Classes.COLUMN_NAME_COURSE_ID + TEXT_TYPE + COMMA_SEP +
                    Classes.COLUMN_NAME_COURSE_NAME + TEXT_TYPE +
            " )";

    public static final String SQL_CREATE_ASSIGNMENTS =
            "CREATE TABLE " + Assignments.TABLE_NAME + " (" +
                    Assignments._ID + " INTEGER PRIMARY KEY," +
                    Assignments.COLUMN_NAME_ASSIGNMENT_ID + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_ASSIGNMENT_NAME + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_DUE_DATE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_CLASSES =
            "DROP TABLE IF EXISTS " + Classes.TABLE_NAME;
    public static final String SQL_DELETE_ASSIGNMENTS =
            "DROP TABLE IF EXISTS " + Assignments.TABLE_NAME;
}
