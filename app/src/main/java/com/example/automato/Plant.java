package com.example.automato;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


//import java.awt.*;


public class Plant implements Serializable {

    List<Integer> pics = new LinkedList<>() {{
        add(R.drawable.tomatenpflanze);
        add(R.drawable.tomate2);
        add(R.drawable.tomate3);
        add(R.drawable.tomate4);
        add(R.drawable.tomate5);
    }};


    private String type;
    private String name;
    private String imagePath;
    private int picture;
    private LocalDateTime lastChecked;

    private int soil_humidity_min = 65;
    private int soil_humidity_max = 90;
    private int required_light_level_min = 55;
    private int required_light_level_max = 90;
    private double required_temperature_min = 10.0;
    private double required_temperature_max = 32.2;

    private int current_light_level = getCurrentLightLevel();
    private int current_water_level = getCurrentSoilHumidity();
    private double current_temperature = getCurrentTemperature();

    private static Random ran = new Random();

    public Plant(String type, String name, String imagePath){

        /*
        addPlant(type, name);
        try {
            if (!ReadJsonFile.checkTypeExists(type))
                throw new Exception("Sorry, we do not have data on this type of plant yet.");
        }catch(Exception e){
            e.printStackTrace();
        }
        */
        this.type = type;
        this.name = name;
        this.imagePath = imagePath;
        this.picture = pics.get(ran.nextInt(pics.size()));
        //initialize();
    }

    /*
    initialize(): extract all relevant data and store them in attributes
     */

    private void initialize(){
        soil_humidity_min = Integer.parseInt(getRequiredData("required_water_level_min"));
        soil_humidity_max = Integer.parseInt(getRequiredData("required_water_level_max"));
        required_light_level_min = Integer.parseInt(getRequiredData("required_light_level_min"));
        required_light_level_max = Integer.parseInt(getRequiredData("required_light_level_max"));
        required_temperature_min = Double.parseDouble(getRequiredData("required_temperature_min"));
        required_temperature_max = Double.parseDouble(getRequiredData("required_temperature_max"));
    }

    /*
    addPlant(): store data of the newly added plant into json file


    private void addPlant(String type, String name){


        try (FileWriter writer = new FileWriter("./app/src/main/assets/plants.json")){

        try {
            JSONArray plants = ReadJsonFile.getJSONArrayFromFile("/plants.json");
            for(int i = 0; i < plants.length(); i++)
                if(plants.getJSONObject(i).get("name").equals(name) && plants.getJSONObject(i).get("type").equals(type))
                    return;
        } catch(Exception e){
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("/plants.json")){


            JSONObject obj = new JSONObject();
            obj.put("type", type);
            obj.put("name", name);
            obj.put("image path", imagePath);


            JSONArray plants = ReadJsonFile.getJSONArrayFromFile("./app/src/main/assets/plants.jsonplants.json");

            JSONArray plants = ReadJsonFile.getJSONArrayFromFile("/plants.json");

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
                    the temperature, soil humidity and light level
     */

    public boolean checkOnPlant(){
        boolean output = true;
        try {


            output = current_light_level >= required_light_level_min && current_light_level <= required_light_level_max
                    && current_water_level >= soil_humidity_min && current_water_level <= soil_humidity_max
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
            output.add(generateMessage(required_light_level_max, current_light_level, false, "light level"));
            output.add(generateMessage(required_light_level_min, current_light_level, true, "light level"));
            output.add(generateMessage(soil_humidity_min, current_water_level, true, "soil humidity"));
            output.add(generateMessage(soil_humidity_max, current_water_level, false, "soil humidity"));
            if(current_temperature < required_temperature_min)
                output.add("Temperature: \nCurrent temperature is too low, your plant " + this.name + " the " + this.type +
                        " can only tolerate a minimum temperature of "+ required_temperature_min);
            else if(current_temperature > required_temperature_max)
                output.add("Temperature: \nCurrent temperature is too high, your plant " + this.name + " the " + this.type +
                        " can only tolerate a maximum temperature of "+ required_temperature_min);
                output.add("Current temperature is too high, your plant " + this.name + " the " + this.type +
                        " can only tolerate a maximum temperature of "+ required_temperature_max);
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
        //builder.append("Your plant " + this.name + " the " + this.type);
        //builder.append("'s "+ factor + " is ");
        builder.append(factor + ": \n");
        if(isMin)
            builder.append("too low, please raise ");
        else
            builder.append("too high, please lower ");
        builder.append(factor + " by " + difference + " percent.\n");
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
        return (double)ran.nextInt(35);
    }

    /*
    getCurrentWaterLevel(): get current soil humidity from sensor
     */

    public int getCurrentSoilHumidity(){
        return ran.nextInt(100);
    }

    /*
    getCurrentLightLevel(): get current light level from sensor
     */

    public int getCurrentLightLevel(){
        return ran.nextInt(100);
    }

    public double getCurrent_temperature() {
        return current_temperature;
    }

    public int getCurrent_light_level() {
        return current_light_level;
    }

    public int getCurrent_water_level() {
        return current_water_level;
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

    public int getPicture() {
        return picture;
    }
}
