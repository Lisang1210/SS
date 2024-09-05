package com.sc.e_commerce_platform.activity;

import static com.sc.e_commerce_platform.entity.URL.URL_GOOD_Detail;
import static com.sc.e_commerce_platform.entity.URL.URL_SUBMIT_ORDER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.entity.Goods;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;

public class SubmitOrderActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    EditText edt1;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        imageView = findViewById(R.id.image);
        tv1 = findViewById(R.id.title);
        tv2 = findViewById(R.id.price);
        tv3 = findViewById(R.id.number);
        tv4 = findViewById(R.id.address);
        tv5 = findViewById(R.id.distribution);
        tv6 = findViewById(R.id.total_price);
        edt1 = findViewById(R.id.remark);
        btn = findViewById(R.id.submit_order_submit);

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String address = sp.getString("address", "");
        Long userId = sp.getLong("id", 0);

        Long goodId = sp.getLong("goodId", 0);

        final String[] distribution = new String[1];

        Handler handler = new Handler(Looper.getMainLooper()){
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                    {
                        case 1:
                            String data = (String) msg.obj;
                            Goods goods = FastJson.parseJsonGood(data);
                            Glide.with(SubmitOrderActivity.this)
                                    .load(goods.getPicture())
                                    .into(imageView);
                            tv1.setText(goods.getTitle());
                            tv2.setText(goods.getPrice().toString());
                            tv3.setText("1");
                            tv4.setText(address);
                            distribution[0] = goods.getDistribution();
                            tv5.setText(distribution[0]);
                            tv6.setText(goods.getPrice().toString());
                            break;
                        case 2:
                            String data1 = (String) msg.obj;
                            Boolean flag1 = FastJson.flag(data1);
                            Log.e("data",data1+"");
                            if (flag1){
                                Toast.makeText(SubmitOrderActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(SubmitOrderActivity.this, MyOrderActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SubmitOrderActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRequest myRequest = new MyRequest();
                String URl = URL_GOOD_Detail+goodId;
                String data = myRequest.get(URl);
                Message message = new Message();
                message.what = 1;
                message.obj= data;
                handler.sendMessage(message);
            }
        }).start();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = edt1.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String URL = URL_SUBMIT_ORDER;
                        String json = "{\"goodId\":"+goodId+",\"remarks\":\""+remark+"\",\"address\":\""+address+"\",\"userId\":\""+userId+"\",\"distribution\":\""+distribution[0]+"\"}";
                        String data = myRequest.post(URL, json);
                        Message message = new Message();
                        message.what = 2;
                        message.obj= data;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

    }
}