package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;
import edu.byu.dtaylor.homeworknotifier.schedule.Schedule;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleFactory;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem.ItemType;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListHeader;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListItem;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.widget.Toolbar;
//import android.text.format.DateFormat;
//import android.view.View;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static ImageButton settingsButton;
    private RecyclerView assignmentsRecyclerView;
    private RecyclerView taskRecyclerView;
    List<AbstractScheduleListItem> assignmentsRecyclerListItems;
    List<AbstractScheduleListItem> taskRecyclerListItems;
    Date dateBeingViewed = Calendar.getInstance().getTime(); //gets the current time.
    public static GsonDatabase database;

    //This will be replaced by what we pull from the server - ie real data
    /*private String assignments = "[{\"Description\":\"GsonAssignment One\",\"Date\":\"2016-03-05\"}," +
            "{\"Description\":\"GsonAssignment Two\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}]";*/

    private String assignments = "[{\"Description\":\"GsonAssignment One\",\"Date\":\"2016-03-05\"}," +
            "{\"Description\":\"GsonAssignment Two\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}]";


    private int offset = 0;
    private Date startDate = new Date();
    private Fragment list;

    //CALENDAR STUFF
    private static final int PAGE_CENTER = 1;
    private int focusPage;
    private Calendar currentDay;
    private Calendar prevDay;
    private Calendar nextDay;
    private CalendarPageAdapter calendarPageAdapter;
    private ViewPager dayPage;
    //END CALENDAR STUFF

    protected Fragment loadSchedule() {
        //TODO

        return null;
    }

    public void OnClickSettingsButtonListener() {

        settingsButton = (ImageButton)findViewById(R.id.imageButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("debug", "settings clicked");
                Intent intent = new Intent("edu.byu.dtaylor.homeworknotifier.SettingsActivity");
                startActivity(intent);
            }
        });
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            OnClickSettingsButtonListener();

        //CALENDAR STUFF
        initializeCalendar();
        //END CALENDAR STUFF

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Schedule userSchedule = ScheduleFactory.create(database);

        //add tasks that are already planned.

        //initialize allAssignments List
        assignmentsRecyclerListItems = new ArrayList<>();
        for (Date date : userSchedule.getDates()) {
            ScheduleListHeader header = new ScheduleListHeader();
            header.setDate(date);
            assignmentsRecyclerListItems.add(header);
            for (ScheduleItem assignment : userSchedule.getItemsByDate(date)) {
                ScheduleListItem item = new ScheduleListItem(assignment, ItemType.ASSIGNMENT);
                assignmentsRecyclerListItems.add(item);
            }
        }

        assignmentsRecyclerView = (RecyclerView) findViewById(R.id.assignment_RV);

        assignmentsRecyclerView.setHasFixedSize(true);


        final ScheduleRVAdapter assignmentAdapter = new ScheduleRVAdapter(assignmentsRecyclerListItems);
        assignmentsRecyclerView.setAdapter(assignmentAdapter);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(assignmentsRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {

                            @Override
                            public boolean canSwipeLeft(int position) {
                                if (assignmentsRecyclerListItems.get(position).getItemType() == ItemType.HEADER)
                                {
                                    return false;
                                }else return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return false;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    assignmentsRecyclerListItems.get(position).plan(dateBeingViewed);
                                    ScheduleListItem newTask =  new ScheduleListItem(
                                            assignmentsRecyclerListItems.get(position),
                                            ItemType.TASK);
                                    ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).taskRecyclerListItems.add(newTask);
                                    assignmentAdapter.notifyItemRemoved(position);
                                    ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).taskAdapter.notifyItemInserted(((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).taskRecyclerListItems.size() - 1);
                                }
                                assignmentAdapter.notifyDataSetChanged();
                                ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).taskAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                return;
                            }
                        });

        assignmentsRecyclerView.addOnItemTouchListener(swipeTouchListener);

        // use a linear layout manager
        RecyclerView.LayoutManager assignmentsLayoutManager = new LinearLayoutManager(this);
        assignmentsRecyclerView.setLayoutManager(assignmentsLayoutManager);


        //Item decoration
        //SpacesItemDecoration itemDecoration = new SpacesItemDecoration(-400);
        //assignmentsRecyclerView.addItemDecoration(itemDecoration);

        //schedule fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();

    }

    private void initializeCalendar() {
        currentDay = Calendar.getInstance();
        nextDay = Calendar.getInstance();

        prevDay = Calendar.getInstance();
        prevDay.setTime(currentDay.getTime());
        prevDay.add(Calendar.DAY_OF_MONTH, -1);

        nextDay = Calendar.getInstance();
        nextDay.setTime(currentDay.getTime());


        CalendarActivityFragment[] fragList = new CalendarActivityFragment[3];
        fragList[0] = new CalendarActivityFragment(this, prevDay);
        fragList[1] = new CalendarActivityFragment(this, currentDay);
        fragList[2] = new CalendarActivityFragment(this, nextDay);

        dayPage = (ViewPager) findViewById(R.id.calendar_view_pager);
        calendarPageAdapter = new CalendarPageAdapter(getSupportFragmentManager(), fragList);
        dayPage.setAdapter(calendarPageAdapter);
        dayPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
        dayPage.setCurrentItem(1, false);
        updateTitle();
        //END CALENDAR STUFF
    }
    //CALENDAR STUFF
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
        setTitle(strdate);
    }
    //END CALENDAR STUFF

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.drawable.ic_menu_camera) {
            // Handle the camera action
        } else if (id == R.drawable.ic_menu_gallery) {

        } else if (id == R.drawable.ic_menu_slideshow) {

        } else if (id == R.drawable.ic_menu_manage) {

        } else if (id == R.drawable.ic_menu_share) {

        } else if (id == R.drawable.ic_menu_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            outRect.bottom = space;
        }
    }
}
