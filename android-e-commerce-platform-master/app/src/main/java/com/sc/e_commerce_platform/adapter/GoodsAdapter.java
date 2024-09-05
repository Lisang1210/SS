package com.sc.e_commerce_platform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.entity.Goods;

import java.util.List;

public class GoodsAdapter extends ArrayAdapter {
    private final int resourceId;

    public GoodsAdapter(Context context, int textViewResourceId, List<Goods> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods goods = (Goods) getItem(position); // 获取当前项的Good实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView goodImage = (ImageView) view.findViewById(R.id.imageView);//获取该布局内的图片视图
        TextView goodName = (TextView) view.findViewById(R.id.title);//获取该布局内的文本视图
        TextView goodPrice = (TextView) view.findViewById(R.id.price);
        TextView goodSoldNum = (TextView) view.findViewById(R.id.sold_num);
        Glide.with(getContext())
                .load(goods.getPicture())
                        .into(goodImage);
        goodName.setText(goods.getTitle());
        goodPrice.setText(goods.getPrice().toString());
        goodSoldNum.setText(goods.getSoldNum());
        view.setTag(goods.getId());
        view.setTag(R.id.textView_total_price, goods.getPrice());
        return view;
    }

}
