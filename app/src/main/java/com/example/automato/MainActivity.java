package com.example.automato;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static List<Plant> plants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer ();
        TimerTask task = new TimerTask () {
            @Override
            public void run () {
                for(Plant plant: plants)
                    if(!plant.checkOnPlant()){
                        List<String> messages = plant.getDetailedMessage();
                        for(String message: messages)
                            if(message != null)
                                notifyUser(message);
                    }
            }
        };

        //check on plants every six hours
        timer.schedule (task, 0l, 1000*60*60*6);
    }

    /*
    notifyUser(): send push notification to user
     */

    private void notifyUser(String message){

    }
}