package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity implements TaskListener, NavigationView.OnNavigationItemSelectedListener {

    private static ImageButton settingsButton;
    private RecyclerView assignmentsRecyclerView;
    private RecyclerView taskRecyclerView;
    List<AbstractScheduleListItem> assignmentsRecyclerListItems;
    List<AbstractScheduleListItem> taskRecyclerListItems;
    Date dateBeingViewed = Calendar.getInstance().getTime(); //gets the current time.
    GsonDatabase database;

    //This will be replaced by what we pull from the server - ie real data
    private String assignments = "[{\"Description\":\"GsonAssignment One\",\"Date\":\"2016-03-05\"}," +
            "{\"Description\":\"GsonAssignment Two\",\"Date\":\"2016-03-06\"}," +
            "{\"Description\":\"GsonAssignment Three\",\"Date\":\"2016-03-06\"}]";

    private int offset = 0;
    private Date startDate = new Date();
    private Fragment list;

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

            GetDatabaseTask task = new GetDatabaseTask(this);
            task.execute(new String[]{"daviddt2", "davidpaseo3"});
            OnClickSettingsButtonListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Schedule userSchedule = ScheduleFactory.create(assignments);

        //initialize task list
        taskRecyclerListItems = new ArrayList<>();
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
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_RV);

        assignmentsRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setHasFixedSize(true);


        final ScheduleRVAdapter assignmentAdapter = new ScheduleRVAdapter(assignmentsRecyclerListItems);
        final ScheduleRVAdapter taskAdapter = new ScheduleRVAdapter(taskRecyclerListItems);
        assignmentsRecyclerView.setAdapter(assignmentAdapter);
        taskRecyclerView.setAdapter(taskAdapter);

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
                                    taskRecyclerListItems.add(newTask);
                                    assignmentAdapter.notifyItemRemoved(position);
                                    taskAdapter.notifyItemInserted(taskRecyclerListItems.size() - 1);
                                }
                                assignmentAdapter.notifyDataSetChanged();
                                taskAdapter.notifyDataSetChanged();
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

        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);

        //Item decoration
        //SpacesItemDecoration itemDecoration = new SpacesItemDecoration(-400);
        //assignmentsRecyclerView.addItemDecoration(itemDecoration);

        //schedule fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();

    }

    @Override
    public void onTaskCompleted(GsonDatabase database) {
        ((TextView)findViewById(R.id.page_title)).setText(database.getUser().getId());
        this.database = database;
    }

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

    class ScheduleRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<AbstractScheduleListItem> itemsShown;
        ScheduleRVAdapter(List<AbstractScheduleListItem> items){
            this.itemsShown = items;
        }
        @Override
        public int getItemViewType(int position) {
            return itemsShown.get(position).getItemType().ordinal();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            ItemType itemType = ItemType.values()[viewType];
            if (itemType == ItemType.HEADER) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_header, viewGroup, false);//$$$
                return new ScheduleHeaderViewHolder(v);
            } else if (itemType == ItemType.ASSIGNMENT){
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_item, viewGroup, false);//$$$
                return new ScheduleItemViewHolder(v);
            } else
            {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_item, viewGroup, false);//$$$
                return new ScheduleItemViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ItemType type = ItemType.values()[getItemViewType(position)];
            if (type == ItemType.HEADER) {
                ScheduleListHeader header = (ScheduleListHeader) assignmentsRecyclerListItems.get(position);
                ScheduleHeaderViewHolder holder = (ScheduleHeaderViewHolder) viewHolder;
                holder.date.setText(header.getDate().toString());
            } else if (type == ItemType.ASSIGNMENT){
                 ScheduleListItem item = (ScheduleListItem) assignmentsRecyclerListItems.get(position);
                ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
                holder.itemName.setText(item.getName());
                // your logic here
            }
            else {
                ScheduleListItem item = (ScheduleListItem) taskRecyclerListItems.get(position);
                ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
                holder.itemName.setText(item.getName());
            }
/*
            itemViewHolder.itemName.setText(itemsShown.get(i).getName());
            itemViewHolder.itemPrice.setText("$"+String.valueOf(itemsShown.get(i).getCost()));
            //itemViewHolder.itemLocation.setText(itemsShown.get(i).getStore());
            itemViewHolder.itemImage.setBackgroundResource(itemsShown.get(i).getImageId());
            itemViewHolder.currentItem = i;
            itemViewHolder.itemId = itemsShown.get(i).getId();*/
        }

        @Override
        public int getItemCount() {
            return itemsShown.size();
        }

        public class ScheduleItemViewHolder extends RecyclerView.ViewHolder {

            CardView item_cv;
            TextView itemName;
            //TextView itemLocation;
            public int currentItem;
            public String itemId;

            ScheduleItemViewHolder(final View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add activity if you want.
                        /*Intent intent = new Intent(EventInfoActivity.this, ItemInfoActivity.class);
                        intent.putExtra("itemId", itemId);
                        EventInfoActivity.this.startActivity(intent);*/
                    }
                });

                item_cv = (CardView)itemView.findViewById(R.id.cv);
                itemName = (TextView)itemView.findViewById(R.id.item_name);
            }
        }

        public class ScheduleHeaderViewHolder extends RecyclerView.ViewHolder {

            TextView date;
            View line;

            ScheduleHeaderViewHolder(final View itemView) {
                super(itemView);

                date = (TextView)itemView.findViewById(R.id.header_date_textview);
                line = itemView.findViewById(R.id.line);
            }
        }
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
