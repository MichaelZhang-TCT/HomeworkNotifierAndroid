package edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources;

import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.database.DatabaseHelper;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItemType;

/**
 * Created by liukaichi on 3/23/2016.
 */
public class ScheduleListItem extends AbstractScheduleListItem {

    ScheduleItem scheduleItem;
    ItemType itemType = ItemType.ASSIGNMENT;
    private int color;
    private String assignmentId;

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
        return scheduleItem.getName();
    }

    public String getDescription() {
        return scheduleItem.getDescription();
    }

    public int getColor() {
        return scheduleItem.getColor();
    }

    public String getCategory() {
        return scheduleItem.getCategory();
    }

    public String getCourseID() {
        return scheduleItem.getCourseID();
    }

    public boolean isGraded() {
        return scheduleItem.isGraded();
    }

    public double getPoints() {
        return scheduleItem.getPoints();
    }

    public String getUrl() {
        return scheduleItem.getUrl();
    }

    public double getWeight() {
        return scheduleItem.getWeight();
    }

    public String getShortTitle() {
        return scheduleItem.getShortTitle();
    }

    public String getTitle() {
        return scheduleItem.getTitle();
    }

    public Date getDueDate()
    {
        return scheduleItem.getDueDate();
    }

    public ScheduleItemType getType()
    {
        return scheduleItem.getType();
    }

    public String getAssignmentId() {
        return scheduleItem.getAssignmentId();
    }
}
