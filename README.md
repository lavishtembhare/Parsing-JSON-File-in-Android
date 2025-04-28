# JSON Parsing Android App(Get API) 📱

This is an Android app that demonstrates how to fetch and display JSON data from a remote server (GitHub in this case) using `HttpURLConnection` and `AsyncTask`. The app parses the JSON data and displays a list of user names in a `ListView`. 

## Features 🎉

- Fetch data from a remote JSON file hosted on GitHub.
- Parse and display user names in a `ListView`.
- Handle network errors gracefully with informative messages.
- Designed to be simple and easy to extend.

## Screenshots 📸

![Main Screen](https://github.com/lavishtembhare/Json-File/blob/main/Screenshot%20(44).png)

*Main Screen showing user names fetched from the remote JSON file.*

## Prerequisites ⚙️

Before you begin, ensure you have the following:

- **Android Studio** installed on your machine.
- An **Android device** or **emulator** to run the app.
- **Internet connection** since the app fetches data from a remote URL.

## Installation Instructions 🔧

1. **Clone this repository** to your local machine:

```bash
https://github.com/lavishtembhare/Parsing-JSON-File-in-Android.git

Open the project in Android Studio.

Build and run the project on your Android device or emulator.

Ensure that you have an active internet connection, as the app fetches data from a remote JSON file.

How to Create a GitHub Raw Link for JSON File 🌐
To use a raw JSON file from GitHub in your Android app, follow these steps:

Go to the repository where your JSON file is hosted (e.g., users.json).

Open the file in GitHub.

Click on the Raw button at the top of the file view.

Copy the URL from the browser's address bar. This URL is the raw link to your JSON file.

Example raw URL:
https://raw.githubusercontent.com/lavishtembhare/Parsing-Json-File/main/JSON-File/users.json

Files Structure 📂
MainActivity.java: Main activity that handles fetching and displaying the JSON data.

activity_main.xml: Layout file with a ListView to display user names.

AndroidManifest.xml: Declares necessary permissions such as INTERNET and the app metadata.

Code Explanation 💻
MainActivity.java
The main logic of the app resides in the MainActivity.java file:

AsyncTask: Handles fetching the JSON data from the remote server in the background (doInBackground) and updating the UI (onPostExecute) with the fetched data.

JSON Parsing: Uses JSONArray and JSONObject to parse the JSON response and extract the user names.

Network Operation: Makes an HTTP GET request using HttpURLConnection to fetch the JSON data.

activity_main.xml
This layout file contains a ListView that displays the fetched user names. The ListView dynamically updates as data is fetched from the remote server.
<ListView
    android:id="@+id/list_content"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

AndroidManifest.xml
In this file, we declare the necessary permissions and define the application structure. The INTERNET permission is necessary to fetch data from the remote server.
<uses-permission android:name="android.permission.INTERNET" />

Troubleshooting 🚧
Failed to Load Data: If the app fails to load data, check Logcat for error messages. Ensure the device has an active internet connection.

App Crashes: Verify that the raw URL is correct, and check for network-related issues.

Contributing 🤝
If you'd like to contribute to this project, feel free to fork the repository and submit a pull request with your changes. Please ensure to:

Follow the project’s coding standards.

Provide a detailed description of the changes made.

Test the changes before submitting.
