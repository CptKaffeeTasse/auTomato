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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    private ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    private Button buttonRefresh;

    public static List<Plant> plants = new ArrayList<>() {{
        add(new Plant("indoor tomato", "ben", ""));
        add(new Plant("indoor tomato", "toni",""));
        add(new Plant("indoor tomato", "julie",""));
    }};



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
                ExampleDialog exampleDialog = new ExampleDialog(position);
                exampleDialog.show(getSupportFragmentManager(), "example dialog");
                mAdapter.notifyDataSetChanged();

            }
        });


        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
                mAdapter.notifyDataSetChanged();

            }
        });

        checkPlants();
    }

    public void checkPlants() {
        Timer timer = new Timer ();
        TimerTask task = new TimerTask () {
            @Override
            public void run () {
                int counter = 0;
                for(Plant plant: plants) {
                    if (!plant.checkOnPlant()) {
                        List<String> messages = plant.getDetailedMessage();
                        for (String message : messages)
                            if (message != null)
                                notifyUser(message);

                        mExampleList.get(counter).setmText2("we're not good :(");
                    } else {
                        mExampleList.get(counter).setmText2("we're good :)");
                    }
                    counter++;
                }
            }
        };

        //check on plants every thirty seconds
        timer.schedule (task, 0l, 1000*30);
    }

    private void notifyUser(String message){
        // send push notification to user
    }

    public void insertItem(int position, String plantName) {
        plants.add(position,new Plant("indoor tomato", plantName, ""));
        mExampleList.add(position, new ExampleItem(plants.get(position).getPicture(), plants.get(position).getName(), plants.get(position).checkOnPlant()?"we're good :)":"we're not good :("));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        plants.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(plants.get(0).getPicture(), plants.get(0).getName(), plants.get(0).checkOnPlant()?"we're good :)":"we're not good :("  ));
        mExampleList.add(new ExampleItem(plants.get(1).getPicture(), plants.get(1).getName(), plants.get(1).checkOnPlant()?"we're good :)":"we're not good :("  ));
        mExampleList.add(new ExampleItem(plants.get(2).getPicture(), plants.get(2).getName(), plants.get(2).checkOnPlant()?"we're good :)":"we're not good :("  ));
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
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent(MainActivity.this, PlantDetails.class);
                intent.putExtra("PLANT", plants.get(position));
                startActivity(intent);
                //mExampleList.get(position).changeText1("test");
                //mAdapter.notifyItemChanged(position);
            }
        });

    }

    @Override
    public void applyText(String plantname, int position) {
        insertItem(position,plantname);
    }
}