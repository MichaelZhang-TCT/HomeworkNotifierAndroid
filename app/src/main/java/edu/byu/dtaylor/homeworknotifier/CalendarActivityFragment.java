package edu.byu.dtaylor.homeworknotifier;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.List;

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
        //add tasks that are already planned.
        taskRecyclerView = (RecyclerView) view.findViewById(R.id.task_RV);
        taskRecyclerView.setHasFixedSize(true);
        taskAdapter = new ScheduleRVAdapter(taskRecyclerListItems);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(taskRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {

                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {

                                Snackbar.make(recyclerView,"scroll state changed",Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {

                                Snackbar.make(recyclerView,"scroll state changed",Snackbar.LENGTH_SHORT).show();
                            }
                        });

        taskRecyclerView.addOnItemTouchListener(swipeTouchListener);
        taskRecyclerView.setAdapter(taskAdapter);
        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(getActivity());
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Snackbar.make(recyclerView,"scroll state changed",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Snackbar.make(recyclerView,"scrolled",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUI(Calendar calendar) {

        View v = getView();
        if (v == null)
            return;
        //setNewDataSource calcurate new List<String> date
        CalendarPageAdapter calendarViewAdapter = (CalendarPageAdapter)((ViewPager) ((MainActivity)parentActivity).findViewById(R.id.calendar_view_pager)).getAdapter();
        calendarViewAdapter.setNewDataSource(calendar);
        calendarViewAdapter.notifyDataSetChanged();
    }
}
