package edu.byu.dtaylor.homeworknotifier.schedule;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Tanner on 3/9/2016.
 */
public class Schedule {

    TreeMap<Date,ScheduleDay> days;

    public Schedule() {
        days = new TreeMap<Date,ScheduleDay>();
    }

    public void add(ScheduleItem item, Date d) {
        ScheduleDay sd = days.get(d);
        if(sd == null) {
            sd = new ScheduleDay(d);
            days.put(d,sd);
        }
        sd.addItem(item);
    }
    public Set<Date> getDates(){
        return days.keySet();
    }
    public List<ScheduleItem> getItemsByDate(Date date){
        return days.get(date).getItems();
    }

    public int size() {
        return days.size();
    }

    public void remove(int position) {

    }


    public class ScheduleDay implements Comparable{

        private Date date;
        private List<ScheduleItem> items;

        public ScheduleDay(Date date) {
            items = new LinkedList<ScheduleItem>();
            this.date = date;
        }

        @Override
        public int compareTo(Object o) {
            ScheduleDay d = (ScheduleDay) o;
            return date.compareTo(d.getDate());
        }

        public void addItem(ScheduleItem item) {
            items.add(item);
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public List<ScheduleItem> getItems() {
            return items;
        }

        public void setItems(List<ScheduleItem> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return date.toString() + ": " + items.toString();
        }

    }

    @Override
    public String toString() {
        return days.toString();
    }
}
