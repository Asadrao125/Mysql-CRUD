package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword, edtCountry, edtMobile;
    Button btnSave;
    String name, email, password, country, mobile;
    String REGISTER_URL = "https://asadrao17.000webhostapp.com/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtCountry = findViewById(R.id.edtCountry);
        edtMobile = findViewById(R.id.edtMobile);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserData();
            }
        });
    }

    private void inserData() {

        name = edtName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        country = edtCountry.getText().toString().trim();
        mobile = edtMobile.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Empty Field", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Data Saved!")) {
                        Toast.makeText(RegisterActivity.this, "Data Saved To Mysql", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Number Already In Use!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("country", country);
                    params.put("mobile", mobile);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(request);

        }
    }
}