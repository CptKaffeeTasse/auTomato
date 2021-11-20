package com.example.automato;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import java.lang.Object;
import java.awt.*;

public class Plant {

    private String type;
    private String name;
    private String imagePath;
    private LocalDateTime lastChecked;

    private int required_water_level_min;
    private int required_water_level_max;
    private int required_light_level_min;
    private int required_light_level_max;
    private double required_temperature_min;
    private double required_temperature_max;

    public Plant(String type, String name, String imagePath, boolean newPlant){

        if(newPlant)
            addPlant(type, name);
        try {
            if (!ReadJsonFile.checkTypeExists(type))
                throw new Exception("Sorry, we do not have data on this type of plant yet.");
        }catch(Exception e){
            e.printStackTrace();
        }

        this.type = type;
        this.name = name;
        this.imagePath = imagePath;
        initialize();
    }

    /*
    initialize(): extract all relevant data and store them in attributes
     */

    private void initialize(){
        required_water_level_min = Integer.parseInt(getRequiredData("required_water_level_min"));
        required_water_level_max = Integer.parseInt(getRequiredData("required_water_level_max"));
        required_light_level_min = Integer.parseInt(getRequiredData("required_light_level_min"));
        required_light_level_max = Integer.parseInt(getRequiredData("required_light_level_max"));
        required_temperature_min = Double.parseDouble(getRequiredData("required_temperature_min"));
        required_temperature_max = Double.parseDouble(getRequiredData("required_temperature_max"));
    }

    /*
    addPlant(): store data of the newly added plant into json file
     */

    private void addPlant(String type, String name){
        try (FileWriter writer = new FileWriter("plants.json")){
            JSONObject obj = new JSONObject();
            obj.put("type", type);
            obj.put("name", name);
            obj.put("image path", imagePath);

            JSONArray plants = ReadJsonFile.getJSONArrayFromFile("plants.json");
            plants.put(obj);
            writer.write(plants.toString());
            writer.flush();
        }
        catch(JSONException | IOException e){
            e.printStackTrace();
        }
    }

    /*
    checkOnPlant(): checks whether the plant is in suitable conditions, taking into account
                    the temperature, water level and light level
     */

    public boolean checkOnPlant(){
        boolean output = true;
        try {
            int current_light_level = getCurrentLightLevel();
            int current_water_level = getCurrentWaterLevel();
            double current_temperature = getCurrentTemperature();

            output = current_light_level >= required_light_level_min && current_light_level <= required_light_level_max
                    && current_water_level >= required_water_level_min && current_water_level <= required_water_level_max
                    && current_temperature >= required_temperature_min && current_temperature <= required_temperature_max;
        } catch(Exception e){
            e.printStackTrace();
        }

        lastChecked = LocalDateTime.now();
        return output;
    }

    /*
    getDetailedMessage(): this method will only be called if the plant is not in a suitable environment,
                            it generates instructions on how to act (Warning: list elements could be null)
     */

    public List<String> getDetailedMessage(){
        List<String> output = new LinkedList<>();
        try {
            int current_light_level = getCurrentLightLevel();
            int current_water_level = getCurrentWaterLevel();
            double current_temperature = getCurrentTemperature();

            output.add(generateMessage(required_light_level_max, current_light_level, false, "light level"));
            output.add(generateMessage(required_light_level_min, current_light_level, true, "light level"));
            output.add(generateMessage(required_water_level_max, current_water_level, true, "water level"));
            output.add(generateMessage(required_water_level_min, current_water_level, false, "water level"));
            if(current_temperature < required_temperature_min)
                output.add("Current temperature is too low, your plant " + this.name + " the " + this.type +
                        " can only tolerate a minimum temperature of "+ required_temperature_min);
            else if(current_temperature > required_temperature_max)
                output.add("Current temperature is too high, your plant " + this.name + " the " + this.type +
                        " can only tolerate a maximum temperature of "+ required_temperature_min);
        } catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }

    /*
    generateMessage(): help method of getDetailedMessage(), the argument 'isMin' will be activated if the
                        argument 'required' refers to a minimum value, 'factor' refers to the environmental
                        factor it is dealing with right now (water/ light level, temperature)
     */

    private String generateMessage(int required, int current, boolean isMin, String factor){
        if(isMin && current >= required || !isMin && current <= required)
            return null;
        int difference = (int)Math.abs(current-required);
        StringBuilder builder = new StringBuilder();
        builder.append("Your plant " + this.name + " the " + this.type);
        builder.append("'s "+ factor + " is ");
        if(isMin)
            builder.append("too low, please raise ");
        else
            builder.append("too high, please lower ");
        builder.append(factor + " by " + difference + " percent.");
        return builder.toString();
    }

    /*
    getRequiredData(): given the keyword, get data about the needs of the plant
     */

    public String getRequiredData(String key){
        return ReadJsonFile.getDataOfType(this.type).get(key);
    }

    /*
    getCurrentTemperature(): get current temperature from sensor
     */

    public double getCurrentTemperature(){
        Random ran = new Random();
        return ran.nextInt(35);
    }

    /*
    getCurrentWaterLevel(): get current water level from sensor
     */

    public int getCurrentWaterLevel(){
        Random ran = new Random();
        return ran.nextInt(100);
    }

    /*
    getCurrentLightLevel(): get current light level from sensor
     */

    public int getCurrentLightLevel(){
        Random ran = new Random();
        return ran.nextInt(100);
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    public String getImagePath(){
        return this.imagePath;
    }
}
