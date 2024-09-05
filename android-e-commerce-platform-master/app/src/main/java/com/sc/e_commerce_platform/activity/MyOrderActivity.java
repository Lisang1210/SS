package com.sc.e_commerce_platform.activity;

import static com.sc.e_commerce_platform.entity.URL.URL_GET_ORDERS;
import static com.sc.e_commerce_platform.entity.URL.URL_ORDER_DELETE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.adapter.GoodsAdapter1;
import com.sc.e_commerce_platform.entity.OrdersShow;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;

import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3;
    ListView listView;
    Button btn;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        listView = findViewById(R.id.listView);
        /*tv1 = findViewById(R.id.name);
        tv2 = findViewById(R.id.phoneNum);
        tv3 = findViewById(R.id.address);*/
        btn = findViewById(R.id.btn);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        Long userId  = sp.getLong("id", 0);
        /*String name = sp.getString("name", "");
        String address = sp.getString("address", "");
        Long number = sp.getLong("number", 0);

        tv1.setText(name);
        tv2.setText(number.toString());
        tv3.setText(address);*/

        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String data = (String) msg.obj;
                List<OrdersShow> ordersGoods = FastJson.ordersShows(data);
                GoodsAdapter1 adapter = new GoodsAdapter1( MyOrderActivity.this, R.layout.layout_listview2, ordersGoods);
                listView.setAdapter(adapter);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRequest myRequest = new MyRequest();
                String URL = URL_GET_ORDERS+userId;
                String data = myRequest.get(URL);
                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);
            }
        }).start();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //清空listview
                listView.setAdapter(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String URL = URL_GET_ORDERS+userId;
                        String data = myRequest.get(URL);
                        Message message = new Message();
                        message.obj = data;
                        handler.sendMessage(message);
                    }
                }).start();
                Toast.makeText(MyOrderActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0){
                    flag = Integer.parseInt(view.getTag().toString());
                    view.setBackgroundColor(getResources().getColor(R.color.grey));
                }else{
                    flag=0;
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                }

            }
        });

        Handler handle2 = new Handler(Looper.getMainLooper()){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String data = (String)msg.obj;
                Boolean flag = FastJson.flag(data);
                String message = FastJson.parseJsonMessage(data);
                if(flag){
                    Toast.makeText(MyOrderActivity.this,message,Toast.LENGTH_LONG);
                    //重新加载页面
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyRequest myRequest = new MyRequest();
                            String URL = URL_GET_ORDERS+userId;
                            String data = myRequest.get(URL);
                            Message message = new Message();
                            message.obj = data;
                            handler.sendMessage(message);
                        }
                    }).start();
                }else{
                    Toast.makeText(MyOrderActivity.this,message,Toast.LENGTH_LONG);
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String URL = URL_ORDER_DELETE +"?"+"userId="+userId+"&"+"goodId="+flag;
                        String data = myRequest.delete(URL);
                        Message message = new Message();
                        message.obj = data;
                        handle2.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}