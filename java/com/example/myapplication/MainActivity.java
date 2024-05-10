package com.example.myapplication;

// Imported
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// HTTP Request
import java.net.HttpURLConnection;
import java.net.URL;

// Required
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTest();
                setText("Wait...");
            }
        });

        // Making http request
        startTest();
    }

    public void startTest() {
        new Thread(() -> {
            //String error;
            // Update your view elements here
            try {
                Boolean isTrue = head("https://www.google.com");
                setText("Working!");
            } catch (Exception e) {
                setText(e.toString());
            }
        }).start();
    }

    public void setText(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView myTextView = findViewById(R.id.text);
                myTextView.setText(text);
            }
        });
    }

    public static Boolean head(String urlString) throws Exception {
        // URL string ko define karein
        // String urlString = "https://example.com/";

        // URL object banaen
        URL url = new URL(urlString);

        // HttpURLConnection object banaen
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Request method ko GET par set karein
        connection.setRequestMethod("HEAD");

        // Response code prapt karein
        int responseCode = connection.getResponseCode();

        // Connection ko band karein
        connection.disconnect();

        // Agar request safal ho to "true" return hoga
        return responseCode == HttpURLConnection.HTTP_OK;
    }
}