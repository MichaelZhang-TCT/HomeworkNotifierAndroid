package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
//import android.support.v7.widget.Toolbar;
//import android.text.format.DateFormat;
//import android.view.View;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.schedule.*;


public class MainActivity extends AppCompatActivity {

    ListView l;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Schedule schedule = ScheduleFactory.create(assignments);

        l = (ListView) findViewById(R.id.listView);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();
    }

}
