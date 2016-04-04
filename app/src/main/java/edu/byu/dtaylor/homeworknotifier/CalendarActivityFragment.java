package edu.byu.dtaylor.homeworknotifier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.database.Assignment;
import edu.byu.dtaylor.homeworknotifier.database.Task;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalendarActivityFragment extends Fragment {

    private Context parentActivity;
    private Calendar currentDay;
    public RecyclerView taskRecyclerView;
    public List<AbstractScheduleListItem> taskRecyclerListItems;
    public ScheduleRVAdapter taskAdapter;

    public CalendarActivityFragment()
    {
    }
    @SuppressLint("ValidFragment")
    public CalendarActivityFragment(Context parentActivity, Calendar currentDay)
    {
        this.parentActivity = parentActivity;
        this.currentDay = currentDay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize task list
        taskRecyclerListItems = new ArrayList<>();
        for(Task task : MainActivity.database.getTasks())
        {
            Calendar day = Calendar.getInstance();
            day.setTimeInMillis(task.getAssignedDate());
            if(day.get(Calendar.DAY_OF_YEAR) == currentDay.get(Calendar.DAY_OF_YEAR))
            {
                Assignment a = task.getAssignment();
                taskRecyclerListItems.add(new ScheduleListItem(new ScheduleItem(a.getName(),a.getDescription(), Color.RED, a.getCategory(), a.getCourseId(), "short title", "title", new Date(a.getDueDate()), a.isGraded(), a.getPoints(), a.getType(), a.getRefUrl(), a.getWeight(), a.getAssignmentId()), AbstractScheduleListItem.ItemType.TASK));
            }
        }
        //add tasks that are already planned.
        taskRecyclerView = (RecyclerView) view.findViewById(R.id.task_RV);
        taskRecyclerView.setHasFixedSize(true);
        taskAdapter = new ScheduleRVAdapter(taskRecyclerListItems, getActivity());
        taskRecyclerView.setAdapter(taskAdapter);
        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(getActivity());
        taskRecyclerView.setLayoutManager(taskLayoutManager);
    }

    public void updateUI(Calendar calendar) {

        View v = getView();
        if (v == null)
            return;
        //setNewDataSource calcurate new List<String> date
        currentDay = calendar;
        CalendarPageAdapter calendarViewAdapter = (CalendarPageAdapter)((ViewPager) ((MainActivity)parentActivity).findViewById(R.id.calendar_view_pager)).getAdapter();
        calendarViewAdapter.setNewDataSource(calendar);
        calendarViewAdapter.notifyDataSetChanged();
    }

    public Calendar getSelectedDay() {
        return currentDay;
    }
}
