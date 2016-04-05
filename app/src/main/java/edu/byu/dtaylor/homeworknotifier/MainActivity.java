package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.database.Database;
import edu.byu.dtaylor.homeworknotifier.database.Task;
import edu.byu.dtaylor.homeworknotifier.notifications.AlarmService;
import edu.byu.dtaylor.homeworknotifier.schedule.Schedule;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleFactory;
import edu.byu.dtaylor.homeworknotifier.schedule.ScheduleItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem.ItemType;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AssignmentRVAdapter;
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

    private static final String TAG = "MainActivity";
    private static ImageView settingsButton;
    private RecyclerView assignmentsRecyclerView;
    private RecyclerView taskRecyclerView;
    List<AbstractScheduleListItem> assignmentsRecyclerListItems;
    List<AbstractScheduleListItem> taskRecyclerListItems;
    Date dateBeingViewed = Calendar.getInstance().getTime(); //gets the current time.
    public static Database database;


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
    public static SharedPreferences settings;
    //END CALENDAR STUFF

    protected Fragment loadSchedule() {
        //TODO

        return null;
    }

    public void OnClickSettingsButtonListener() {

        settingsButton = (ImageView)findViewById(R.id.imageButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*MainActivity.settings.edit().remove("netID");
                MainActivity.settings.edit().remove("password");*/
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
        settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(getIntent().getStringExtra("netID") != null && getIntent().getStringExtra("password") != null)
        {
            editor.putString("netID",getIntent().getStringExtra("netID"));
            editor.putString("password",getIntent().getStringExtra("password"));
            editor.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Schedule userSchedule = ScheduleFactory.create(database);
        if (userSchedule == null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("error", "server not initialized");
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            return;
        }
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


        final AssignmentRVAdapter assignmentAdapter = new AssignmentRVAdapter(assignmentsRecyclerListItems, this);
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
                                    ScheduleListItem item = ((ScheduleListItem)assignmentsRecyclerListItems.get(position));
                                    item.plan(dateBeingViewed);
                                    Calendar selectedDay = ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).getSelectedDay();
                                    database.addTask(new Task(item.getAssignmentId(), item.getCourseID(), String.valueOf(item.getDueDate().getTime()), String.valueOf(selectedDay.getTime().getTime()), String.valueOf(item.getColor())), MainActivity.this);
                                    ScheduleListItem newTask =  new ScheduleListItem(
                                            assignmentsRecyclerListItems.get(position),
                                            ItemType.TASK);
                                    ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).taskRecyclerListItems.add(newTask);
                                    //assignmentAdapter.notifyItemRemoved(position);
                                    ((CalendarActivityFragment)CalendarPageAdapter.getCurrentFragment()).getView().findViewById(R.id.no_tasks_message).setVisibility(View.INVISIBLE);
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
        final LinearLayoutManager assignmentsLayoutManager = new LinearLayoutManager(this);
        // scroll to the right day right away
        assignmentsLayoutManager.scrollToPositionWithOffset(getCurrentDayIndex(), 0);

        //assignmentsLayoutManager.scrollToPosition(getCurrentDayIndex());
        assignmentsRecyclerView.setLayoutManager(assignmentsLayoutManager);

        //go-to-today button
        ImageView goToToday = (ImageView) findViewById(R.id.go_to_today_image);
        goToToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // scroll to the right day
                assignmentsLayoutManager.scrollToPositionWithOffset(getCurrentDayIndex(), 0);
                AlarmService alarm = new AlarmService(MainActivity.this);
                alarm.startAlarm();

            }
        });


        //Item decoration
        //SpacesItemDecoration itemDecoration = new SpacesItemDecoration(-400);
        //assignmentsRecyclerView.addItemDecoration(itemDecoration);

        //schedule fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();

    }

    private void setNotification(long showTime){

//                long showAt = System.currentTimeMillis();//immediately
//                long showAt = System.currentTimeMillis()+30000;

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                Notification notification = new Notification(icon,title,showAt);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), new Intent(), 0);
        Notification notification = new Notification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(MainActivity.this)
                    .setTicker("Ticker text")
                    .setContentTitle("Homework Notification")
                    .setContentText("You have a homework assignment due at ...")
                    .setSmallIcon(R.drawable.ic_food_apple_white_18dp)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent).build();
        }

//                notification.defaults |= Notification.DEFAULT_SOUND;
        //use the above default or set custom valuse as below
//                notification.sound = Uri.parse("file:///sdcard/notification/robo_da.mp3");
//                notification.defaults |= Notification.DEFAULT_VIBRATE;
        //use the above default or set custom valuse as below
//                long[] vibrate = {0,200,100,200};
//                notification.vibrate = vibrate;
//                notification.defaults |= Notification.DEFAULT_LIGHTS;
        //use the above default or set custom valuse as below
//                notification.ledARGB = 0xffff0000;//red color
//                notification.ledOnMS = 400;
//                notification.ledOffMS = 500;
//                notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        final int notificationIdentifier = 0; //a unique number set by developer to identify a notification, using this notification can be updated/replaced
        notificationManager.notify(notificationIdentifier, notification);
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

                //Snackbar.make(findViewById(R.id.calendar_view_pager),"page scrolled",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                focusPage = position;
                //Snackbar.make(findViewById(R.id.calendar_view_pager),"page selected",Snackbar.LENGTH_SHORT).show();
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
                //Snackbar.make(findViewById(R.id.calendar_view_pager),"state changed",Snackbar.LENGTH_SHORT).show();
            }
        });
        dayPage.setCurrentItem(1, false);
        updateTitle();
        //END CALENDAR STUFF
    }
    //CALENDAR STUFF
    private void updateTitle() {
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
                strdate = Utils.stringifyDate(currentDay.getTime(), false);
            }
        }
        setTitle(strdate);
        TextView mainPageTitle = (TextView) findViewById(R.id.page_title);
        if(mainPageTitle != null)
            mainPageTitle.setText(strdate);
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

    public int getCurrentDayIndex() {
        int result = assignmentsRecyclerListItems.size() - 1;
        double minDifference = Double.MAX_VALUE;
        for (AbstractScheduleListItem item : assignmentsRecyclerListItems)
        {
            if (item.getItemType() == ItemType.HEADER)
            {
                ScheduleListHeader header = (ScheduleListHeader) item;
                double difference = (header.getDate().getTime()) - currentDay.getTime().getTime();
                if (difference > 0) {
                    if (difference < minDifference)
                    {
                        minDifference = difference;
                        result = assignmentsRecyclerListItems.indexOf(item);
                    }
                }
            }
        }
        return result;
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
