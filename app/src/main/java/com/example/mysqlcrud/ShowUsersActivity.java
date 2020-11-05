package com.example.mysqlcrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysqlcrud.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowUsersActivity extends AppCompatActivity {
    private ListView myListView;
    private MyAdapter myAdapter;
    public static ArrayList<User> arrayListUser = new ArrayList<>();
    private String RETRIEVE_URL = "https://asadrao17.000webhostapp.com/retrieve.php";
    private String DELETE_URL = "https://asadrao17.000webhostapp.com/delete.php";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);

        myListView = findViewById(R.id.myListView);
        myAdapter = new MyAdapter(this, arrayListUser);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogItem = {"View Data", "Edit Data", "Delete Data"};
                builder.setTitle(arrayListUser.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("position", position);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intentUpdate = new Intent(getApplicationContext(), UpdateDataActivity.class);
                                //Toast.makeText(ShowUsersActivity.this, "" + arrayListUser.get(position).getId(), Toast.LENGTH_SHORT).show();
                                intentUpdate.putExtra("position", position);
                                intentUpdate.putExtra("name", arrayListUser.get(position).getName());
                                intentUpdate.putExtra("email", arrayListUser.get(position).getEmail());
                                intentUpdate.putExtra("country", arrayListUser.get(position).getCountry());
                                intentUpdate.putExtra("mobile", arrayListUser.get(position).getMobile());
                                startActivity(intentUpdate);
                                break;
                            case 2:
                                deleteData(position);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        retrieveData();
    }

    private void deleteData(int position) {
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Data Deleted")) {
                    Toast.makeText(ShowUsersActivity.this, "Data Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowUsersActivity.this, "Failed To Delete!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowUsersActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", arrayListUser.get(position).getId());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ShowUsersActivity.this);
        requestQueue.add(request);
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