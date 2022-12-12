package edu.uw.tcss450.uwnetid.raindropapp.ui.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentLocationWeatherBinding;

/**
 * This Fragment is for obtaining the Weather information using the current
 * location of the device. It gets the latitude and longitude from the GPS
 * location and uses that to make GET https requests for current weather,
 * hourly forecast, and 5-day forecasts.
 */
public class LocationWeatherFragment extends Fragment
{
    private FragmentLocationWeatherBinding mBinding;
    EditText currentCity, currentCountry;
    TextView weatherResult;
    private final String url = "https://api.openweathermap.org/data/2.5/";
    //Url from weatherbit api that is no longer in use
    //private final String url = "https://api.weatherbit.io/v2.0/";
    private final String mKey = "8a2398f36aaae06e0b76d942954de8cd";
    //Keys from weatherbit api that are no longer in use
    //private final String mKey = "f0a7f688802a43609edd1fc3a82260ca";
    //private final String key = "0721a87721a04175806802e3e8688271";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private LocationManager mLocationManager;
    private Criteria criteria;
    private Location mLocation;
    private String mLatitude = "";
    private String mLongitude = "";

    public LocationWeatherFragment()
    {
        //Required empty public constructor
    }

    /**
     * In this Fragment's onCreate() a bit of Location logic is implemented
     * in order to get the GPS location and fetch the latitude and longitude.
     * It retrieves the last known location and checks if it is null. If it is not
     * it uses that location to get a latitude and longitude from, if it is null,
     * the latitude and longitude are set to UW Tacoma's location by default.
     * @param savedInstanceState Parameter for the savedInstanceState using Bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        double latitude = 0;
        double longitude = 0;

        mLocationManager = (LocationManager) this.requireActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        String bestProvider = String.valueOf(mLocationManager
                .getBestProvider(criteria, true)).toString();

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(bestProvider);

        if (location != null)
        {
            Log.e("TAG", "GPS is on");
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else
        {
            /*
             * Below code would work but currently it does not as a temporary work
             * around it just sets latitude and longitude to UW Tacoma by default.
             * As a side note, the emulator is weird and google maps may have to
             * be opened first in order for it to save a Last Known Location for
             * the above code to access.
             */
            //mLocationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            latitude = 47.2454;
            longitude = -122.4385;
        }

        mLatitude += latitude;
        mLongitude += longitude;

        Log.d("Latitude", mLatitude);
        Log.d("Longitude", mLongitude);
    }

    /**
     * Standard implementation for the onCreateView() method.
     * @param inflater Parameter for the LayoutInflater.
     * @param container Parameter for the container.
     * @param savedInstanceState Parameter for the savedInstanceState using Bundle.
     * @return Returns the root for mBinding.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mBinding = FragmentLocationWeatherBinding.inflate(inflater);

        //Inflate the layout for this fragment
        return mBinding.getRoot();
    }


    /**
     * Implementing onViewCreated() to initialize the buttons that
     * the user will be able to use to pick what type of Weather they would
     * like to see: current, hourly, or 5-day.
     * @param view Parameter for the View.
     * @param savedInstanceState Parameter for the savedInstanceState from Bundle.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mBinding.buttonCurrentForecast.setOnClickListener(this::getCurrentWeather);
        mBinding.buttonHourlyForecast.setOnClickListener(this::getHourlyWeather);
        mBinding.buttonFiveDayForecast.setOnClickListener(this::getFiveDayWeather);
    }

    /**
     * This method getCurrentWeather() gets the current Weather using a
     * GET https request to obtain the current Weather information.
     * @param view Parameter for the View.
     */
    public void getCurrentWeather(final View view)
    {
        String tempURL = "";
        String latitude = mLatitude;
        String longitude = mLongitude;

        if (latitude.equals(""))
        {
            weatherResult.setText("Location could not be found.");
        }
        else
        {
            tempURL = url + "weather" + "?lat=" + latitude + "&lon=" + longitude
                    + "&appid=" + mKey + "&units=imperial";
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    tempURL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Log.d("Response", response);
                    String output = "";
                    JSONObject jsonResponse;
                    JSONObject jsonWeather;
                    String description;
                    JSONArray jsonArray;
                    JSONObject jsonMain;
                    JSONObject jsonWind;
                    JSONObject jsonClouds;
                    JSONObject jsonCoord;
                    double temp;
                    double feelsLike;
                    float pressure;
                    int humidity;
                    String wind;
                    String clouds;
                    String outLatitude;
                    String outLongitude;

                    try
                    {
                        jsonResponse = new JSONObject(response);
                        jsonArray = jsonResponse.getJSONArray("weather");
                        jsonWeather = jsonArray.getJSONObject(0);
                        description = jsonWeather.getString("description");
                        jsonMain = jsonResponse.getJSONObject("main");
                        temp = jsonMain.getDouble("temp");
                        feelsLike = jsonMain.getDouble("feels_like");
                        pressure = jsonMain.getInt("pressure");
                        humidity = jsonMain.getInt("humidity");
                        jsonWind = jsonResponse.getJSONObject("wind");
                        wind = jsonWind.getString("speed");
                        jsonClouds = jsonResponse.getJSONObject("clouds");
                        clouds = jsonClouds.getString("all");
                        jsonCoord = jsonResponse.getJSONObject("coord");
                        outLatitude = jsonCoord.getString("lat");
                        outLongitude = jsonCoord.getString("lon");
                        mBinding.weatherResult.setTextColor(Color.rgb(0, 0, 0));
                        output += "Current Weather of " + outLatitude + ", " + outLongitude
                                + "\nTemp: " + decimalFormat.format(temp) + " °F"
                                + "\nFeels Like: " + decimalFormat.format(feelsLike) + " °F"
                                + "\nHumidity: " + humidity + "%"
                                + "\nDescription: " + description
                                + "\nWind Speed: " + wind + "m/s (meters per second)"
                                + "\nCloudiness: " + clouds + "%"
                                + "\nPressure: " + pressure + " hPa";
                        mBinding.weatherResult.setText(output);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) //Error handling for Volley
                {
                    Toast.makeText(getActivity(), error.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    /**
     * This method getHourlyWeather() gets the hourly forecast using a
     * GET https request to obtain the hourly Weather information.
     * @param view Parameter for the View.
     */
    public void getHourlyWeather(final View view)
    {
        String tempURL = "";
        String latitude = mLatitude;
        String longitude = mLongitude;

        if (latitude.equals(""))
        {
            weatherResult.setText("City field is empty.");
        }
        else
        {
            if (!latitude.equals(""))
            {
                tempURL = url + "forecast" + "?lat=" + latitude + "&lon=" + longitude
                        + "&appid=" + mKey + "&units=imperial";
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    tempURL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Log.d("Response", response);
                    String output = "";
                    JSONObject jsonResponse;
                    JSONObject jsonWeather;
                    String description;
                    JSONArray jsonList;
                    JSONObject jsonListMain;
                    JSONArray jsonArray;
                    JSONObject jsonListWeather;
                    JSONObject jsonWind;
                    JSONObject jsonClouds;
                    double temp;
                    double feelsLike;
                    float pressure;
                    int humidity;
                    String wind;
                    String clouds;
                    String time;

                    try
                    {
                        jsonResponse = new JSONObject(response);
                        jsonList = jsonResponse.getJSONArray("list");
                        jsonWeather = jsonList.getJSONObject(0);
                        jsonListMain = jsonWeather.getJSONObject("main");
                        temp = jsonListMain.getDouble("temp");
                        feelsLike = jsonListMain.getDouble("feels_like");
                        pressure = jsonListMain.getInt("pressure");
                        humidity = jsonListMain.getInt("humidity");
                        jsonArray = jsonWeather.getJSONArray("weather");
                        jsonListWeather = jsonArray.getJSONObject(0);
                        description = jsonListWeather.getString("description");
                        jsonWind = jsonWeather.getJSONObject("wind");
                        wind = jsonWind.getString("speed");
                        jsonClouds = jsonWeather.getJSONObject("clouds");
                        clouds = jsonClouds.getString("all");
                        time = jsonWeather.getString("dt_txt");
                        mBinding.weatherResult.setTextColor(Color.rgb(0, 0, 0));
                        output += "Current Weather on " + time
                                + "\nTemp: " + decimalFormat.format(temp) + " °F"
                                + "\nFeels Like: " + decimalFormat.format(feelsLike) + " °F"
                                + "\nHumidity: " + humidity + "%"
                                + "\nDescription: " + description
                                + "\nWind Speed: " + wind + "m/s (meters per second)"
                                + "\nCloudiness: " + clouds + "%"
                                + "\nPressure: " + pressure + " hPa";
                        mBinding.weatherResult.setText(output);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getActivity(), error.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    /**
     * This method getHourlyWeather() gets the five day forecast using a
     * GET https request to obtain the hourly Weather information. Yes it
     * is actually the hourly Weather information as the API being used does
     * not have options for daily forecasts that are not free. The JWT I can
     * obtain has the Weather information in an array that is every 3 hours,
     * up to 5 days. GET request is made using latitude and longitude in this
     * case.
     * @param view Parameter for the View.
     */
    public void getFiveDayWeather(final View view)
    {
        String tempURL = "";
        String latitude = mLatitude;
        String longitude = mLongitude;

        if (latitude.equals(""))
        {
            weatherResult.setText("City field is empty.");
        }
        else
        {
            if (!latitude.equals(""))
            {
                tempURL = url + "forecast" + "?lat=" + latitude + "&lon=" + longitude
                        + "&appid=" + mKey + "&units=imperial";
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    tempURL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Log.d("Response", response);
                    String output = "";
                    JSONObject jsonResponse;
                    JSONObject jsonWeather;
                    String description;
                    JSONArray jsonList;
                    JSONObject jsonListMain;
                    JSONArray jsonArray;
                    JSONObject jsonListWeather;
                    JSONObject jsonWind;
                    JSONObject jsonClouds;
                    double temp;
                    double feelsLike;
                    float pressure;
                    int humidity;
                    String wind;
                    String clouds;
                    String time;

                    try
                    {
                        jsonResponse = new JSONObject(response);
                        jsonList = jsonResponse.getJSONArray("list");
                        jsonWeather = jsonList.getJSONObject(39);
                        jsonListMain = jsonWeather.getJSONObject("main");
                        temp = jsonListMain.getDouble("temp");
                        feelsLike = jsonListMain.getDouble("feels_like");
                        pressure = jsonListMain.getInt("pressure");
                        humidity = jsonListMain.getInt("humidity");
                        jsonArray = jsonWeather.getJSONArray("weather");
                        jsonListWeather = jsonArray.getJSONObject(0);
                        description = jsonListWeather.getString("description");
                        jsonWind = jsonWeather.getJSONObject("wind");
                        wind = jsonWind.getString("speed");
                        jsonClouds = jsonWeather.getJSONObject("clouds");
                        clouds = jsonClouds.getString("all");
                        time = jsonWeather.getString("dt_txt");
                        mBinding.weatherResult.setTextColor(Color.rgb(0, 0, 0));
                        output += "Current Weather on " + time
                                + "\nTemp: " + decimalFormat.format(temp) + " °F"
                                + "\nFeels Like: " + decimalFormat.format(feelsLike) + " °F"
                                + "\nHumidity: " + humidity + "%"
                                + "\nDescription: " + description
                                + "\nWind Speed: " + wind + "m/s (meters per second)"
                                + "\nCloudiness: " + clouds + "%"
                                + "\nPressure: " + pressure + " hPa";
                        mBinding.weatherResult.setText(output);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getActivity(), error.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
}
