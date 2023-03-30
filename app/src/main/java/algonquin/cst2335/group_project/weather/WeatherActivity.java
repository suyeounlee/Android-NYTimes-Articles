package algonquin.cst2335.group_project.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.group_project.R;
import algonquin.cst2335.group_project.databinding.ActivityWeatherBinding;
import algonquin.cst2335.group_project.databinding.WeatherSearchHistoryBinding;

public class WeatherActivity extends AppCompatActivity {
    ActivityWeatherBinding binding;
    WeatherViewModel weatherModel;
    ArrayList<WeatherMessage> messages = new ArrayList<>();
    WeatherDAO mDAO;
    private RecyclerView.Adapter myAdapter;
    protected RequestQueue queue = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch( item.getItemId() )
        {
            case R.id.item_1:

                AlertDialog.Builder builder = new AlertDialog.Builder( WeatherActivity.this );
                builder.setTitle("How to use this app:")
                        .setMessage("(1) Get real time city weather info, input the city name in the text field and press the 'Search' button. \n\n" +
                                "(2) To save weather info you got, Press the 'Save Search Record' button. \n\n" +
                                "(3) To delete or review the saved details, click on the record you saved at the bottom. \n\n")
                        .setNegativeButton("Ok",(dialog, cl) ->{

                        })
                        .create().show();
                break;

        }
        return true;
    }

    private void saveLastSearchedCity(String cityName) {
        SharedPreferences sharedPreferences = getSharedPreferences("last_searched_city", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("city", cityName);
        editor.apply();
    }

    private String loadLastSearchedCity() {
        SharedPreferences sharedPreferences = getSharedPreferences("last_searched_city", MODE_PRIVATE);
        return sharedPreferences.getString("city", "");
    }

    private void getWeatherIcon(String description){
        description = description.toLowerCase();
        if(description.contains("sunny")){
            binding.imageView.setImageResource(R.drawable.sunny);
        }else if(description.contains("cloud")){
            binding.imageView.setImageResource(R.drawable.cloudy);
        }else if(description.contains("overcast")){
            binding.imageView.setImageResource(R.drawable.cloudy);
        }else if(description.contains("wind")){
            binding.imageView.setImageResource(R.drawable.windy);
        }else if(description.contains("mist")){
            binding.imageView.setImageResource(R.drawable.mist);
        }else if(description.contains("clear")){
            binding.imageView.setImageResource(R.drawable.clear);
        }else if(description.contains("snow")){
            binding.imageView.setImageResource(R.drawable.snow);
        }else{
            binding.imageView.setImageResource(R.drawable.unknown);
        }
    }

    private void fetchWeatherData(String cityName) {

        String apiKey = "749a132a1ed8ac34586506dd92981ed8";
        String url = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + cityName;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, (response) -> {
                    try {
                        JSONObject location = response.getJSONObject("location");
                        JSONObject current = response.getJSONObject("current");
                        JSONArray weatherDescriptions = current.getJSONArray("weather_descriptions");

                        String cityNameReal = location.getString("name");
                        int temperature = current.getInt("temperature");
                        String dateTime = location.getString("localtime");
                        String description = weatherDescriptions.getString(0);
                        int humidity = current.getInt("humidity");

                        binding.cityName.setText(cityNameReal);
                        binding.temperature.setText(temperature + "°C");
                        binding.dateTime.setText(dateTime);
                        binding.description.setText(description);
                        binding.humidity.setText("Humidity: " + humidity + "%");
                        getWeatherIcon(description);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Connection Problem. Make sure that you provided a correct city name.", Toast.LENGTH_SHORT).show();
                    }
                }, (error) -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
                });
        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        weatherModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        messages = weatherModel.messages.getValue();
        WeatherDatabase db = Room.databaseBuilder(getApplicationContext(), WeatherDatabase.class, "WeatherMessage").build();
        mDAO = db.cmDAO();

        setSupportActionBar(binding.myToolbar);

        if(messages == null)
        {
            weatherModel.messages.setValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        binding.searchCityButton.setOnClickListener(clk->{
            String cityName = binding.textInput.getText().toString();
            if (!cityName.isEmpty()) {
                fetchWeatherData(cityName);
                saveLastSearchedCity(cityName);
            }else{
                Toast.makeText(getApplicationContext(), "Please input a city name!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.saveCityButton.setOnClickListener(clk->{
            if(binding.cityName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Please input a city name!", Toast.LENGTH_LONG).show();
            }else{
                String humidityText = binding.humidity.getText().toString();
                String humidityNumber = humidityText.replace("Humidity: ", "").replace("%", "").trim();
                double humidity = Double.parseDouble(humidityNumber);
                String temperatureText = binding.temperature.getText().toString();
                String temperatureNumber = temperatureText.replace("°C", "").trim();
                double temperature = Double.parseDouble(temperatureNumber);

                WeatherMessage message = new WeatherMessage(binding.cityName.getText().toString(), binding.description.getText().toString(),
                        temperature, binding.dateTime.getText().toString(), humidity);
                messages.add(message);
                myAdapter.notifyItemInserted(messages.size()-1);
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    mDAO.insertMessage(message);
                });
                Toast.makeText(getApplicationContext(), "Weather info saved", Toast.LENGTH_LONG).show();
            }

        });

        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView cityViewHolder;
            TextView timeViewHolder;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                itemView.setOnClickListener(clk->{
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder( WeatherActivity.this );
                    builder.setTitle("Question:")
                            .setMessage("What do you want to do with this record?")
                            .setNegativeButton("Delete it",(dialog, cl) ->{
                                Executor thread = Executors.newSingleThreadExecutor();
                                WeatherMessage m = messages.get(position);
                                thread.execute(() ->
                                {
                                    mDAO.deleteMessage(m);
                                });
                                messages.remove(position);
                                myAdapter.notifyItemRemoved(position);
                                Snackbar.make(cityViewHolder,"You deleted the record #"+ position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo",click ->{
                                            messages.add(position, m);
                                            myAdapter.notifyItemInserted(position);
                                        })
                                        .show();
                            })
                            .setPositiveButton("Load it",(dialog, cl) -> {
                                WeatherMessage selected = messages.get(position);
                                weatherModel.selectedMessage.postValue(selected);

                            })
                            .create().show();
                });

                cityViewHolder = itemView.findViewById(R.id.cityViewHold);
                timeViewHolder = itemView.findViewById(R.id.timeViewHold);
            }
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                WeatherSearchHistoryBinding binding = WeatherSearchHistoryBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder  holder, int position) {
                WeatherMessage obj = messages.get(position);
                holder.cityViewHolder.setText(obj.getCity());
                holder.timeViewHolder.setText(obj.getTimeRetrieved());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        });

        weatherModel.selectedMessage.observe(this, (newMessageValue) -> {
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            WeatherDetailsFragment chatFragment = new WeatherDetailsFragment( newMessageValue );
            tx.replace(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack("");
            tx.commit();
        });

        String lastSearchedCity = loadLastSearchedCity();
        if (!lastSearchedCity.isEmpty()) {
            binding.textInput.setText(lastSearchedCity);
        }
    }
}
