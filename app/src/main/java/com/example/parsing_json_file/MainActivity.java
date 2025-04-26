package com.example.parsing_json_file;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app16.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final String TAG = "MainActivity";
    private static final String JSON_URL = "https://raw.githubusercontent.com/lavishtembhare/Json-File/refs/heads/main/JSON-File/users.json?token=GHSAT0AAAAAADCSYILXXL6C23VCMLXZ4KPW2AMVVCQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ✅ No need to prefix with package

        listView = findViewById(R.id.list_content); // ✅ No need to prefix with package

        // Start fetching user data
        new FetchUserDataTask().execute(JSON_URL);
    }

    private class FetchUserDataTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            ArrayList<String> userNames = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "HTTP error code: " + responseCode);
                    return userNames;
                }

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String name = obj.getString("name");
                    userNames.add(name);
                    Log.d(TAG, "Parsed Name: " + name);
                }

            } catch (Exception e) {
                Log.e(TAG, "Error fetching data", e);
            } finally {
                if (connection != null) connection.disconnect();
                try {
                    if (reader != null) reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing reader", e);
                }
            }
            return userNames;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);

            if (result.isEmpty()) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            } else {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        result
                );
                listView.setAdapter(arrayAdapter);
            }
        }
    }
}
