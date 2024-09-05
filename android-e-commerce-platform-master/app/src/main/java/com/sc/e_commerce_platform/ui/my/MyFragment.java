package com.sc.e_commerce_platform.ui.my;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.activity.LoginActivity;
import com.sc.e_commerce_platform.activity.MyOrderActivity;


public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    ImageView imageView;
    Button btn1,btn2;
    @Override
    public void onResume()
    {
        super.onResume();
        tv1 = getView().findViewById(R.id.number);
        tv2 = getView().findViewById(R.id.name);
        tv3 = getView().findViewById(R.id.sex);
        tv4 = getView().findViewById(R.id.introduction);
        tv6 = getView().findViewById(R.id.address);
        btn1 = getView().findViewById(R.id.btn1);
        btn2 = getView().findViewById(R.id.btn2);
        imageView = getView().findViewById(R.id.imageView);


        SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
        Long number = sp.getLong("number", 0);
        String name = sp.getString("name","");
        String sex = sp.getString("sex","");
        String introduction = sp.getString("introduction","");
        String address = sp.getString("address","");
        String pic = sp.getString("pic","");

        tv1.setText(number.toString());
        tv2.setText(name);
        tv3.setText(sex);
        tv4.setText(introduction);
        tv6.setText(address);
        Glide.with(getContext())
                .load(pic)
                .into(imageView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSharedPreferences("user", 0).edit().clear().commit();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}