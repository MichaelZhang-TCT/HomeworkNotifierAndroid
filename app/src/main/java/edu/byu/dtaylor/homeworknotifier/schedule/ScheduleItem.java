package edu.byu.dtaylor.homeworknotifier.schedule;

/**
 * Created by Tanner on 3/11/2016.
 */
public class ScheduleItem {

    private String description;
    private ScheduleItemType type;
    private boolean planned; //this is planned time to work on item TODO: refactor to datetime?
    private boolean completed;

    public ScheduleItem() {
        planned = false;
        completed = false;
        description = "";
        type = ScheduleItemType.CUSTOM;
    }
    public ScheduleItem(String description) {
        this.description = description;
        type = ScheduleItemType.CUSTOM;
        planned = false;
        completed = false;
    }
    public ScheduleItem(String description, ScheduleItemType type) {
        this.description = description;
        this.type = type;
        planned = false;
        completed = false;
    }

    public void complete() {
        completed = true;
    }
    public void unComplete() {
        completed = false;
    }

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScheduleItemType getType() {
        return type;
    }

    public void setType(ScheduleItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Description: " + description + "; Type: " + type.name() + "; Planned: " + planned + "; Completed: " + completed;
    }
}
