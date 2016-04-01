package edu.byu.dtaylor.homeworknotifier.schedule;

import android.graphics.Color;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Tanner on 3/11/2016.
 */
public class ScheduleItem {

    private String name;
    private String description;
    private ScheduleItemType type;
    private HashSet<Date> plannedDates; //this is plannedDates time to work on item TODO: refactor to datetime?
    private boolean completed;
    private int color;

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ScheduleItem() {
        plannedDates = new HashSet<>();
        completed = false;
        description = "";
        type = ScheduleItemType.CUSTOM;
        color = Color.RED;
    }
    public ScheduleItem(String description) {
        this.description = description;
        type = ScheduleItemType.CUSTOM;
        plannedDates = new HashSet<>();
        completed = false;
        this.color = Color.RED;
    }
    public ScheduleItem(String name, String description, int color) {
        this.name = name;
        this.description = description;
        type = ScheduleItemType.CUSTOM;
        plannedDates = new HashSet<>();
        completed = false;
        this.color = color;
    }
    public ScheduleItem(String description, ScheduleItemType type) {
        this.description = description;
        this.type = type;
        plannedDates = new HashSet<>();
        completed = false;
    }

    public void complete() {
        completed = true;
    }
    public void unComplete() {
        completed = false;
    }

    public HashSet<Date> getPlannedDates() {
        return plannedDates;
    }

    public void setPlannedDates(HashSet<Date> plannedDates) {
        this.plannedDates = plannedDates;
    }

    public void addPlannedDate(Date date)
    {
        plannedDates.add(date);
    }
    public void removePlannedDate(Date date)
    {
        plannedDates.remove(date);
    }

    public boolean isPlanned()
    {
        return plannedDates.size() != 0 ? true : false;
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

    public int getColor()
    {
        return color;
    }

    public void setType(ScheduleItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Description: " + description + "; Type: " + type.name() + "; Planned: " + plannedDates + "; Completed: " + completed;
    }
}
