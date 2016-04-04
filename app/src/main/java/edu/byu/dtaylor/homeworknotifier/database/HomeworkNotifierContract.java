package edu.byu.dtaylor.homeworknotifier.database;

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
        public static final String COLUMN_NAME_EXTERNAL_COURSE_ID = "course_id";
        public static final String COLUMN_NAME_TITLE = "course_title";
        public static final String COLUMN_NAME_SHORT_TITLE = "course_short_title";
    }

    public static abstract class Tasks implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_ASSIGNMENT_ID = "assignment_id";
        public static final String COLUMN_NAME_COURSE_ID = "course_id";
        public static final String COLUMN_NAME_DUE_DATE = "due_date";
        public static final String COLUMN_NAME_ASSIGNED_DATE = "assigned_date";
    }

    public static abstract class Assignments implements BaseColumns {
        public static final String TABLE_NAME = "assignments";
        public static final String COLUMN_NAME_CLASS_ID = "class_id";
        public static final String COLUMN_NAME_EXTERNAL_COURSE_ID = "external_course_id";
        public static final String COLUMN_NAME_EXTERNAL_ASSIGNMENT_ID = "external_assignment_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DUE_DATE = "due_date";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_GRADED = "graded";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_REF_URL = "ref_url";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String REAL_TYPE = " REAL";
    public static final String PRIMARY_KEY = INTEGER_TYPE + " PRIMARY KEY";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_CLASSES =
            "CREATE TABLE " + Classes.TABLE_NAME + " (" +
                    Classes._ID + PRIMARY_KEY + COMMA_SEP +
                    Classes.COLUMN_NAME_EXTERNAL_COURSE_ID + TEXT_TYPE + COMMA_SEP +
                    Classes.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    Classes.COLUMN_NAME_SHORT_TITLE + TEXT_TYPE +
            " )";

    public static final String SQL_CREATE_TASKS =
            "CREATE TABLE " + Tasks.TABLE_NAME + " (" +
                    Tasks._ID + PRIMARY_KEY + COMMA_SEP +
                    Tasks.COLUMN_NAME_ASSIGNMENT_ID + TEXT_TYPE + COMMA_SEP +
                    Tasks.COLUMN_NAME_COURSE_ID + TEXT_TYPE + COMMA_SEP +
                    Tasks.COLUMN_NAME_DUE_DATE + INTEGER_TYPE + COMMA_SEP +
                    Tasks.COLUMN_NAME_ASSIGNED_DATE + INTEGER_TYPE +
                    " )";

    public static final String SQL_CREATE_ASSIGNMENTS =
            "CREATE TABLE " + Assignments.TABLE_NAME + " (" +
                    Assignments._ID + PRIMARY_KEY + COMMA_SEP +
                    Assignments.COLUMN_NAME_EXTERNAL_ASSIGNMENT_ID + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_CLASS_ID + INTEGER_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_EXTERNAL_COURSE_ID + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_DUE_DATE + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_CATEGORY_ID + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_GRADED + INTEGER_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_POINTS + INTEGER_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_WEIGHT + REAL_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    Assignments.COLUMN_NAME_REF_URL + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_CLASSES =
            "DROP TABLE IF EXISTS " + Classes.TABLE_NAME;
    public static final String SQL_DELETE_TASKS =
            "DROP TABLE IF EXISTS " + Tasks.TABLE_NAME;
    public static final String SQL_DELETE_ASSIGNMENTS =
            "DROP TABLE IF EXISTS " + Assignments.TABLE_NAME;
}
