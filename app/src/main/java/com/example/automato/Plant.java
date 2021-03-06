package com.example.automato;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


//import java.awt.*;


public class Plant implements Serializable {

    List<Integer> pics = new LinkedList<>() {{
        add(R.drawable.tomatenpflanze);
        add(R.drawable.tomate2);
        add(R.drawable.tomate3);
        add(R.drawable.tomate4);
        add(R.drawable.tomate5);
        add(R.drawable.tomate6);
        add(R.drawable.tomate7);
        add(R.drawable.tomate8);
    }};


    private String type;
    private String name;
    private String imagePath;
    private int picture;
    private LocalDateTime lastChecked;
    private boolean lastStatus;

    private int soil_humidity_min = 65;
    private int soil_humidity_max = 90;
    private int required_light_level_min = 55;
    private int required_light_level_max = 80;
    private double required_temperature_min = 18.0;
    private double required_temperature_max = 28.0;

    private static Random ran = new Random();

    private int current_light_level = ran.nextInt(50) + 40;
    private int current_water_level = ran.nextInt(50) + 50;
    private double current_temperature = ran.nextInt(20) + 10;

    private static int counter = 0;

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

        Timer timer = new Timer ();
        TimerTask task = new TimerTask () {
            @Override
            public void run () {
                current_light_level = getCurrentLightLevel();
                current_water_level = getCurrentSoilHumidity();
                current_temperature = getCurrentTemperature();
                counter++;
            }
        };

        //check on plants every thirty seconds
        timer.schedule (task, 0l, 1000*30);
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
            counter++;
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
        int stage = (difference < 10)?1:(difference < 25)?2:(difference<50)?3:4;
        StringBuilder builder = new StringBuilder();
        //builder.append("Your plant " + this.name + " the " + this.type);
        //builder.append("'s "+ factor + " is ");
        builder.append(factor + ": \n");
        builder.append((stage == 4)?"way ":"");
        builder.append((stage >= 2)?"too ":"");
        if(isMin) {
            builder.append("low, it needs ");
            builder.append((stage==1)?"a little ":(stage==2)?"":(stage==3)?"much ":"a lot ");
            builder.append("more ");
        }
        else {
            builder.append("high, it needs ");
            builder.append((stage==1)?"a little ":(stage==2)?"":(stage==3)?"much ":"a lot ");
            builder.append("less ");
        }
        //builder.append(factor + " by " + difference + " percent.\n");
        builder.append((factor.equals("light level"))?"sun light.":"water.");
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
        int salt = ran.nextInt(2);
        switch (salt) {
            case 0:
                return current_temperature +(0.8 + ran.nextInt(2));
            default:
                return current_temperature - (0.8 + ran.nextInt(2));
        }
        //return 15.0 + (24+counter)%24*0.6;
    }

    /*
    getCurrentWaterLevel(): get current soil humidity from sensor
     */

    public int getCurrentSoilHumidity(){
        int salt = ran.nextInt(2);
        switch (salt) {
            case 0:
                return current_water_level +( 1 + ran.nextInt(4));
            default:
                return current_water_level - (1 + ran.nextInt(4));
        }
        //return 90 - (counter)%50;
    }

    /*
    getCurrentLightLevel(): get current light level from sensor
     */

    public int getCurrentLightLevel(){
        int salt = ran.nextInt(2);
        switch (salt) {
            case 0:
                return current_light_level +( 1 + ran.nextInt(4));
            default:
                return current_light_level - (1 + ran.nextInt(4));
        }
        //return 80 - (counter)%50;
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
    public boolean getLastStatus(){
        return this.lastStatus;
    }
}
