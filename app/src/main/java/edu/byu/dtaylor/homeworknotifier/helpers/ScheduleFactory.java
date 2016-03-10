package edu.byu.dtaylor.homeworknotifier.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tanner on 3/9/2016.
 */
public class ScheduleFactory {

    public static Schedule create(String jsonData) {
        Schedule s = new Schedule();
        //TODO
        try {
            JSONArray jsonarray = new JSONArray(jsonData);
            for(int i=0; i<jsonarray.length(); i++){
                JSONObject obj = jsonarray.getJSONObject(i);

                String name = obj.getString("Name");
                String date = obj.getString("Date");

            }
        }
        catch(JSONException e) {

        }
        return s;
    }
}
