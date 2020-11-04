package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateDataActivity extends AppCompatActivity {
    private String UPDATE_URL = "https://asadrao17.000webhostapp.com/update.php";
    int position;
    private EditText edtName, edtEmail, edtCountry, edtMobile;
    private Button btnSave;
    private String name, email, country, mobile;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        Intent mIntent = getIntent();
        position = mIntent.getIntExtra("position", 100000);
        //Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCountry = findViewById(R.id.edtCountry);
        edtMobile = findViewById(R.id.edtMobile);
        btnSave = findViewById(R.id.btnSave);
        id = findViewById(R.id.id);

        id.setText("ID: " + ShowUsersActivity.arrayListUser.get(position).getId());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(position);
            }
        });

        settingEditTextValues();
    }

    private void settingEditTextValues() {
        String namename = getIntent().getStringExtra("name");
        String emailemail = getIntent().getStringExtra("email");
        String countrycountry = getIntent().getStringExtra("country");
        String mobilemobile = getIntent().getStringExtra("mobile");

        edtName.setText(namename);
        edtEmail.setText(emailemail);
        edtCountry.setText(countrycountry);
        edtMobile.setText(mobilemobile);
    }

    private void updateData(int position) {
        name = edtName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        country = edtCountry.getText().toString().trim();
        mobile = edtMobile.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(position));
                params.put("name", name);
                params.put("email", email);
                params.put("country", country);
                params.put("mobile", mobile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateDataActivity.this);
        requestQueue.add(request);

    }
}