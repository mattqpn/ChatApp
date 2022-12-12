
package edu.uw.tcss450.uwnetid.raindropapp.ui.weather;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * This Fragment is for the core of the Weather fragment. It contains buttons
 * that the user can use to choose how they would like to obtain Weather
 * information. The buttons are as the follows:
 * Search by City, Search by Zipcode, and Search by Location.
 * Search by Location is the only different searching method as it actually
 * uses the user's GPS location to obtain the Weather information.
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
     * @param savedInstanceState Parameter for savedInstanceState using Bundle.
     * @return Returns the root for mBinding.
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
     * Implementing onViewCreated() to initialize the buttons that
     * the user will be able to use to pick how they want to search
     * for the Weather.
     * @param view Parameter for the View.
     * @param savedInstanceState Parameter for savedInstanceState using Bundle.
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
     * This method uses the nav graph to move the window to the Search by City
     * fragment.
     * @param view Parameter for the View.
     */
    public void goToCityWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_cityWeatherFragment);
    }

    /**
     * This method uses the nav graph to move the window to the Search by Zipcode
     * fragment.
     * @param view Parameter for the View.
     */
    public void goToZipcodeWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_zipcodeWeatherFragment);
    }

    /**
     * This method uses the nav graph to move the window to the Search by Location
     * fragment.
     * @param view Parameter for the View.
     */
    public void goToLocationWeather(final View view)
    {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_weather_to_locationWeatherFragment);
    }
}
