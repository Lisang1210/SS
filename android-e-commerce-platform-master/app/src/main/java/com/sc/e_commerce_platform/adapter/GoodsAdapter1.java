package com.sc.e_commerce_platform.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.entity.OrdersShow;

import java.util.List;

public class GoodsAdapter1 extends ArrayAdapter {
    private final int resourceId;
    SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

    public GoodsAdapter1(Context context, int textViewResourceId, List<OrdersShow> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name1 = sp.getString("name", "");
        Long phoneNum1 = sp.getLong("number", 0);
        String address1 = sp.getString("address", "");
        OrdersShow ordersShow = (OrdersShow) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView goodImage = (ImageView) view.findViewById(R.id.imageView);//获取该布局内的图片视图
        TextView goodName = (TextView) view.findViewById(R.id.title);//获取该布局内的文本视图
        TextView goodPrice = (TextView) view.findViewById(R.id.price);
        TextView goodDistribution = (TextView) view.findViewById(R.id.distribution);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView phoneNum = (TextView) view.findViewById(R.id.phoneNum);
        TextView address = (TextView) view.findViewById(R.id.address);
        Glide.with(getContext())
                .load(ordersShow.getPicture())
                        .into(goodImage);
        goodName.setText(ordersShow.getTitle());
        goodPrice.setText(ordersShow.getPrice().toString());
        goodDistribution.setText(ordersShow.getDistribution());
        name.setText(name1);
        phoneNum.setText(phoneNum1+"");
        address.setText(address1);
        view.setTag(ordersShow.getId());

        return view;
    }

}
