package algonquin.cst2335.group_project.nytimes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_project.R;

import algonquin.cst2335.group_project.databinding.ActivityNytimesBinding;
import algonquin.cst2335.group_project.databinding.NewyorkArticleBinding;
import algonquin.cst2335.group_project.kitten.KittenActivity;
import algonquin.cst2335.group_project.nasa.NasaActivity;
import algonquin.cst2335.group_project.weather.WeatherActivity;


/**
 * NYTimesActivity is the main class responsible for managing the NY Times article search and
 * display, as well as handling user interactions related to API data.
 *
 * @author Su Yeoun Lee
 */
public class NYTimesActivity extends AppCompatActivity {

    private ActivityNytimesBinding binding;
    protected String currentKeyword;
    protected RequestQueue queue = null;
    private RecyclerView.Adapter myAdapter;
    ArrayList<API_Items> apiList;
    API_ItemsDAO apiDao;
    NYT_ViewModel viewModel;
    int position;
    AlertDialog.Builder builder;

    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * Overrides onCreateOptionsMenu method to inflate the options menu for this activity
     *
     * @param menu the menu to be inflated
     * @return true when options menu is inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.nyt_menu, menu);
        return true;
    }

    /**
     * Overrides onOptionsItemSelected method to handle item selections from options menu
     *
     * @param item the item that was selected
     * @return true when the item is handled
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.add:
                builder = new AlertDialog.Builder(NYTimesActivity.this);
                builder.setMessage("Do you want to add this article to your list? ")
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {

                            API_Items Article = viewModel.selectedArticle.getValue();

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                apiDao.insertData(Article);
                                runOnUiThread(() -> Toast.makeText(NYTimesActivity.this, "Article added successfully!", Toast.LENGTH_SHORT).show());
                            });
                        }).create().show();

                break;

            case R.id.list:
                apiList.clear(); // clear list

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    apiList.addAll(apiDao.getAllData()); //Once you get the data from database

                    runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView
                });
                break;

            case R.id.delete:

                int position = apiList.indexOf(viewModel.selectedArticle.getValue());

                builder = new AlertDialog.Builder(NYTimesActivity.this);
                builder.setTitle("Question:")
                        .setMessage("Do you want to delete the article?")
                        .setNegativeButton("No", (dialog, cl) -> {
                        })
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Executor deleteThread = Executors.newSingleThreadExecutor();
                            API_Items items = apiList.get(position);

                            deleteThread.execute(() ->
                            {
                                apiDao.deleteData(items);
                            });
                            apiList.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(findViewById(R.id.delete), "You deleted the article #: " + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", click -> {
                                        apiList.add(position, items);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .create().show();
                break;

            case R.id.help:
                builder = new AlertDialog.Builder(NYTimesActivity.this);
                builder.setMessage("To use this app, please follow these steps:\n" +
                                "\n" +
                                "(1) Enter a keyword for the article you want to view.\n" +
                                "(2) Click the 'Search' button.\n" +
                                "(3) Click on the article you are interested in to view more details and add it to your list.\n" +
                                "(4) If you want to delete a saved article, click the garbage icon in the toolbar.")
                        .create().show();
                break;


            case R.id.weather:

                startActivity(new Intent(NYTimesActivity.this, WeatherActivity.class));
                break;

            case R.id.kitten:

                startActivity(new Intent(NYTimesActivity.this, KittenActivity.class));
                break;

            case R.id.nasa:

                startActivity(new Intent(NYTimesActivity.this, NasaActivity.class));
                break;

        }

        return true;
    }

    /**
     * Overrides onCreate method to initialize and set up the activity and its components
     *
     * @param savedInstanceState the saved instance state of the activity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.newyork_times);

        binding = ActivityNytimesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NYT_ViewModel.class);
        apiList = viewModel.items.getValue();

        API_Database db = Room.databaseBuilder(getApplicationContext(), API_Database.class, "nyt_db").build();
        apiDao = db.apiDAO();

        queue = Volley.newRequestQueue(this);

        viewModel.selectedArticle.observe(this, (newValue) -> {
            DataDetailsFragment nyFragment = new DataDetailsFragment(newValue);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, nyFragment)
                    .addToBackStack("")
                    .commit();
        });

        setSupportActionBar(binding.myToolbar);

        /** MyData" is the name of the file that will be opened for saving,
         * and the Context.MODE_PRIVATE means that only the application that created the file can open it. */
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        /** save the keyword that is typed in to the EditText. */
        SharedPreferences.Editor editor = prefs.edit();

        /** to see if anything is saved using a variable name */
        String keyword = prefs.getString("Keyword", "");
        binding.editText.setText(keyword);

        /** so that the RecyclerView show the data after the database has loaded it: */
        if (apiList == null) {
            viewModel.items.setValue(apiList = new ArrayList<>());
            /**The execute() function calls the run() function of a Runnable on another thread. This is where you should run your database query and add all of the objects returned to your apiList ArrayList */
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                apiList.addAll(apiDao.getAllData()); //Once you get the data from database
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView
            });
        }

        binding.button.setOnClickListener(clk -> {
            apiList.clear(); // clear previous list items
            currentKeyword = binding.editText.getText().toString();

            /** Set the currentKeyword to the new keyword from the ArrayList
             * currentKeyword = apiString.get(apiString.size() - 1); */
            editor.putString("Keyword", currentKeyword);
            editor.apply();

            Toast.makeText(this, "Search button clicked: " + currentKeyword, Toast.LENGTH_SHORT).show();

            if (myAdapter != null) {
                myAdapter.notifyItemInserted(apiList.size() - 1);
            }
            // binding.editText.setText(""); // clear the EditText

            String stringURL = "";
            try {
                stringURL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
                        + URLEncoder.encode(currentKeyword, "UTF-8")
                        + "&api-key=ktsLppXjo1vaophZGkHslluJ19wUkI6m";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {

                            try {

                                JSONObject res = response.getJSONObject("response");
                                JSONArray docsArray = res.getJSONArray("docs");

                                for (int i = 0; i < docsArray.length(); i++) {
                                    JSONObject position0 = docsArray.getJSONObject(i);

                                    Log.i("NYTimesActivity", "docsArray: " + docsArray);

                                    String pub_date = position0.getString("pub_date");
                                    Log.i("NYTimesActivity", "pub_date: " + pub_date);

                                    String section_name = position0.getString("section_name");
                                    Log.i("NYTimesActivity", "section_name: " + section_name);

                                    String webUrl = position0.getString("web_url");
                                    Log.i("NYTimesActivity", "webUrl: " + webUrl);

                                    String snippet = position0.getString("snippet");
                                    Log.i("NYTimesActivity", "snippet: " + snippet);

                                    JSONObject position1 = position0.getJSONObject("headline");
                                    String headline = position1.getString("main");
                                    Log.i("NYTimesActivity", "headline: " + headline);

                                    apiList.add(new API_Items(pub_date, section_name, headline, snippet, webUrl));

                                } //for

                                myAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }, //JsonObjectRequest (response)
                        (error) -> {
                        }

                ); //JsonObjectRequest
                queue.add(request);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
            binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

                /** creates a ViewHolder object
                 This is responsible for creating a layout for a row, and setting the TextViews in code. */
                @NonNull
                @Override
                public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    NewyorkArticleBinding binding = NewyorkArticleBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }

                /** initializes a ViewHolder to go at the row specified by the position parameter.
                 * where you set the objects in your layout for the row */
                @Override
                public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                    API_Items apiItems = apiList.get(position);

                    /** parse the pub_date string into a Date object */
                    try {
                        Date date = inputFormat.parse(apiItems.getPub_date());
                        /** format the Date object into a string with the desired format */
                        String formattedDate = outputFormat.format(date);
                        holder.pub_date.setText(formattedDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        holder.pub_date.setText(apiItems.getPub_date());
                    }

                    holder.headline.setText(apiItems.getHeadline());
                    holder.snippet.setText(apiItems.getSnippet());
                }

                /** This function just returns an int specifying how many items to draw.
                 * return the number of rows in the list */
                @Override
                public int getItemCount() {
                    return apiList.size();
                } //getItemCount

            }); //setAdapter
        }); //setOnClickListener

    } //onCreate

    /**
     * The whole point of the MyRowHolder class is to maintain variables for what you want to set on each row in your list.
     * for representing everything that goes on a row in the list.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView pub_date;
        TextView headline;
        TextView snippet;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAdapterPosition();
                API_Items selected = apiList.get(position);
                viewModel.selectedArticle.postValue(selected);
            });

            pub_date = itemView.findViewById(R.id.pub_date);
            headline = itemView.findViewById(R.id.headline);
            snippet = itemView.findViewById(R.id.snippet);
        } //MyRowHolder innerClass
    } //MyRowHolder

}//MainActivity



