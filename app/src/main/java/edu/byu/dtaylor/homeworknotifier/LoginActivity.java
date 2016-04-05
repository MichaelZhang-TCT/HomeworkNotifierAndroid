package edu.byu.dtaylor.homeworknotifier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.byu.dtaylor.homeworknotifier.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        OnClickButtonListener();

        if (getIntent().getStringExtra("error") != null)
        {
            Log.e(TAG, "We started with an error!");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sorry, the server isn't running right now. Try again in a few minutes");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog error = builder.create();
            error.show();
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        //dbHelper.onUpgrade(dbHelper.getWritableDatabase(), -1, -1);
        try {
            MainActivity.database = dbHelper.getDatabaseFromSql();
        }
        catch(Exception e)
        {
            dbHelper.onUpgrade(dbHelper.getWritableDatabase(), -1, -1);
            Log.e("LoginActivity","Couldn't load database",e);
        }
        finally{
            dbHelper.close();
        }

        if(MainActivity.database != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }

    public void OnClickButtonListener() {
        final EditText netID = ((EditText)findViewById(R.id.netID));
        final EditText password = ((EditText)findViewById(R.id.password));
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SplashScreenActivity.class);
                if(netID.getText().toString().equals("")||password.getText().toString().equals(""))
                {
                    intent.putExtra("netID","daviddt2");
                    intent.putExtra("password","davidpaseo3");
                }
                else
                {
                    intent.putExtra("netID", netID.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
