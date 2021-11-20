package com.example.automato;

import org.json.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class ReadJsonFile {

    public static InputStream inputStreamFromFile(String path){
        InputStream input = null;
        try{
            input = FileHandler.class.getResourceAsStream(path);
        } catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }

    public static String getJSONStrFromFile(String path){
        InputStream in = inputStreamFromFile(path);
        Scanner scanner = new Scanner(in);
        String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
        //in.close();
        return json;
    }

    public static JSONArray getJSONArrayFromFile(String path) {
        JSONArray obj = null;
        try {
            obj =  new JSONArray(getJSONStrFromFile(path));
        }catch(Exception e){
            e.printStackTrace();
        }
        return obj;
    }

    public static HashMap<String, String> getDataOfType(String type) {
        HashMap<String, String> data = new HashMap<>();
        JSONArray array = getJSONArrayFromFile("/info.json");
        for(int i = 0; i < array.length(); i++){
            try{
                JSONObject tmp = array.getJSONObject(i);
            if(tmp.get("type").equals(type)){
                data.put("required_water_level_max", tmp.get("required_water_level_max").toString());
                data.put("required_water_level_min", tmp.get("required_water_level_min").toString());
                data.put("required_water_level_max", tmp.get("required_water_level_max").toString());
                data.put("required_light_level_min", tmp.get("required_light_level_min").toString());
                data.put("required_temperature_max", tmp.get("required_temperature_max").toString());
                data.put("required_temperature_min", tmp.get("required_temperature_min").toString());
            }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return data;
    }

    public static boolean checkTypeExists(String type) throws JSONException {
        JSONArray array = getJSONArrayFromFile("/info.json");
        for(int i = 0; i < array.length(); i++){
            JSONObject tmp = array.getJSONObject(i);
            if(tmp.get("type").equals(type))
                return true;
        }
        return false;
    }
}
