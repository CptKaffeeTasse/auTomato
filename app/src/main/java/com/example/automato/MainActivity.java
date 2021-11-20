package com.example.automato;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;

    public static List<Plant> plants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });
    }

    public void checkPlants() {
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

    private void notifyUser(String message){
        // send push notification to user
    }

    public void insertItem(int position) {
        mExampleList.add(position, new ExampleItem(R.drawable.tomato, "New Item At Position" + position, "This is Line 2"));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createExampleList() {
        Plant plant1 = new Plant("indoor tomato", "ben", "",true);
        Plant plant2 = new Plant("indoor tomato", "toni","", true);
        Plant plant3 = new Plant("indoor tomato", "julie","", true);


        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.tomato, plant1.getName(), plant1.checkOnPlant()?"we're good :)":"we're not goof :("  ));
        mExampleList.add(new ExampleItem(R.drawable.tomato, plant2.getName(), plant2.checkOnPlant()?"we're good :)":"we're not goof :("  ));
        mExampleList.add(new ExampleItem(R.drawable.tomato, plant3.getName(), plant3.checkOnPlant()?"we're good :)":"we're not goof :("  ));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.scroller);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, PlantDetails.class);
                startActivity(intent);
                //mExampleList.get(position).changeText1("test");
                //mAdapter.notifyItemChanged(position);
            }
        });


//_____________________________________________________
        Timer timer = new Timer ();
        TimerTask task = new TimerTask () {
            @Override
            public void run () {
                for(Plant plant: plants)
                    if(!plant.checkOnPlant()){

                    }
            }
        };

        //check on plants every six hours
        timer.schedule (task, 0l, 1000*60*60*6);
    }

    private void notify(String message){
        // send push notification to user

    }
}