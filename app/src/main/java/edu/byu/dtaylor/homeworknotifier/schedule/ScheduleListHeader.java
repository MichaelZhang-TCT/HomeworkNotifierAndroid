package edu.byu.dtaylor.homeworknotifier.schedule;

import java.util.Date;

/**
 * Created by liukaichi on 3/23/2016.
 */
public class ScheduleListHeader extends AbstractScheduleListItem {
    private Date date;

    // here getters and setters
    // for title and so on, built
    // using date

    @Override
    public int getType() {
        return AbstractScheduleListItem.TYPE_HEADER;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
