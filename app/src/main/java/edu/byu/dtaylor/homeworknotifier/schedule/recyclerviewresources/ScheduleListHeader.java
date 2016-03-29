package edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources;

import android.util.Log;

import java.util.Date;

/**
 * Created by liukaichi on 3/23/2016.
 */
public class ScheduleListHeader extends AbstractScheduleListItem {
    private static final String TAG = "ScheduleListHeader";
    private Date date;

    // here getters and setters
    // for title and so on, built
    // using date

    @Override
    public ItemType getItemType() {
        return ItemType.HEADER;
    }

    @Override
    public void plan(Date dateBeingViewed) {
        Log.e(TAG, "Tryin to plan a header...this shouldnt' be possible.");
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
