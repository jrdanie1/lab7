package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    EditText newName;
    LinearLayout nameField;
    LinearLayout showActivityLayout;
    TextView cityNameTextView;
    Button backButton;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Original MainActivity views
        nameField = findViewById(R.id.field_nameEntry);
        newName = findViewById(R.id.editText_name);
        cityList = findViewById(R.id.city_list);

        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> nameField.setVisibility(View.VISIBLE));

        Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(v -> {
            String cityName = newName.getText().toString();
            cityAdapter.add(cityName);
            newName.getText().clear();
            nameField.setVisibility(View.INVISIBLE);
        });

        Button deleteButton = findViewById(R.id.button_clear);
        deleteButton.setOnClickListener(v -> cityAdapter.clear());

        // 🔹 NEW: Embedded "ShowActivity" layout inside MainActivity
        showActivityLayout = findViewById(R.id.show_activity_layout);
        cityNameTextView = findViewById(R.id.textView_cityName);
        backButton = findViewById(R.id.button_back);

        // When a city is clicked, show the embedded layout
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = dataList.get(position);
            cityNameTextView.setText(cityName);
            showActivityLayout.setVisibility(View.VISIBLE);
        });

        // Handle back button to return to MainActivity UI
        backButton.setOnClickListener(v -> showActivityLayout.setVisibility(View.GONE));
    }
}
