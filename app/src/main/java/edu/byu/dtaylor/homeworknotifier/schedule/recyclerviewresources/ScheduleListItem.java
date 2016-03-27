package edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources;

import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;

/**
 * Created by liukaichi on 3/23/2016.
 */
public class ScheduleListItem extends AbstractScheduleListItem {

    ScheduleItem scheduleItem;
    ItemType itemType = ItemType.ASSIGNMENT;

    public ScheduleListItem(AbstractScheduleListItem abstractScheduleListItem, ItemType itemType) {
        this.scheduleItem = ((ScheduleListItem) abstractScheduleListItem).scheduleItem;
        this.itemType = itemType;
    }

    public ScheduleListItem(ScheduleItem assignment, ItemType itemType) {
        this.scheduleItem = assignment;
        this.itemType = itemType;
    }
    // here getters and setters
    // for title and so on, built
    // using event


    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }
    public ScheduleItem getScheduleItem()
    {
        return scheduleItem;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }
    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }

    @Override
    public void plan(Date dateBeingViewed) {
        scheduleItem.addPlannedDate(dateBeingViewed);
    }


    public String getName(){
        return scheduleItem.getDescription();
    }
}
