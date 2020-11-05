package com.example.mysqlcrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysqlcrud.Model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAdapter extends ArrayAdapter<User> {
    Context context;
    List<User> arrayListUser;

    public MyAdapter(@NonNull Context context, List<User> arrayListUser) {
        super(context, R.layout.item_user, arrayListUser);
        this.context = context;
        this.arrayListUser = arrayListUser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, true);

        TextView tvID = view.findViewById(R.id.user_id);
        TextView tvUsername = view.findViewById(R.id.user_name);
        /*TextView tvEmail = view.findViewById(R.id.user_email);
        TextView tvCountry = view.findViewById(R.id.user_country);
        TextView tvMobile = view.findViewById(R.id.user_mobile);*/

        tvID.setText(arrayListUser.get(position).getId());
        tvUsername.setText(arrayListUser.get(position).getName());
       /* tvEmail.setText(arrayListUser.get(position).getEmail());
        tvCountry.setText(arrayListUser.get(position).getCountry());
        tvMobile.setText(arrayListUser.get(position).getMobile());*/

        return view;
    }
}