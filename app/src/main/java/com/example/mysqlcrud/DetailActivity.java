package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    int position;
    TextView tvID, tvEMAIL, tvNAME, tvCOUNTRY, tvMOBILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvID = findViewById(R.id.tvID);
        tvMOBILE = findViewById(R.id.tvMOBILE);
        tvEMAIL = findViewById(R.id.tvEMAIL);
        tvNAME = findViewById(R.id.tvNAME);
        tvCOUNTRY = findViewById(R.id.tvCOUNTRY);

        Intent mIntent = getIntent();
        position = mIntent.getIntExtra("position", 100000);

        tvID.setText("ID: " + ShowUsersActivity.arrayListUser.get(position).getId());
        tvEMAIL.setText("Email: " + ShowUsersActivity.arrayListUser.get(position).getEmail());
        tvNAME.setText("Name: " + ShowUsersActivity.arrayListUser.get(position).getName());
        tvCOUNTRY.setText("Country: " + ShowUsersActivity.arrayListUser.get(position).getCountry());
        tvMOBILE.setText("Mobile: " + ShowUsersActivity.arrayListUser.get(position).getMobile());
    }
}