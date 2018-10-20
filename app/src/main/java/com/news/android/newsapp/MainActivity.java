package com.news.android.newsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    int index;
    private String aboutTitle;
    private String aboutDescription;
    private String aboutPhoto;
    private String header;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    JSONObject byIndex;
    JSONArray article;
    ArrayList<HashMap<String, String>> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        try{
            Thread.sleep(1100);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetTitles().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position of selected JSON obj
                index = position;

                try {
                    //getting JSON objects by index
                    byIndex = article.getJSONObject(index);
                    //using index to fetch data
                    aboutTitle = byIndex.getString("title");
                    aboutDescription = byIndex.getString("description");
                    aboutPhoto = byIndex.getString("urlToImage");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                //sending data to About.class
                Intent in = new Intent(MainActivity.this, About.class);
                in.putExtra("title", aboutTitle);
                in.putExtra("description", aboutDescription);
                in.putExtra("photo", aboutPhoto);
                startActivity(in);
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    private class GetTitles extends AsyncTask<Void, Void, Void> {

        @Override
        //Loading app before you say cookie
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        //doing some progress in background
        protected Void doInBackground(Void... arg0) {
            URLconnection urlConn = new URLconnection();
            // Making a request to url and getting response
            String url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=e402c76fc8584a1c81849179f1277a74";
            //.........connection.........
            String response = urlConn.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + response);
            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    // Getting JSON Array node
                    article = jsonObj.getJSONArray("articles");

                    // looping through articles
                    for (int i = 0; i < article.length(); i++) {

                        JSONObject c = article.getJSONObject(i);
                        header = c.getString("title");

                        // news hash map
                        HashMap<String, String> news = new HashMap<>();
                        // adding each child node to HashMap key => value
                        news.put("naslov", header);

                        // adding titles to titleList
                        titleList.add(news);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Ups..Something went wrong...Check your internet connection or restart your app.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, titleList, R.layout.list_item, new String[]{ "naslov"}, new int[]{R.id.title});
            lv.setAdapter(adapter);
            Toast.makeText(MainActivity.this,"Your news are ready.",Toast.LENGTH_LONG).show();
        }
}

}
