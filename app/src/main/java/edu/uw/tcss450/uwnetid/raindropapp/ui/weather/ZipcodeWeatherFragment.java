
package edu.uw.tcss450.uwnetid.raindropapp.ui.weather;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentZipcodeWeatherBinding;

/**
 * This Fragment is for obtaining the Weather information using a user entered
 * city in the TextView. It gets the zipcode from a user entered EditText
 * field and uses that to make GET https requests for current weather,
 * hourly forecast, and 5-day forecasts.
 */
public class ZipcodeWeatherFragment extends Fragment
{
    private FragmentZipcodeWeatherBinding mBinding;
    EditText zipCode, currentCountry;
    TextView weatherResult;
    private final String url = "https://api.openweathermap.org/data/2.5/";
    //Url from weatherbit api that is no longer in use
    //private final String url = "https://api.weatherbit.io/v2.0/";
    private final String mKey = "8a2398f36aaae06e0b76d942954de8cd";
    //Keys from weatherbit api that are no longer in use
    //private final String mKey = "f0a7f688802a43609edd1fc3a82260ca";
    //private final String key = "0721a87721a04175806802e3e8688271";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public ZipcodeWeatherFragment()
    {
        //Required empty public constructor
    }

    /**
     * Standard implementation of onCreate() for this Fragment.
     * @param savedInstanceState Parameter for the savedInstanceState using Bundle.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * Standard implementation of onCreateView() for this Fragment.
     * @param inflater Parameter for the LayoutInflater.
     * @param container Parameter for the container from ViewGroup.
     * @param savedInstanceState Parameter for the savedInstanceState using Bundle.
     * @return Returns the root for mBinding.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mBinding = FragmentZipcodeWeatherBinding.inflate(inflater);

        //Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    /**
     * Implementing onViewCreated() to initialize the buttons that
     * the user will be able to use to pick what type of Weather they would
     * like to see: current, hourly, or 5-day.
     * @param view Parameter for the View.
     * @param savedInstanceState Parameter for the savedInstanceState using Bundle.
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
        String zipCode = mBinding.currentZipcode.getText().toString();
        String country = mBinding.currentCountry.getText().toString();

        if (zipCode.equals(""))
        {
            weatherResult.setText("Zipcode field is empty.");
        }
        else
        {
            if (!country.equals(""))
            {
                tempURL = url + "weather" + "?zip=" + zipCode + "," + country
                        + "&appid=" + mKey + "&units=imperial";
            }
            else
            {
                tempURL = url + "weather" + "?zip=" + zipCode + "&appid=" + mKey
                        + "&units=imperial";
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
                    JSONArray jsonArray;
                    JSONObject jsonMain;
                    JSONObject jsonWind;
                    JSONObject jsonClouds;
                    JSONObject jsonSys;
                    double temp;
                    double feelsLike;
                    float pressure;
                    int humidity;
                    String wind;
                    String clouds;
                    String cityName;
                    String countryName;

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
                        jsonSys = jsonResponse.getJSONObject("sys");
                        cityName = jsonResponse.getString("name");
                        countryName = jsonSys.getString("country");
                        output += "Current Weather of " + cityName + " (" + countryName + ")"
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
     * This method getHourlyWeather() gets the hourly forecast using a
     * GET https request to obtain the hourly Weather information.
     * @param view Parameter for the View.
     */
    public void getHourlyWeather(final View view)
    {
        String tempURL = "";
        String zipcode = mBinding.currentZipcode.getText().toString();
        String country = mBinding.currentCountry.getText().toString();

        if (zipcode.equals(""))
        {
            weatherResult.setText("Zipcode field is empty.");
        }
        else
        {
            if (!country.equals(""))
            {
                tempURL = url + "forecast" + "?zip=" + zipcode + "," + country
                        + "&appid=" + mKey + "&units=imperial";
            }
            else
            {
                tempURL = url + "forecast" + "?zip=" + zipcode + "&appid=" + mKey
                        + "&units=imperial";
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
     * up to 5 days. GET request is made using the zipcode in this
     * case.
     * @param view Parameter for the View.
     */
    public void getFiveDayWeather(final View view)
    {
        String tempURL = "";
        String zipcode = mBinding.currentZipcode.getText().toString();
        String country = mBinding.currentCountry.getText().toString();

        if (zipcode.equals(""))
        {
            weatherResult.setText("Zipcode field is empty.");
        }
        else
        {
            if (!country.equals(""))
            {
                tempURL = url + "forecast" + "?zip=" + zipcode + "," + country
                        + "&appid=" + mKey + "&units=imperial";
            }
            else
            {
                tempURL = url + "forecast" + "?zip=" + zipcode + "&appid=" + mKey
                        + "&units=imperial";
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
