package edu.byu.dtaylor.homeworknotifier;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final int PAGE_CENTER = 1;
    private int focusPage;
    private Calendar currentDay;
    private Calendar prevDay;
    private Calendar nextDay;
    private CalendarPageAdapter calendarPageAdapter;
    private ViewPager dayPage;

    public CalendarActivity()
    {
        currentDay = Calendar.getInstance();
        nextDay = Calendar.getInstance();

        prevDay = Calendar.getInstance();
        prevDay.setTime(currentDay.getTime());
        prevDay.add(Calendar.DAY_OF_MONTH, -1);

        nextDay = Calendar.getInstance();
        nextDay.setTime(currentDay.getTime());
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initializeCalendar();
    }

    private void initializeCalendar() {
        //CALENDAR STUFF
        CalendarActivityFragment[] fragList = new CalendarActivityFragment[3];
        fragList[0] = new CalendarActivityFragment(this, prevDay);
        fragList[1] = new CalendarActivityFragment(this, currentDay);
        fragList[2] = new CalendarActivityFragment(this, nextDay);

        dayPage = (ViewPager) findViewById(R.id.calendar_view_pager);
        calendarPageAdapter = new CalendarPageAdapter(getSupportFragmentManager(), fragList);
        dayPage.setAdapter(calendarPageAdapter);
        dayPage.addOnPageChangeListener(this);
        dayPage.setCurrentItem(1, false);
        updateTitle();
        //END CALENDAR STUFF
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        focusPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        changeCurrentDate(state);
    }

    private void changeCurrentDate(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {

            if (focusPage < PAGE_CENTER) {
                currentDay.add(Calendar.DAY_OF_MONTH, -1);
            } else if (focusPage > PAGE_CENTER) {
                currentDay.add(Calendar.DAY_OF_MONTH, 1);
            }
            calendarPageAdapter.setCalendar(currentDay);
            dayPage.setCurrentItem(1, false);
            updateTitle();
        }
    }

    private void updateTitle() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strdate = "";
        if (currentDay != null) {
            int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            int currentday = currentDay.get(Calendar.DAY_OF_YEAR);
            if(currentday == today)
            {
                strdate = "Today";
            }
            else if(currentday == today +1)
            {
                strdate = "Tomorrow";
            }
            else if(currentday == today -1)
            {
                strdate = "Yesterday";
            }
            else
            {
                strdate = sdf.format(currentDay.getTime());
            }
        }
        this.setTitle(strdate);
    }


}
