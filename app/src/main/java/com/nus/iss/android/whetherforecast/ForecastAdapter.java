package com.nus.iss.android.whetherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class ForecastAdapter extends ArrayAdapter<Weather> {

    public ForecastAdapter(Context context, List<Weather> weathers) {
        super(context,0, weathers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.forcast_listview,parent,false);
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
        return listItemView;
    }



}
