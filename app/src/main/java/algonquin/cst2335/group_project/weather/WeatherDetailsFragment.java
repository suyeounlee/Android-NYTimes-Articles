package algonquin.cst2335.group_project.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import algonquin.cst2335.group_project.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_project.databinding.WeatherDetailsLayoutBinding;

public class WeatherDetailsFragment extends Fragment {
    WeatherMessage selected;

    public WeatherDetailsFragment(WeatherMessage m){
        selected = m;
    }

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
            binding.detailsIcon.setImageResource(R.drawable.sunny);
        }else if(description.contains("cloud")){
            binding.detailsIcon.setImageResource(R.drawable.cloudy);
        }else if(description.contains("overcast")){
            binding.detailsIcon.setImageResource(R.drawable.cloudy);
        }else if(description.contains("wind")){
            binding.detailsIcon.setImageResource(R.drawable.windy);
        }else if(description.contains("mist")){
            binding.detailsIcon.setImageResource(R.drawable.mist);
        }else if(description.contains("clear")){
            binding.detailsIcon.setImageResource(R.drawable.clear);
        }else if(description.contains("snow")){
            binding.detailsIcon.setImageResource(R.drawable.snow);
        }else{
            binding.detailsIcon.setImageResource(R.drawable.unknown);
        }

        return binding.getRoot();
    }
}
