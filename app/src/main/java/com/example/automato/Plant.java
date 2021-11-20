package com.example.automato;

public class Plant {

    private String type;
    private String name;

    public Plant(String type, String name){
        try {
            if (!ReadJsonFile.checkTypeExists(type))
                throw new Exception("Sorry, we do not have data on this type of plant yet.");
        }catch(Exception e){
            e.printStackTrace();
        }
        this.type = type;
        this.name = name;
    }

    public String getData(String key){
        return ReadJsonFile.getDataOfType(this.type).get(key);
    }
}
