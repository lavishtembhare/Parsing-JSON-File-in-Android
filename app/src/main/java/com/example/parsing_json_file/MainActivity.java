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

    // Declare ListView to display user names
    private ListView listView;

    // Define constant for logging
    private static final String TAG = "MainActivity";

    // Define the URL for the JSON data (raw GitHub link)
    private static final String JSON_URL = "https://raw.githubusercontent.com/lavishtembhare/Json-File/refs/heads/main/JSON-File/users.json?token=GHSAT0AAAAAADCSYILWW7DMAYUEVY2AJY6A2AMXCUQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for the activity

        // Initialize ListView to display the data
        listView = findViewById(R.id.list_content);

        // Start fetching user data in the background using AsyncTask
        new FetchUserDataTask().execute(JSON_URL);
    }

    // AsyncTask to fetch and parse the JSON data in the background
    private class FetchUserDataTask extends AsyncTask<String, Void, ArrayList<String>> {

        // This method runs in the background and fetches the data from the URL
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            ArrayList<String> userNames = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Open a connection to the provided URL
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");  // Set HTTP method to GET
                connection.connect();

                // Check if the response is OK (HTTP 200)
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "HTTP error code: " + responseCode); // Log error if response code is not OK
                    return userNames; // Return empty list in case of error
                }

                // Read the response from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);  // Append each line of the response
                }

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);  // Get each user object
                    String name = obj.getString("name");  // Extract the name
                    userNames.add(name);  // Add the name to the list
                    Log.d(TAG, "Parsed Name: " + name); // Log the parsed name
                }

            } catch (Exception e) {
                Log.e(TAG, "Error fetching data", e); // Log any exceptions that occur
            } finally {
                // Clean up: disconnect the connection and close the reader
                if (connection != null) connection.disconnect();
                try {
                    if (reader != null) reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing reader", e); // Log error if reader fails to close
                }
            }
            return userNames;  // Return the list of user names
        }

        // This method runs on the main UI thread and updates the UI with the result
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);

            // If the result is empty, show a toast message indicating failure
            if (result.isEmpty()) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, update the ListView with the fetched user names
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,  // Layout for each list item
                        result  // Data to display (user names)
                );
                listView.setAdapter(arrayAdapter);  // Set the adapter to the ListView
            }
        }
    }
}
