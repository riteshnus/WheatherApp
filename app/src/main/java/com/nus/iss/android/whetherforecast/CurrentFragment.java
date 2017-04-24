package com.nus.iss.android.whetherforecast;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ritesh on 4/22/2017.
 */

public class CurrentFragment extends Fragment implements LocationListener, LoaderManager.LoaderCallbacks<Weather> {

    public CurrentFragment() {
        // Required empty public constructor
    }

    public static final String LOG_TAG = MainActivity.class.getName();

    private TextView mLocation;
    private TextView mdescription;
    private TextView mcondition;
    private TextView mtemp;
    private TextView mtemp_max_min;
    private TextView mpressure;
    private TextView mhumidity;

    LocationManager locationManager;
    private String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public Location location;
    private String userLocation;
    private String url = "http://api.openweathermap.org/data/2.5/weather?appid=95f374f5396a9e255a48c7017c46e675&q=";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.current_view, container, false);

        // Get the location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) { // Marshmallow
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else{
                Toast.makeText(getActivity(), "no permission", Toast.LENGTH_SHORT).show();
                //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, this);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mLocation = (TextView) rootView.findViewById(R.id.message);

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }

        mtemp = (TextView) rootView.findViewById(R.id.tv_temp);
        mcondition = (TextView) rootView.findViewById(R.id.tv_condition);
        mdescription = (TextView) rootView.findViewById(R.id.tv_desc);
        mtemp_max_min = (TextView) rootView.findViewById(R.id.tv_max_min_temp);
        mpressure = (TextView) rootView.findViewById(R.id.tv_pressure);
        mhumidity = (TextView) rootView.findViewById(R.id.tv_humidity);

        CurrentWeatherAsyncTask currentWeatherAsyncTask = new CurrentWeatherAsyncTask();
        Log.v(LOG_TAG,url+userLocation);
        currentWeatherAsyncTask.execute(url+userLocation);
        //getLoaderManager().initLoader(0, null, this).forceLoad();
        //QueryUtils.callHttpConnection(url);

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        Log.v(LOG_TAG,location.toString());
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            String countryName = address.get(0).getAddressLine(1).split(" ")[0];
            userLocation = countryName;
            Log.v("location new ",countryName);
            mLocation.setText(countryName.toString()); //This will display the final address.
        } catch (IOException e) {
            Log.e(LOG_TAG,e.toString());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG,e.toString());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setTextValues(Weather weather){
        mtemp.setText(weather.getTemp());
        mcondition.setText(weather.getCondition());
        mdescription.setText(weather.getDescription());
        mtemp_max_min.setText(weather.getTempMinMax());
        mpressure.setText(weather.getPressure());
        mhumidity.setText(weather.getHumidity());
    }


    @Override
    public Loader<Weather> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new CurrentLoader(getActivity(),uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<Weather> loader, Weather weather) {
        setTextValues(weather);
    }

    @Override
    public void onLoaderReset(Loader<Weather> loader) {

    }

    private class CurrentWeatherAsyncTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            if (params.length < 1 || params[0] == null) {
                return null;
            }
            Weather weathers = QueryUtils.extractWeather(params[0]);
            return weathers;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather != null) {
                setTextValues(weather);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getActivity(), "no location granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
