package edu.byu.dtaylor.homeworknotifier.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.byu.dtaylor.homeworknotifier.MainActivity;
import edu.byu.dtaylor.homeworknotifier.R;
import edu.byu.dtaylor.homeworknotifier.Utils;
import edu.byu.dtaylor.homeworknotifier.database.Assignment;

public class AlarmService {
    private Context context;
    private PendingIntent mAlarmSender;
    public AlarmService(Context context) {
        this.context = context;
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        ArrayList<Assignment> assignments = (ArrayList) MainActivity.database.getAssignmentsByDueDate(cal.getTime());
        if (assignments.size() > 0) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("name", assignments.get(0).getName());
            if (assignments.size() > 1)
            {
                intent.putExtra("others", String.valueOf(assignments.size() - 1));
            }
            else{
                intent.putExtra("dueTime", Utils.stringifyTimeDue(new Date(assignments.get(0).getDueDate())));
            }
            mAlarmSender = PendingIntent.getBroadcast(context, 0, intent, 0);
        }

    }

    public void startAlarm(){
        //Set the alarm to 10 seconds from now
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 3);

        long firstTime = c.getTimeInMillis();
        // Schedule the alarm!
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, mAlarmSender);

    }
}