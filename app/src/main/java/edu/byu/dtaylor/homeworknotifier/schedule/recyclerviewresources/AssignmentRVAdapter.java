package edu.byu.dtaylor.homeworknotifier.schedule.recyclerviewresources;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.AssignmentDialogFragment;
import edu.byu.dtaylor.homeworknotifier.MainActivity;
import edu.byu.dtaylor.homeworknotifier.R;
import edu.byu.dtaylor.homeworknotifier.Utils;

/**
 * Created by dtaylor on 4/1/2016.
 */
public class AssignmentRVAdapter extends ScheduleRVAdapter {

    private static final String TAG = "AssignmentRVAdapter";

    public AssignmentRVAdapter(List<AbstractScheduleListItem> items, Context context) {
        super(items, context);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        currentDayIndex = ((MainActivity) context).getCurrentDayIndex();
        AbstractScheduleListItem.ItemType type = AbstractScheduleListItem.ItemType.values()[getItemViewType(position)];
        if (type == AbstractScheduleListItem.ItemType.HEADER) {
            ScheduleListHeader header = (ScheduleListHeader) itemsShown.get(position);
            ScheduleHeaderViewHolder holder = (ScheduleHeaderViewHolder) viewHolder;
            if (position == currentDayIndex) //render differently if it is showing the current day.
            {
                Calendar headerDate = Calendar.getInstance();
                headerDate.setTime(new Date(header.getDate().getTime() * 1000));
                if (headerDate.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                    holder.date.setText("Today"); //only display "today" if it's literally today.
                } else holder.date.setText(Utils.stringifyDate(header.getDate(), true, false));
                //holder.date.setTypeface(null, Typeface.BOLD);
                //holder.date.setTextColor(Color.parseColor("#A7A7A7"));

                holder.date.setTextColor((ContextCompat.getColor(context, R.color.colorPrimaryMediumLight)));

                holder.line.requestLayout();
                //holder.line.getLayoutParams().height = Utils.pxFromDp(2, context);
                holder.line.setBackgroundColor((ContextCompat.getColor(context, R.color.colorPrimaryMediumLight)));

            } else {
                holder.date.setText(Utils.stringifyDate(header.getDate(), true, false));
                holder.date.setTypeface(null, Typeface.NORMAL);
                holder.date.setTextColor((ContextCompat.getColor(context, R.color.lightGray)));
                holder.line.requestLayout();
                //holder.line.getLayoutParams().height = Utils.pxFromDp(1, context);

                holder.line.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
            }
            Log.d(TAG, Utils.stringifyDate(header.getDate(), true, false));
        } else {
            ScheduleListItem item = (ScheduleListItem) itemsShown.get(position);
            ScheduleItemViewHolder holder = (ScheduleItemViewHolder) viewHolder;
            holder.itemName.setText(item.getName());
            holder.item_cv.setBackgroundColor(item.getColor());
            holder.itemTypeImage.setImageAlpha(60);
            holder.description = item.getDescription();
            holder.itemDueTime.setText(Utils.stringifyTimeDue(item.getDueDate()));
            holder.itemCourse.setText(item.getShortTitle());
            switch (item.getType()) {
                case HOMEWORK:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_book_minus_white_24dp);
                    break;
                case TEST:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_school_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_sale_white_24dp);
                    break;
                case QUIZ:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_pen_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_sale_white_24dp);
                    break;
                case READING:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_library_white_24dp);
                    break;
                case CUSTOM:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_flask_white_24dp);
                    break;
                case OTHER:
                    holder.itemTypeImage.setImageResource(R.drawable.ic_certificate_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_food_apple_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_rename_box_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_script_white_24dp);

                    holder.itemTypeImage.setImageResource(R.drawable.ic_scale_balance_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_presentation_white_24dp);
                    holder.itemTypeImage.setImageResource(R.drawable.ic_rename_box_white_24dp);
                default:
                    break;
            }
        }
/*
            itemViewHolder.itemName.setText(itemsShown.get(i).getName());
            itemViewHolder.itemPrice.setText("$"+String.valueOf(itemsShown.get(i).getCost()));
            //itemViewHolder.itemLocation.setText(itemsShown.get(i).getStore());
            itemViewHolder.itemImage.setBackgroundResource(itemsShown.get(i).getImageId());
            itemViewHolder.currentItem = i;
            itemViewHolder.itemId = itemsShown.get(i).getId();*/


    }


    /*************INNER CLASSES***********/

    public class AssignmentViewHolder extends ScheduleRVAdapter.ScheduleItemViewHolder {

        public AssignmentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    DialogFragment dialog = new AssignmentDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", itemName.getText().toString());
                    bundle.putString("description", description);
                    bundle.putString("dueDate", itemDueTime.getText().toString());
                    bundle.putString("points", pointsPossible);
                    bundle.putString("course", itemCourse.getText().toString());
                    dialog.setArguments(bundle);
                    dialog.show(((MainActivity) context).getSupportFragmentManager(), "AssignmentDialogFragment");
                    return true;
                }
            });
        }




    }
}
