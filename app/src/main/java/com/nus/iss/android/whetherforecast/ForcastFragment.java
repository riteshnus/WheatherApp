package com.nus.iss.android.whetherforecast;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class ForcastFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Weather>>{

    public ForcastFragment() {
    }
    private final String url="http://api.openweathermap.org/data/2.5/forecast?q=singapore&appid=95f374f5396a9e255a48c7017c46e675";
    private ForecastAdapter weatherAdapter;
    private StackView stackView;
    StackAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forcast_main,container,false);

        /*ListView weatherListView = (ListView) rootView.findViewById(R.id.list);
        weatherAdapter = new ForecastAdapter(getActivity(),new ArrayList<Weather>());
        weatherListView.setAdapter(weatherAdapter);*/

        stackView = (StackView) rootView.findViewById(R.id.stack_view);
        adapter = new StackAdapter(getActivity(), new ArrayList<Weather>());
        stackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getLoaderManager().initLoader(1, null, this);
        return rootView;
    }

    @Override
    public Loader<List<Weather>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new ForcastLoader(getActivity(),uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> data) {
        //View loadingIndicator = findViewById(R.id.loading_indicator);
        //loadingIndicator.setVisibility(View.GONE);
        /*weatherAdapter.clear();
        if (data != null && !data.isEmpty()) {
            weatherAdapter.addAll(data);
        }*/
        /*for (int i = 0; i < data.size(); i++) {
            list.add(new StackItems(data.get(i)));
        }*/
        adapter = new StackAdapter(getActivity(), data);
        stackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Weather>> loader) {

    }

}
