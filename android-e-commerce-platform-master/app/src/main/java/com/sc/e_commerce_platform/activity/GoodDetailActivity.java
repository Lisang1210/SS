package com.sc.e_commerce_platform.activity;

import static com.sc.e_commerce_platform.entity.URL.URL_ADD_SHOPPINGCART;
import static com.sc.e_commerce_platform.entity.URL.URL_GOOD_Detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.entity.Goods;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;

import java.util.HashMap;
import java.util.Map;

public class GoodDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv1,tv2,tv3,tv4,tv5;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);

        imageView = findViewById(R.id.good_detail_image);
        tv1 = findViewById(R.id.good_detail_title);
        tv2 = findViewById(R.id.good_detail_price);
        tv3 = findViewById(R.id.good_sold_num);
        tv4 = findViewById(R.id.good_distribution);
        tv5 = findViewById(R.id.good_delivery_time);
        btn1 = findViewById(R.id.good_detail_add_cart);
        btn2 = findViewById(R.id.good_buy);
        final Long[] goodId = new Long[1];
        
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);

        Handler handler1 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String data = (String) msg.obj;
                        Goods goods = FastJson.parseJsonGood(data);
                        goodId[0] = goods.getId();
                        Glide.with(GoodDetailActivity.this)
                                .load(goods.getPicture())
                                .into(imageView);
                        tv1.setText(goods.getTitle());
                        tv2.setText(goods.getPrice().toString());
                        tv3.setText(goods.getSoldNum());
                        tv4.setText(goods.getDistribution());
                        tv5.setText(goods.getDeliveryTime());
                        break;
                    case 2:
                        String data1 = (String) msg.obj;
                        Boolean flag = FastJson.flag(data1);
                        if(flag){
                            Toast.makeText(GoodDetailActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GoodDetailActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRequest myRequest = new MyRequest();
                Intent intent = getIntent();
                long id = intent.getLongExtra("id", 0);
                String url = URL_GOOD_Detail+id;
                String data = myRequest.get(url);
                Log.e("id",id+1+"");
                Log.e("data",data);
                Message message = new Message();
                message.what = 1;
                message.obj = data;
                handler1.sendMessage(message);

            }
        }).start();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Long GoodId = getIntent().getLongExtra("id", 0);
                        Long userId = sp.getLong("id", 0);
                        String json = "{\"goodId\":"+GoodId+",\"userId\":"+userId+"}";
                        MyRequest myRequest =new MyRequest();
                        String URL = URL_ADD_SHOPPINGCART;
                        String data = myRequest.post(URL,json);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = data;
                        handler1.sendMessage(message);
                    }
                }).start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putLong("goodId",goodId[0]);
                editor.commit();
                Intent intent = new Intent(GoodDetailActivity.this, SubmitOrderActivity.class);
                intent.putExtra("goodId",goodId[0]);
                startActivity(intent);
                editor.putLong("goodId",0);
            }
        });
    }
}