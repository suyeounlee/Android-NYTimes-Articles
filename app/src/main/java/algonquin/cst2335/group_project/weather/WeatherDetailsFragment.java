package algonquin.cst2335.group_project.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import algonquin.cst2335.group_project.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_project.databinding.WeatherDetailsLayoutBinding;

/**
 * WeatherDetailsFragment is a class that extends Fragment and is responsible for
 * displaying the weather details of a selected WeatherMessage.
 * This class is part of the Group Project package related to displaying weather information.
 * @author Shing Kwan Chow
 */
public class WeatherDetailsFragment extends Fragment {
    WeatherMessage selected;

    /**
     * Constructs a WeatherDetailsFragment with the given WeatherMessage object.
     * @param m The WeatherMessage object containing the weather details to display.
     */
    public WeatherDetailsFragment(WeatherMessage m){
        selected = m;
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate the views.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        WeatherDetailsLayoutBinding binding = WeatherDetailsLayoutBinding.inflate(inflater);
        binding.timeDetails.setText(selected.getTimeRetrieved());
        binding.cityDetails.setText(selected.getCity());
        binding.tempDetails.setText(selected.getTemperature() + "°C");
        binding.humidityDetails.setText("Humidity: "+selected.getHumidity() + "%");
        binding.descriptionDetails.setText(selected.getWeather_descriptions());

        String description = selected.getWeather_descriptions().toLowerCase();
        if(description.contains("sunny")){
            binding.detailsIcon.setImageResource(R.drawable.weather_sunny);
        }else if(description.contains("cloud")){
            binding.detailsIcon.setImageResource(R.drawable.weather_cloudy);
        }else if(description.contains("overcast")){
            binding.detailsIcon.setImageResource(R.drawable.weather_cloudy);
        }else if(description.contains("wind")){
            binding.detailsIcon.setImageResource(R.drawable.weather_windy);
        }else if(description.contains("mist")){
            binding.detailsIcon.setImageResource(R.drawable.weather_mist);
        }else if(description.contains("clear")){
            binding.detailsIcon.setImageResource(R.drawable.weather_clear);
        }else if(description.contains("snow")){
            binding.detailsIcon.setImageResource(R.drawable.weather_snow);
        }else{
            binding.detailsIcon.setImageResource(R.drawable.weather_unknown);
        }

        return binding.getRoot();
    }
}
