package edu.byu.dtaylor.homeworknotifier.helpers;

import java.util.Date;
import java.util.List;

/**
 * Created by Tanner on 3/9/2016.
 */
public class Schedule {

    public Schedule() {

    }

    public void add() {
        //TODO
    }

    public class ScheduleDay {
        private Date date;
        private List<ScheduleItem> items;

    }

    public class ScheduleItem {
        private String description;
        private ScheduleItemType type;
        private boolean planned; //this is planned time to work on item TODO: refactor to datetime?
        private boolean completed;

        public ScheduleItem() {

        }

        public void complete() {
            completed = true;
        }
        public void unComplete() {
            completed = false;
        }
    }

    public enum ScheduleItemType{
        HOMEWORK,
        QUIZ,
        TEST,
        READING,
        OTHER,
        CUSTOM
    }
}
