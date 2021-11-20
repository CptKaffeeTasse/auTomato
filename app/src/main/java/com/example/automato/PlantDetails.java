package com.example.automato;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlantDetails extends AppCompatActivity {
    Plant plant = new Plant("Tomate", "Tobi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        TextView plantName = findViewById(R.id.plantName);
        ImageView plantPicture = findViewById(R.id.plantPicture);
        TextView currWaterlvl = findViewById(R.id.curr_water);
        TextView currTemp = findViewById(R.id.curr_temp);

        plantName.setText(plant.getName());
        plantPicture.setImageResource(plant.getPicture());
        currWaterlvl.setText(plant.getWaterlevel() + "%");
        currTemp.setText(plant.getTemperature() + "°C");
    }
}