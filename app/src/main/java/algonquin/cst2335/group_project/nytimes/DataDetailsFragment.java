package algonquin.cst2335.group_project.nytimes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_project.databinding.NewyorkDetailsLayoutBinding;

/**
 * A DataDetailsFragment class is for to display of the details of a selected article.
 *
 * @author Su Yeoun Lee
 */
public class DataDetailsFragment extends Fragment {

    API_Items selected;

    /**
     * Default constructor for the DataDetailsFragment.
     */
    public DataDetailsFragment() {

    }

    /**
     * Constructor for the DataDetailsFragment that takes an API article as a parameter.
     *
     * @param a the API article to be displayed
     */
    public DataDetailsFragment(API_Items a) {
        selected = a;
    }


    /**
     * Inflates the view for the fragment and displays the details of the selected API article.
     *
     * @param inflater           the LayoutInflater object that can be used to inflate the views in the fragment
     * @param container          the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved state of the fragment, or null if there is none
     * @return the view for the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        NewyorkDetailsLayoutBinding binding = NewyorkDetailsLayoutBinding.inflate(inflater);

        binding.pubDateFg.setText(selected.getPub_date());
        binding.sectionNameFg.setText(selected.getSection_name());
        binding.headlineFg.setText(selected.getHeadline());
        binding.urlFg.setText(selected.getUrl());
        binding.snippetFg.setText(selected.getSnippet());

        return binding.getRoot();
    }
}
