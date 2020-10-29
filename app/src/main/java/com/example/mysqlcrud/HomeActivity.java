package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView tvUserName;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        number = getIntent().getStringExtra("number");
        tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(number);
    }
}