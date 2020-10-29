package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edtNumber, edtPassword;
    Button btnLogin, btnCreateAccount, btnShowUsers;
    String LOGIN_URL = "https://asadrao17.000webhostapp.com/login.php";
    private String number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNumber = findViewById(R.id.edtNumber);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnShowUsers = findViewById(R.id.btnShowUsers);

        btnShowUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowUsersActivity.class));
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        number = edtNumber.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Empty Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Empty Password", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Login Complete")) {

                        Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                        edtPassword.setText("");
                        edtNumber.setText("");
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("number", number);

                        //
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, "" + jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
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
                    params.put("mobile", number);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }
}