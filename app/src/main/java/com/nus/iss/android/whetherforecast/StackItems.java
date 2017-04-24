package com.nus.iss.android.whetherforecast;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class StackItems {

    Integer image;
    Weather weather;

    public StackItems(Weather weather) {
        this.weather = weather;
    }

    public int getImage() {
        return image;
    }


}
