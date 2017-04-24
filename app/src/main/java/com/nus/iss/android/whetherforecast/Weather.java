package com.nus.iss.android.whetherforecast;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class Weather {

    private String description;
    private String condition;
    private String temp;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
    private String tempMinMax;
    private String date;
    private String location;


    public Weather(String description, String condition, String temp, String temp_min, String temp_max, String humidity, String pressure,String tempMinMax, String date) {
        this.description = description;
        this.condition = condition;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempMinMax = tempMinMax;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTempMinMax() {
        return tempMinMax;
    }

    public void setTempMinMax(String tempMinMax) {
        this.tempMinMax = tempMinMax;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
