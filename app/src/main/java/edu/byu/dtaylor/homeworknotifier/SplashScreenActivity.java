package edu.byu.dtaylor.homeworknotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

public class SplashScreenActivity extends AppCompatActivity implements CustomTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new CustomAsyncTask(this).execute("daviddt2","davidpaseo3");
    }

    @Override
    public void onPostExecute(Object object) {
        MainActivity.database = (GsonDatabase)object;
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    public Object doInBackground(Object[] params) {
        return Utils.getAllInfo(this, "daviddt2", "davidpaseo3");
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Object[] values) {

    }
}
