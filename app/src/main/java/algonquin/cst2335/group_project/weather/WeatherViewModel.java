package algonquin.cst2335.group_project.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class WeatherViewModel extends ViewModel {
    public MutableLiveData<ArrayList<WeatherMessage>> messages = new MutableLiveData< >();
    public MutableLiveData<WeatherMessage> selectedMessage = new MutableLiveData< >();
}
