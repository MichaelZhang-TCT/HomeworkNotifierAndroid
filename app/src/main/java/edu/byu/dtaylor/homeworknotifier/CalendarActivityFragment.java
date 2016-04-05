package edu.byu.dtaylor.homeworknotifier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.database.Assignment;
import edu.byu.dtaylor.homeworknotifier.database.DatabaseHelper;
import edu.byu.dtaylor.homeworknotifier.database.Task;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.TaskRVAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalendarActivityFragment extends Fragment {

    private Context parentActivity;
    private Calendar currentDay;
    public RecyclerView taskRecyclerView;
    public List<AbstractScheduleListItem> taskRecyclerListItems;
    public TaskRVAdapter taskAdapter;

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
        final View view2 = view;
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.calendar_swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Snackbar.make(view2,"refreshing",Snackbar.LENGTH_SHORT);
                new AsyncTask<Void,Void,GsonDatabase>()
                {
                    @Override
                    protected GsonDatabase doInBackground(Void... params) {
                        String netID = MainActivity.settings.getString("netID",null);
                        String password = MainActivity.settings.getString("password",null);

                        GsonDatabase gsonDb = Utils.getAllInfo(getActivity(), netID, password);
                        return gsonDb;
                    }

                    @Override
                    protected void onPostExecute(GsonDatabase db) {
                        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                        dbHelper.updateDatabase(db);
                        swipeLayout.setRefreshing(false);
                    }
                }.execute();
            }
        });
        //initialize task list
        taskRecyclerListItems = new ArrayList<>();
        for(Task task : MainActivity.database.getTasks())
        {
            Calendar day = Calendar.getInstance();
            day.setTimeInMillis(task.getAssignedDate());
            if(day.get(Calendar.DAY_OF_YEAR) == currentDay.get(Calendar.DAY_OF_YEAR))
            {
                view.findViewById(R.id.no_tasks_message).setVisibility(View.INVISIBLE);
                Assignment a = MainActivity.database.getAssignmentById(task.getAssignmentId());
                taskRecyclerListItems.add(new ScheduleListItem(new ScheduleItem(a.getName(),a.getDescription(), task.getColor(), a.getCategory(), a.getCourseID(), MainActivity.database.getCourseById(a.getCourseID()).getShortTitle(), MainActivity.database.getCourseById(a.getCourseID()).getTitle(), new Date(a.getDueDate()), a.getGraded(), a.getPoints(), a.getType(), a.getUrl(), a.getWeight(), a.getId()), AbstractScheduleListItem.ItemType.TASK));
            }
        }
        //add tasks that are already planned.
        taskRecyclerView = (RecyclerView) view.findViewById(R.id.task_RV);
        taskRecyclerView.setHasFixedSize(true);
        taskAdapter = new TaskRVAdapter(taskRecyclerListItems, getActivity());
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
