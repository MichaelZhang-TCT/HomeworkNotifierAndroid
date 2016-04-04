package edu.byu.dtaylor.homeworknotifier.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.MainActivity;
import edu.byu.dtaylor.homeworknotifier.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//                Notification notification = new Notification(icon,title,showAt);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), new Intent(context, MainActivity.class), 0);
        Notification notification = new Notification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setTicker("You have something due soon...")
                    .setContentTitle("Homework Notificatitionizer")
                    .setContentText(generateMessage(intent))
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

    private String generateMessage(Intent intent){
        String result = intent.getStringExtra("name");
        if (intent.hasExtra("others"))
        {
            result += "and " + intent.getStringExtra("others") + " other assignments are due tomorrow.";
        }
        else{
            result += " is due tomorrow at " + intent.getStringExtra("dueTime");
        }



                return result;

    }
}