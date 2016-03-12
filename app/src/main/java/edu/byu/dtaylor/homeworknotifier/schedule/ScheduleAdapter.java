package edu.byu.dtaylor.homeworknotifier.schedule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Tanner on 3/12/2016.
 */
public class ScheduleAdapter extends BaseAdapter {

    Schedule schedule;
    public ScheduleAdapter(Context c,Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public int getCount() {
        return schedule.size();
    }

    @Override
    public Object getItem(int position) {
        //TODO
        return null;
    }

    @Override
    public long getItemId(int position) {
        //TODO
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO
        return null;
    }
}

