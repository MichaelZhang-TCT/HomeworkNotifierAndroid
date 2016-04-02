package edu.byu.dtaylor.homeworknotifier;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;

public class CalendarPageAdapter extends FragmentPagerAdapter
{
    static CalendarActivityFragment[] fragList;
    CalendarActivityFragment temp;
    int fragNumber = 3;
    private Calendar newDataSource;

    public CalendarPageAdapter(FragmentManager fm, CalendarActivityFragment[] fragList) {
        super(fm);
        this.fragList = fragList;

    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }
    @Override
    public Fragment getItem(int position) {
        return fragList[position];
    }

    @Override
    public int getCount() {
        return fragNumber;
    }
    public void setCalendar(Calendar currentDay) {

        Calendar prevDay = Calendar.getInstance();
        prevDay.setTime(currentDay.getTime());
        prevDay.add(Calendar.DAY_OF_MONTH, -1);

        Calendar nextDay = Calendar.getInstance();
        nextDay.setTime(currentDay.getTime());
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        //update all 3 fragments
        fragList[0].updateUI(prevDay);
        fragList[1].updateUI(currentDay);
        fragList[2].updateUI(nextDay);
    }

    public void setNewDataSource(Calendar newDataSource) {
        this.newDataSource = newDataSource;
    }

    public static Fragment getCurrentFragment()
    {
        return fragList[1];
    }
}
