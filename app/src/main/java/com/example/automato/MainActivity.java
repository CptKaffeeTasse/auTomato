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

        checkPlants();
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
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.tomato, "Line 1", "Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.tomato, "Line 3", "Line 4"));
        mExampleList.add(new ExampleItem(R.drawable.tomato, "Line 5", "Line 6"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.scroller);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
<<<<<<< HEAD

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

=======
>>>>>>> b57c90e6fa2812ca5e4e886d465c6364639928d2
    }
}