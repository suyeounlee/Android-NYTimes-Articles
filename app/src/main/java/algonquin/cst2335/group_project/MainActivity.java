package algonquin.cst2335.group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import algonquin.cst2335.group_project.kitten.KittenActivity;
import algonquin.cst2335.group_project.nasa.NasaActivity;
import algonquin.cst2335.group_project.nytimes.NYTimesActivity;
import algonquin.cst2335.group_project.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOne = findViewById(R.id.nasa_app);
        Button buttonTwo = findViewById(R.id.kitten_app);
        Button buttonThree = findViewById(R.id.nytime_app);
        Button buttonFour = findViewById(R.id.weather_app);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NasaActivity.class));
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KittenActivity.class));
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NYTimesActivity.class));
            }
        });

        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
            }
        });
    }
}