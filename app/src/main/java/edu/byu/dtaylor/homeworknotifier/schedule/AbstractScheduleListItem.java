package edu.byu.dtaylor.homeworknotifier.schedule;

import java.util.Date;

/**
 * Created by liukaichi on 3/23/2016.
 */
public abstract class AbstractScheduleListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ASSIGNMENT = 1;

    abstract public int getType();
}

