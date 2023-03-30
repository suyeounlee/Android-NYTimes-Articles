package NewYorkTimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.group_project.R;
import algonquin.cst2335.group_project.databinding.ActivityNewYorkSecondBinding;
import algonquin.cst2335.group_project.databinding.ArticleDetailBinding;

public class NewYorkSecond extends AppCompatActivity {

    RequestQueue queue = null;
    TextView keyword = null;
    String stringURL = null;
    ActivityNewYorkSecondBinding binding;
    RecyclerView.Adapter<MyRowHolder> myAdapter;
    ArticleDetailBinding articleBinding;
    ArrayList<API_Items> apiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_new_york_second);

        Intent fromPrevious = getIntent();
        String searchKeyword = fromPrevious.getStringExtra("Keyword");

        binding = ActivityNewYorkSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        keyword = (TextView) findViewById(R.id.textView);
        keyword.setText("Your search keyword is: " + searchKeyword);

        queue = Volley.newRequestQueue(this);


        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override //It represents a single row in the list
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ArticleDetailBinding binding = ArticleDetailBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override //this initializes a ViewHolder to go at the row specified by the position parameter.
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                API_Items api = apiList.get(position);
                holder.pub_date.setText(api.getPub_date());
                holder.section_name.setText(api.getSection_name());
                holder.main.setText(api.getMain());
                holder.snippet.setText(api.getSnippet());
               // holder.image.setText(api.getImage());

            }


            @Override // This function just returns an int specifying how many items to draw.
            public int getItemCount() {
                return apiList.size();
            }

        }); //setAdapter

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView recyclerView = findViewById(R.id.recycleView);



    } //onCreate

    // MyRowHolder which will be an object for representing everything that goes on a row in the list.
    class MyRowHolder extends ViewHolder {

        TextView pub_date;
        TextView section_name;
        TextView main;
        TextView snippet;
        ImageView image;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            pub_date =itemView.findViewById(R.id.pub_date);
            section_name = itemView.findViewById(R.id.section_name);
            main = itemView.findViewById(R.id.main);
            snippet = itemView.findViewById(R.id.snippet);
            image = itemView.findViewById(R.id.image);


        } //MyRowHolder

    } //MyRowHolder


}; //newYorKSecond
