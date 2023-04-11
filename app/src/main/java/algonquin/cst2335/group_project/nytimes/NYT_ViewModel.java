package algonquin.cst2335.group_project.nytimes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


/**
 * A NYT_ViewModel class for storing and managing data related to the New York Times API items.
 * It includes MutableLiveData objects for storing an ArrayList of API_Items and a single selected API_Item.
 */
public class NYT_ViewModel extends ViewModel {

    public MutableLiveData<ArrayList<API_Items>> items = new MutableLiveData<>();
    public MutableLiveData<API_Items> selectedArticle = new MutableLiveData<>();

}
