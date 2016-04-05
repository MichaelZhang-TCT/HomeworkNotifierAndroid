package edu.byu.dtaylor.homeworknotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import edu.byu.dtaylor.homeworknotifier.database.DatabaseHelper;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

public class SplashScreenActivity extends AppCompatActivity implements CustomTaskListener {

    private String netID;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(MainActivity.database == null)
        {
            netID = getIntent().getStringExtra("netID");
            password = getIntent().getStringExtra("password");
            new CustomAsyncTask(this).execute(netID,password);
        }
        else
            Log.e("SplashScreenActivity","Something wrong with database");
    }

    @Override
    public void onPostExecute(Object object) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("netID",netID);
        intent.putExtra("password",password);
        this.startActivity(intent);
        finish();
    }

    @Override
    public Object doInBackground(Object[] params) {
        String netID = getIntent().getStringExtra("netID");
        String password = getIntent().getStringExtra("password");

        GsonDatabase gsonDb = Utils.getAllInfo(this, netID, password);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.setDBfromGson(gsonDb);

        return gsonDb;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Object[] values) {

    }
}
