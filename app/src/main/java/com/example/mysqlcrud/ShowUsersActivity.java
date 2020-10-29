package com.example.mysqlcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowUsersActivity extends AppCompatActivity {
    private ListView myListView;
    private MyAdapter myAdapter;
    public static ArrayList<User> arrayListUser = new ArrayList<>();
    private String RETRIEVE_URL = "https://asadrao17.000webhostapp.com/retrieve.php";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        myListView = findViewById(R.id.myListView);
        myAdapter = new MyAdapter(this, arrayListUser);
        myListView.setAdapter(myAdapter);
        retrieveData();
    }

    private void retrieveData() {

        StringRequest request = new StringRequest(Request.Method.POST, RETRIEVE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                arrayListUser.clear();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (sucess.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            String id = jsonArray.getJSONObject(i).getString("id");
                            String name = jsonArray.getJSONObject(i).getString("name");
                            String email = jsonArray.getJSONObject(i).getString("email");
                            String country = jsonArray.getJSONObject(i).getString("country");
                            String mobile = jsonArray.getJSONObject(i).getString("mobile");

                            user = new User(id, name, email, country, mobile);
                            arrayListUser.add(user);
                            myAdapter.notifyDataSetChanged();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowUsersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ShowUsersActivity.this);
        requestQueue.add(request);
    }
}