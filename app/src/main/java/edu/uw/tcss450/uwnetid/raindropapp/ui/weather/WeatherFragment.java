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
    private final String mKey = "f0a7f688802a43609edd1fc3a82260ca";
    //private final String key = "0721a87721a04175806802e3e8688271";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public WeatherFragment()
    {
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentWeatherBinding.inflate(inflater);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonEnter.setOnClickListener(this::getCurrentWeather);
    }

    /**
     *
     * @param view
     */
    public void getCurrentWeather(final View view)
    {
        String tempURL = "";
        String city = binding.currentCity.getText().toString();
        String country = binding.currentCountry.getText().toString();

        if (city.equals(""))
        {
            weatherResult.setText("City field is empty.");
        }
        else
        {
            if (!country.equals(""))
            {
                tempURL = url + "?city=" + city + "&country=" + country + "&key=" +
                        mKey;
            }
            else
            {
                tempURL = url + "?city=" + city + "&key=" + mKey;
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
                    double temp;
                    int humidity;
                    String wind;
                    String clouds;
                    String countryName;
                    String cityName;

                    try
                    {
                        jsonResponse = new JSONObject(response);
                        jsonArray = jsonResponse.getJSONArray("data");
                        jsonMain = jsonArray.getJSONObject(0);
                        jsonWeather = jsonMain.getJSONObject("weather");
                        description = jsonWeather.getString("description");
                        temp = (jsonMain.getDouble("app_temp") * 1.8) + 32;
                        humidity = jsonMain.getInt("rh");
                        wind = jsonMain.getString("wind_spd");
                        clouds = jsonMain.getString("clouds");
                        countryName = jsonMain.getString("country_code");
                        cityName = jsonMain.getString("city_name");
                        binding.weatherResult.setTextColor(Color.rgb(68, 134, 199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\nTemp: " + decimalFormat.format(temp) + " °F"
                                + "\nHumidity: " + humidity + "%"
                                + "\nDescription: " + description
                                + "\nWind Speed: " + wind + "m/s (meters per second)"
                                + "\nCloudiness: " + clouds + "%";
                        binding.weatherResult.setText(output);
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