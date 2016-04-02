package edu.byu.dtaylor.homeworknotifier;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.AbstractScheduleListItem;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListHeader;
import edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources.ScheduleListItem;

/**
 * Created by dtaylor on 4/1/2016.
 */
class ScheduleRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ScheduleRVAdapter";
    List<AbstractScheduleListItem> itemsShown;
    Context context;
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
        AbstractScheduleListItem.ItemType itemType = AbstractScheduleListItem.ItemType.values()[viewType];
        if (itemType == AbstractScheduleListItem.ItemType.HEADER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_header, viewGroup, false);//$$$
            return new ScheduleHeaderViewHolder(v);
        } else if (itemType == AbstractScheduleListItem.ItemType.ASSIGNMENT){
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_item, viewGroup, false);//$$$
            return new ScheduleItemViewHolder(v);
        } else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_list_item, viewGroup, false);//$$$
            return new ScheduleItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AbstractScheduleListItem.ItemType type = AbstractScheduleListItem.ItemType.values()[getItemViewType(position)];
        if (type == AbstractScheduleListItem.ItemType.HEADER) {
            ScheduleListHeader header = (ScheduleListHeader) itemsShown.get(position);
            ScheduleHeaderViewHolder holder = (ScheduleHeaderViewHolder) viewHolder;
            holder.date.setText(Utils.stringifyDate(header.getDate(), false));
            Log.d(TAG, Utils.stringifyDate(header.getDate(), false));
        } else if (type == AbstractScheduleListItem.ItemType.ASSIGNMENT){
            ScheduleListItem item = (ScheduleListItem) itemsShown.get(position);
            ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
            holder.itemName.setText(item.getName());
            holder.item_cv.setBackgroundColor(item.getColor());
            holder.itemTypeImage.setAlpha(30);
            switch ((new Random()).nextInt() % 3){
                case 0:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_book_minus_white_24dp);
                    break;
                case 1:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_flask_white_24dp);
                    break;
                case 2:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_pen_white_24dp);
                    break;
                default:
                    break;
            }


            // your logic here
        }
        else {
            ScheduleListItem item = (ScheduleListItem) itemsShown.get(position);
            ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
            holder.itemName.setText(item.getName());
            holder.item_cv.setBackgroundColor(item.getColor());
            holder.itemTypeImage.setAlpha(30);

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
        ImageView itemTypeImage;
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
            itemTypeImage = (ImageView) itemView.findViewById(R.id.assignment_type_image);
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
