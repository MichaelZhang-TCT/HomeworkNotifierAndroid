package edu.byu.dtaylor.homeworknotifier;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private static ImageButton settingsButton;

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

                Log.d("debug","settings clicked");
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
        Schedule schedule = ScheduleFactory.create(assignments);

        l = (ListView) findViewById(R.id.listView);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.scheduleFragmentContainer,new ScheduleFragment());
//        fragmentTransaction.commit();
    }

}
