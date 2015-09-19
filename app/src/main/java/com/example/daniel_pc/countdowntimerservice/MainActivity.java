package com.example.daniel_pc.countdowntimerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //all properties here
    TextView countdownTimer;
    EditText hours;
    EditText minutes;
    EditText seconds;

    //seconds sum
    long milisSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownTimer = (TextView)findViewById(R.id.counterTextView);
        hours = (EditText)findViewById(R.id.hoursText);
        minutes = (EditText)findViewById(R.id.minutesText);
        seconds = (EditText)findViewById(R.id.secondsText);

    }

    //make some button trigger this method.

    public void startService(View view) {

        //collect all data from text fields:

        //HOURS
        //get string first
        String hourStr = hours.getText().toString();

        //parse to long
        Long hourLong = Long.parseLong(hourStr);

        //to MIliseconds
        long hourMili = hourLong * 3600000 ;


        //MINUTES
        //get String first
        String minuteStr = minutes.getText().toString();

        //parse to long
        Long minuteLong = Long.parseLong(minuteStr);

        //to Miliseconds
        long minuteMili = minuteLong * 60000;


        //SECONDS
        //get String first
        String secondStr = seconds.getText().toString();

        //parse to long
        Long secondLong = Long.parseLong(secondStr);

        //to MIliseconds
        long secondMili = secondLong * 1000;


        //add to the sum

        milisSum = secondMili + minuteMili + hourMili;


        //store sum
        long miliseconds = milisSum;

        //new intent to start the service
        Intent intent = new Intent(this, CountDownTimerService.class);

        //put extra
        intent.putExtra("Sum_milliseconds", miliseconds);

        //send the intent, start service
        startService(intent);

        //Registers receiver for later?
        //first param is perhaps a name of this broadcast
        //second is a intent filer which I know nothing about
        registerReceiver(uiUpdated, new IntentFilter("COUNTDOWN_UPDATED"));
        //Log.d("SERVICE", "STARTED!");

    }//startService

    public void stopService(View view){

        //make this slightly more sophisticated
        //e.g. pausing might be too difficult but restarting from the start is ok I guess
        stopService(new Intent(this, CountDownTimerService.class));
    }

    //New boradcasrReceiver with the same name as he one registered earlier
    private BroadcastReceiver uiUpdated = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //This is the part where I get the timer value from the service and I update it every second,
            //because I send the data from the service every second.
            countdownTimer.setText(intent.getExtras().getString("countdown"));

        }
    };

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
