
package edu.uw.tcss450.uwnetid.raindropapp.ui.weather;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentWeatherBinding;

/**
 *
 */
public class WeatherFragment extends Fragment
{
    private FragmentWeatherBinding mBinding;
    private EditText currentCity, currentCountry;
    private TextView weatherResult;
    private final String url = "https://api.weatherbit.io/v2.0/current";
    private final String appID = "0721a87721a04175806802e3e8688271";
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public WeatherFragment()
    {
        //Required empty public constructor
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        currentCity = getActivity().findViewById(R.id.current_city);
        currentCountry = getActivity().findViewById(R.id.current_country);
        weatherResult = getActivity().findViewById(R.id.weather_result);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mBinding = FragmentWeatherBinding.inflate(inflater);

        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mBinding.buttonSearchByCity.setOnClickListener(this::goToCityWeather);
        mBinding.buttonSearchByZipcode.setOnClickListener(this::goToZipcodeWeather);
        mBinding.buttonSearchByLocation.setOnClickListener(this::goToLocationWeather);
    }

    /**
     * @param view
     */
    public void goToCityWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_cityWeatherFragment);
    }

    public void goToZipcodeWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_zipcodeWeatherFragment);
    }

    public void goToLocationWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_locationWeatherFragment);
    }
}
