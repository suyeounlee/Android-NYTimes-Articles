package NewYorkTimes;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.group_project.databinding.NewyorkMainBinding;


public class NewYorkMain extends AppCompatActivity {

    private NewyorkMainBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.newyork_times);

        binding = NewyorkMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        binding.button.setOnClickListener(clk -> {
            Toast.makeText(this, "Search button clicked", Toast.LENGTH_SHORT).show();
        });

        //MyData" is the name of the file that will be opened for saving,
        // and the Context.MODE_PRIVATE means that only the application that created the file can open it.
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        //save the keyword that is typed in to the EditText
        SharedPreferences.Editor editor = prefs.edit();

        //to see if anything is saved using a variable name
        String keyword = prefs.getString("Keyword", "");
        binding.editText.setText(keyword);

        binding.button.setOnClickListener(clk-> {

            editor.putString("Keyword", binding.editText.getText().toString());
            editor.apply();

            Intent nextPage = new Intent(NewYorkMain.this, NewYorkSecond.class);
            nextPage.putExtra("Keyword", binding.editText.getText().toString());
            startActivity(nextPage);



//        try {
//            stringURL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
//                    + URLEncoder.encode(searchKeyword, "UTF-8")
//                    + "&api-key=ktsLppXjo1vaophZGkHslluJ19wUkI6m&Units=Metric";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
//                response -> {
//                    try {
//                        JSONArray docs = response.getJSONArray("docs");
//                        for (int i = 0; i < docs.length() - 1; i++) {
//                            JSONObject doc = docs.getJSONObject(i);
//                            String url = String.valueOf(doc.getJSONObject("web_url"));
//                            String headline = String.valueOf(doc.getJSONArray("headline").getJSONObject(Integer.parseInt("main")));
//                            String date = String.valueOf(doc.getJSONObject("pub_date"));
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> {
//                });
//        queue.add(request);


        });



    } //oncreate


} //newyork
