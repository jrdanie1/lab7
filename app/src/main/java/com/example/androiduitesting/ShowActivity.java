package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get references to views
        TextView cityNameTextView = findViewById(R.id.textView_cityName);
        Button backButton = findViewById(R.id.button_back);

        // Get city name from the intent
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city_name");
        if (cityName != null) {
            cityNameTextView.setText(cityName);
        }

        // Back button closes this activity and returns to MainActivity
        backButton.setOnClickListener(v -> finish());
    }
}
