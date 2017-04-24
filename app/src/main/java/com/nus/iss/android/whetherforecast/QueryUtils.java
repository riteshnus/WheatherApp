package com.nus.iss.android.whetherforecast;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class QueryUtils {

    private QueryUtils() {
    }
    public static final String LOG_TAG = QueryUtils.class.getName();
    private static Date localDate = null;
    public static Weather extractWeather(String urlToCall) {

        Weather weather = null;
        String response = callHttpConnection(urlToCall);
        try {
            if(!response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                Log.v("Single JSON response", jsonObject.toString());
                JSONArray whaetherJsonArray = jsonObject.getJSONArray("weather");
                String description = whaetherJsonArray.getJSONObject(0).getString("description").toUpperCase();
                String condition = whaetherJsonArray.getJSONObject(0).getString("main");

                JSONObject mainJsonObject = jsonObject.getJSONObject("main");
                String temp = mainJsonObject.getString("temp") + " °F";
                String pressure = mainJsonObject.getString("pressure") + " Pressure";
                String humidity = mainJsonObject.getString("humidity") + " Humidity";
                String temp_min = mainJsonObject.getString("temp_min");
                String temp_max = mainJsonObject.getString("temp_max");

                String location = jsonObject.getString("name").toString();
                String tempMinMax = temp_max + " / " + temp_min + " °F";
                Date currentdate = Calendar.getInstance().getTime();
                //Log.v("response",response);
                weather = new Weather(description, condition, temp, temp_min, temp_max, humidity, pressure, tempMinMax, currentdate.toString());
                weather.setLocation(location);
            }else{
                Log.v(LOG_TAG,"No json Response");
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return weather;
    }

    public static String callHttpConnection(String url){
        String jsonResponse = "";
        URL newUrl = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            Thread.sleep(2000);
            newUrl = new URL(url);
            if (newUrl == null)
                return jsonResponse;
            urlConnection = (HttpURLConnection) newUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<Weather> extractForcast(String urlToCall) {

        ArrayList<Weather> weathers = new ArrayList<>();
        String response = callHttpConnection(urlToCall);
        try {
            JSONObject jsonObjectRes = new JSONObject(response);
            JSONArray jsonArray = jsonObjectRes.getJSONArray("list");
            Log.v(LOG_TAG, "In extractForcast"+ weathers.size());
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dateInMis = jsonObject.getString("dt_txt");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(dateInMis);
                boolean isCorrect = CheckNextDate(date);
                if(isCorrect){
                    Log.v(LOG_TAG,jsonObject.toString());
                    JSONArray whaetherJsonArray = jsonObject.getJSONArray("weather");
                    String description = whaetherJsonArray.getJSONObject(0).getString("description").toUpperCase();
                    String condition = whaetherJsonArray.getJSONObject(0).getString("main");
                    JSONObject mainJsonObject = jsonObject.getJSONObject("main");
                    String temp = mainJsonObject.getString("temp")+" °F";
                    String pressure = mainJsonObject.getString("pressure");
                    String humidity = mainJsonObject.getString("humidity");
                    String temp_min = mainJsonObject.getString("temp_min");
                    String temp_max = mainJsonObject.getString("temp_max");
                    String tempMinMax = temp_max+" / "+temp_min+" °F";
                    SimpleDateFormat formatterNew = new SimpleDateFormat("EEE, d MMM yyyy");
                    Weather weather = new Weather(description, condition, temp, temp_min, temp_max, humidity, pressure,tempMinMax,formatterNew.format(date));
                    weathers.add(weather);
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.v("weathers count",""+ weathers.size());
        return weathers;
    }

    public static boolean CheckNextDate(Date currentDate){
        boolean isAccept = false;
        if(localDate == null) {
            localDate = currentDate;
        }
        if(localDate.compareTo(currentDate)==0){
            Calendar c = Calendar.getInstance();
            c.setTime(localDate);
            c.add(Calendar.DATE, 1);
            localDate = c.getTime();
            return true;
        }
        return isAccept;
    }

    public static TreeMap<String,List<String>> extractCityMap(String urlToCall) {

        TreeMap<String,List<String>> cityMap = new TreeMap<>();
        String response = callHttpConnection(urlToCall);
        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.v(LOG_TAG, "In extractCityMap");
            Iterator jsonKeys = jsonObject.keys();
            while(jsonKeys.hasNext()){
                String countryKey = (String)jsonKeys.next();
                JSONArray jsonArray = jsonObject.getJSONArray(countryKey);
                List<String> cityList = new ArrayList<>();
                for(int x=0;x<jsonArray.length();x++){
                    cityList.add(jsonArray.get(x).toString());
                }
                cityMap.put(countryKey,cityList);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return cityMap;
    }
}
