package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.support.v7.widget.Toolbar;
//import android.text.format.DateFormat;
//import android.view.View;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.schedule.*;


public class MainActivity extends AppCompatActivity {

    private static ImageButton settingsButton;
    private RecyclerView recyclerView;
    List<AbstractScheduleListItem> recyclerListItems;


    //This will be replaced by what we pull from the server - ie real data
    private String assignments = "[{\"Description\":\"Assignment One\",\"Date\":\"2016-03-05\"}," +
            "{\"Description\":\"Assignment Two\",\"Date\":\"2016-03-06\"}]";

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

        OnClickSettingsButtonListener();
        Schedule userSchedule = ScheduleFactory.create(assignments);

// ...
        recyclerListItems = new ArrayList<>();
        for (Date date : userSchedule.getDates()) {
            ScheduleListHeader header = new ScheduleListHeader();
            header.setDate(date);
            recyclerListItems.add(header);
            for (ScheduleItem assignment : userSchedule.getItemsByDate(date)) {
                ScheduleListItem item = new ScheduleListItem();
                item.setScheduleItem(assignment);
                recyclerListItems.add(item);
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.assignment_RV);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //schedule fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();
        initializeAdapter();
    }



    private void initializeAdapter(){
        ScheduleRVAdapter adapter = new ScheduleRVAdapter(recyclerListItems);
        recyclerView.setAdapter(adapter);
    }
    class ScheduleRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<AbstractScheduleListItem> itemsShown;

        ScheduleRVAdapter(List<AbstractScheduleListItem> items){
            this.itemsShown = items;
        }
        @Override
        public int getItemViewType(int position) {
            return itemsShown.get(position).getType();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            if (viewType == AbstractScheduleListItem.TYPE_HEADER) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_header, viewGroup, false);//$$$
                return new ScheduleHeaderViewHolder(v);
            } else {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_item, viewGroup, false);//$$$
                return new ScheduleItemViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            int type = getItemViewType(position);
            if (type == AbstractScheduleListItem.TYPE_HEADER) {
                ScheduleListHeader header = (ScheduleListHeader) recyclerListItems.get(position);
                ScheduleHeaderViewHolder holder = (ScheduleHeaderViewHolder) viewHolder;
                holder.date.setText(header.getDate().toString());
            } else {
                ScheduleListItem item = (ScheduleListItem) recyclerListItems.get(position);
                ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
                // your logic here
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

            /*CardView item_cv;
            TextView itemName;
            TextView itemPrice;
            //TextView itemLocation;
            RelativeLayout itemImage;
            public int currentItem;
            public String itemId;*/

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

                /*
                item_cv = (CardView)itemView.findViewById(R.id.cv);
                itemName = (TextView)itemView.findViewById(R.id.item_name);
                itemPrice = (TextView)itemView.findViewById(R.id.item_cost);
                itemImage = (RelativeLayout)itemView.findViewById(R.id.item_image);*/
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
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;

        }
    }
}
