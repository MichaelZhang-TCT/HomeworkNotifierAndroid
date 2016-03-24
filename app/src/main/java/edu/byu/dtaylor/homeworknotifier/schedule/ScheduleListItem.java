package edu.byu.dtaylor.homeworknotifier.schedule;

/**
 * Created by liukaichi on 3/23/2016.
 */
public class ScheduleListItem extends AbstractScheduleListItem {

    ScheduleItem scheduleItem;

    // here getters and setters
    // for title and so on, built
    // using event


    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }

    @Override
    public int getType() {
        return AbstractScheduleListItem.TYPE_ASSIGNMENT;
    }
}
