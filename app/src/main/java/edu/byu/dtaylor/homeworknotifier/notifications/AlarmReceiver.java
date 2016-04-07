package edu.byu.dtaylor.homeworknotifier.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.MainActivity;
import edu.byu.dtaylor.homeworknotifier.R;
import edu.byu.dtaylor.homeworknotifier.Utils;
import edu.byu.dtaylor.homeworknotifier.database.Assignment;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        ArrayList<Assignment> assignments = (ArrayList) MainActivity.database.getAssignmentsByDueDate(cal.getTime());
        if (assignments.size() > 0) {

            String assignmentName = assignments.get(0).getName();
            int others = 0;
            String dueTime = "";

            if (assignments.size() > 1)
            {
                others = assignments.size() - 1;
            }
            else{
                dueTime = Utils.stringifyTimeDue(new Date(assignments.get(0).getDueDate()));
            }
            sendNotification(context,assignmentName,others,dueTime);
        }
    }

    private void sendNotification(Context context, String assignmentName, int others, String dueTime)
    {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent newIntent = new Intent(context, MainActivity.class);
        // System.currentTimeMillis() gives a unique ID for the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), newIntent, 0);
        Notification notification = new Notification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(context)
                    .setTicker("Approaching due date...")
                    .setContentTitle("Homework Notifier")
                    .setContentText(generateMessage(assignmentName,others,dueTime))
                    .setSmallIcon(R.mipmap.skoold_logo_circle)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            notification = new Notification.BigTextStyle(builder)
                    .bigText(generateMessage(assignmentName,others,dueTime)).build();
        }

        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.sound = Uri.parse("file:///sdcard/notification/robo_da.mp3");
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0,200,100,200};
        notification.vibrate = vibrate;
//        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = 0xff0000ff; //blue color
        notification.ledOnMS = 400;
        notification.ledOffMS = 2000;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        final int notificationId = 101; //a unique number set to identify a notification, using this notification can be updated/replaced
        notificationManager.notify(notificationId, notification);
    }

    private String generateMessage(String name, int others, String dueTime){
        String result = name;
        if (others > 0)
        {
            result += "and " + others + " other assignments are due tomorrow.";
        }
        else{
            result += " is due tomorrow at " + dueTime;
        }
        return result;
    }
}