package com.sc.e_commerce_platform.ui.shoppingcart;


import static com.sc.e_commerce_platform.entity.URL.URL_DELETE_SHOPPINGCART;
import static com.sc.e_commerce_platform.entity.URL.URL_GET_GOODS;
import static com.sc.e_commerce_platform.entity.URL.URL_GET_SHOPPINGCART;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.activity.SubmitOrderActivity;
import com.sc.e_commerce_platform.adapter.GoodsAdapter;
import com.sc.e_commerce_platform.entity.Goods;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;

import java.util.List;


public class ShoppingCartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        return view;
    }
    ListView listView;
    Button btn1,btn2;
    TextView tv;
    int flag = 0;


    @Override
    public void onResume() {
        super.onResume();
        listView = getView().findViewById(R.id.listView_shoppingcart);
        btn1 = getView().findViewById(R.id.button_delete);
        btn2 = getView().findViewById(R.id.button_settlement);
        tv = getView().findViewById(R.id.textView_total_price);
        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);

        SharedPreferences sp = getActivity().getSharedPreferences("user",0);
        Long userId = sp.getLong("id",0);

        Handler handle1 = new Handler(Looper.getMainLooper()){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String data = (String)msg.obj;
                List<Goods> goods = FastJson.listParseJson(data);
                GoodsAdapter adapter = new GoodsAdapter(requireContext(), R.layout.layout_listview, goods);
                listView.setAdapter(adapter);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRequest myRequest = new MyRequest();
                String URL = URL_GET_SHOPPINGCART+userId;
                String data = myRequest.get(URL);
                Log.e("data",data);
                Message message = new Message();
                message.obj = data;
                handle1.sendMessage(message);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(flag == 0){
                    flag = Integer.parseInt(view.getTag().toString());
                    view.setBackgroundColor(getResources().getColor(R.color.grey));
                }else{
                    flag = 0;
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });

        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String URL = URL_GET_SHOPPINGCART+userId;
                        String data = myRequest.get(URL);
                        Log.e("data",data);
                        Message message = new Message();
                        message.obj = data;
                        handle1.sendMessage(message);
                    }
                }).start();
                Toast.makeText(requireContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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
                    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG);
                    //重新加载页面
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyRequest myRequest = new MyRequest();
                            String URL = URL_GET_SHOPPINGCART+userId;
                            String data = myRequest.get(URL);
                            Log.e("data",data);
                            Message message = new Message();
                            message.obj = data;
                            handle1.sendMessage(message);
                        }
                    }).start();
                }else{
                    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG);
                }
            }
        };

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String URL = URL_DELETE_SHOPPINGCART+"?"+"userId="+userId+"&"+"goodId="+flag;
                        String data = myRequest.delete(URL);
                        Message message = new Message();
                        message.obj = data;
                        handle2.sendMessage(message);
                    }
                }).start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= sp.edit();
                editor.putLong("goodId",flag);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(requireContext(), SubmitOrderActivity.class);
                startActivity(intent);
                editor.putLong("goodId",0);
            }
        });
    }

}