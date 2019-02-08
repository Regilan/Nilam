package com.wordpress.regilanj.www.nilam;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FarmData {

    public String Temp;
    public String Humidity;
    public String HeatIndex;
    public String SoilAnalog;
    public String SoilDigital;
    public Map<String, Boolean> data = new HashMap<>();

    public FarmData() {

    }

    public  FarmData(String Temp, String Humidity, String HeatIndex, String SoilAnalog, String SoilDigital) {

        this.Temp = Temp;
        this.Humidity = Humidity;
        this.HeatIndex = HeatIndex;
        this.SoilAnalog = SoilAnalog;
        this.SoilDigital = SoilDigital;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("Temp",this.Temp);
        result.put("Humid",this.Humidity);
        result.put("HeatI",this.HeatIndex);
        result.put("SoilA",this.SoilAnalog);
        result.put("SoilD",this.SoilDigital);

        return result;

    }

    }
