package com.nus.iss.android.whetherforecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class StackAdapter extends BaseAdapter {

    List<Weather> weathers;
    LayoutInflater inflater;
    RecyclerView.ViewHolder holder = null;

    public StackAdapter(Context context, List<Weather> weathers) {
        this.weathers = weathers;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Weather getItem(int position) {
        return weathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listItemView = view;

        if(listItemView == null){
            listItemView = inflater.inflate(R.layout.forcast_listview, parent, false);
        }

        Weather weather = getItem(position);
        TextView forcastDesc = (TextView)listItemView.findViewById(R.id.tv_forcast_desc);
        forcastDesc.setText(weather.getDescription());
        TextView forcastTemp = (TextView)listItemView.findViewById(R.id.tv_forcast_temp);
        forcastTemp.setText(weather.getTemp());
        TextView forcastHumidity = (TextView)listItemView.findViewById(R.id.tv_forcast_humidity);
        forcastHumidity.setText(weather.getHumidity());
        TextView forcastPressure = (TextView)listItemView.findViewById(R.id.tv_forcast_pressure);
        forcastPressure.setText(weather.getPressure());
        TextView forcastMaxMin = (TextView)listItemView.findViewById(R.id.tv_forcast_max_min);
        forcastMaxMin.setText(weather.getTempMinMax());
        TextView forcastCondition = (TextView)listItemView.findViewById(R.id.tv_forcast_condition);
        forcastCondition.setText(weather.getCondition());
        TextView forcastDate = (TextView)listItemView.findViewById(R.id.tv_date);
        String dateInMis = weather.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        try {
            String currentDate = formatter.format(new Date());
            Date d1 = formatter.parse(dateInMis);
            Date d2 = formatter.parse(currentDate);;
            if(dateInMis.equals(currentDate)){
                forcastDate.setText("Today's");
            }else if(daysBetween(d2.getTime(),d1.getTime()) == 1){
                forcastDate.setText("Tommorow");
            }else{
                forcastDate.setText(dateInMis);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listItemView;
    }

    public int daysBetween(long t1, long t2) {
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }
}
