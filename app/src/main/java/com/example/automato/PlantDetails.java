package com.example.automato;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlantDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);
        Plant plant = (Plant) getIntent().getSerializableExtra("PLANT");

        TextView plantName = findViewById(R.id.plantName);
        ImageView plantPicture = findViewById(R.id.plantPicture);
        TextView currWaterlvl = findViewById(R.id.curr_water);
        TextView currTemp = findViewById(R.id.curr_temp);
        TextView currlight = findViewById(R.id.currlight);
        TextView message = findViewById(R.id.message);

        plantName.setText(plant.getName());
        plantPicture.setImageResource(plant.getPicture());
        currWaterlvl.setText(plant.getCurrentSoilHumidity() + "%");
        currTemp.setText(plant.getCurrentTemperature() + "°C");
        currlight.setText(plant.getCurrentLightLevel() + "%");
        StringBuilder b = new StringBuilder();
        plant.getDetailedMessage().stream().filter(s -> s != null).forEach(s -> b.append("\n").append(s));
        message.setText(b.toString());
    }
}