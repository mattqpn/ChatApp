package edu.uw.tcss450.uwnetid.raindropapp.ui.weather;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

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

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentWeatherBinding;

/**
 *
 */
public class WeatherFragment extends Fragment
{
    private FragmentWeatherBinding binding;
    EditText currentCity, currentCountry;
    TextView weatherResult;
    private final String url = "https://api.weatherbit.io/v2.0/current";
    private final String appID = "0721a87721a04175806802e3e8688271";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        currentCity = currentCity.findViewById(R.id.currentCity);
        currentCountry = currentCountry.findViewById(R.id.currentCountry);
        weatherResult = weatherResult.findViewById(R.id.weatherResult);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentWeatherBinding.inflate(inflater);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    public void getCurrentWeather(View view)
    {
        String tempURL = "";
        String city = currentCity.getText().toString();
        String zipCode = currentCountry.getText().toString();
        if (city.equals(""))
        {
            weatherResult.setText("City field is empty.");
        }
        else
        {
            if (!zipCode.equals(""))
            {
                tempURL = url + "?q=" + city + "," + zipCode + "&appID=" + appID;
            }
            else
            {
                tempURL = url + "?q=" + city + "&appID=" + appID;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    tempURL, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Log.d("Response", response);
                    String output = "";
                    JSONObject jsonResponse;
                    JSONArray jsonArray;
                    JSONObject jsonWeather;
                    String description;
                    JSONObject jsonMain;
                    double temp;
                    double feelsLike;
                    float pressure;
                    int humidity;
                    JSONObject jsonWind;
                    String wind;
                    JSONObject jsonClouds;
                    String clouds;
                    JSONObject jsonSys;
                    String countryName;
                    String cityName;

                    try
                    {
                        jsonResponse = new JSONObject(response);
                        jsonArray = jsonResponse.getJSONArray("weather");
                        jsonWeather = jsonArray.getJSONObject(0);
                        description = jsonWeather.getString("description");
                        jsonMain = jsonResponse.getJSONObject("main");
                        temp = jsonMain.getDouble("temp") - 273.15;
                        feelsLike = jsonMain.getDouble("feels_like") - 273.15;
                        pressure = jsonMain.getInt("pressure");
                        humidity = jsonMain.getInt("humidity");
                        jsonWind = jsonResponse.getJSONObject("wind");
                        wind = jsonWind.getString("speed");
                        jsonClouds = jsonResponse.getJSONObject("clouds");
                        clouds = jsonClouds.getString("all");
                        jsonSys = jsonResponse.getJSONObject("sys");
                        countryName = jsonSys.getString("country");
                        cityName = jsonResponse.getString("name");
                        weatherResult.setTextColor(Color.rgb(68, 134, 199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\nTemp: " + decimalFormat.format(temp) + " °C"
                                + "\nFeels Like: "  + decimalFormat.format(feelsLike) + " °C"
                                + "\nHumidity: " + humidity + "%"
                                + "\nDescription: " + description
                                + "\nWind Speed: " + wind + "m/s (meters per second)"
                                + "\nCloudiness: " + clouds + "%"
                                + "\nPressure: " + pressure + " hPa";
                        weatherResult.setText(output);
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