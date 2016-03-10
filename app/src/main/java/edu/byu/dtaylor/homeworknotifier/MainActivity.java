package edu.byu.dtaylor.homeworknotifier;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.dtaylor.homeworknotifier.helpers.*;


public class MainActivity extends AppCompatActivity {

    //This will be replaced by what we pull from the server - ie real data
    private String assignments = "[{\"Name\":\"Assignment One\",\"Date\":\"2016-03-05\"}," +
            "{\"Name\":\"Assignment Two\",\"Date\":\"2016-03-06\"}]";

    /*
    {
        date:...,
        assignments:[name1,name2,name2]
    }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Schedule schedule = ScheduleFactory.create(assignments);
        //TODO
    }

}
