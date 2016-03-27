package edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources;

import java.util.Date;

/**
 * Created by liukaichi on 3/23/2016.
 */
public abstract class AbstractScheduleListItem {
    public enum ItemType {HEADER, ASSIGNMENT, TASK};

    abstract public ItemType getItemType();


    public abstract void plan(Date dateBeingViewed);
}

