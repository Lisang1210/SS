package com.sc.e_commerce_platform.ui.home;

import static com.sc.e_commerce_platform.entity.URL.URL_GET_GOODS;
import static com.sc.e_commerce_platform.entity.URL.URL_SEEK_GOODS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sc.e_commerce_platform.activity.GoodDetailActivity;
import com.sc.e_commerce_platform.adapter.GoodsAdapter;
import com.sc.e_commerce_platform.entity.Goods;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;
import com.sc.e_commerce_platform.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    Button btn;
    EditText ed;
    ListView lv;
    TextView tv;
    int page = 1;
    int pageSize = 10;
    Boolean isLoadingMore = false;

    GoodsAdapter adapter, adapter1;

    List<Goods> goods, goods1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        btn = getView().findViewById(R.id.search_button);
        ed = getView().findViewById(R.id.search);
        lv = getView().findViewById(R.id.list_view);
        tv = getView().findViewById(R.id.tv);
        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout);


        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String data = (String) msg.obj;
                        goods = FastJson.listParseJson(data);
                        adapter = new GoodsAdapter(requireContext(), R.layout.layout_listview, goods);
                        lv.setAdapter(adapter);
                        break;
                    case 2:
                        String data1 = (String) msg.obj;
                        goods1 = FastJson.listParseJson(data1);
                        adapter1 = new GoodsAdapter(requireContext(), R.layout.layout_listview, goods1);
                        lv.setAdapter(adapter1);
                        break;
                    case 3:
                        String moreData = (String) msg.obj;
                        List<Goods> moreGoods = FastJson.listParseJson(moreData);
                        Log.e("moreGoods", moreGoods.toString());
                        if (!moreGoods.isEmpty()) {
                            goods.addAll(moreGoods);
                            adapter.notifyDataSetChanged();
                            page++; // 增加页数以便下一次加载更多数据
                        }
                        isLoadingMore = false;
                        break;
                }
            }
        };



        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myrequest = new MyRequest();
                        String url = URL_GET_GOODS;
                        String data = myrequest.get(url);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
                    }
                }).start();
                Toast.makeText(requireContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                page = 1;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRequest myrequest = new MyRequest();
                String url = URL_GET_GOODS + "?page=" + page + "&pageSize=" + pageSize;
                String data = myrequest.get(url);
                Log.e("data", data);
                Message message = new Message();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        }).start();

        // 搜索功能
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ed.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(requireContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyRequest myrequest = new MyRequest();
                            String url = URL_SEEK_GOODS + text;
                            String data = myrequest.get(url);
                            Message message = new Message();
                            message.what = 2;
                            message.obj = data;
                            handler.sendMessage(message);
                            Log.e("data", data);
                        }
                    }).start();
                }
                ed.setText("");
            }
        });

        // listview点击监听事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                page = 1;
                Intent intent = new Intent();
                intent.setClass(requireContext(), GoodDetailActivity.class);
                intent.putExtra("id", (Long) view.getTag());
                startActivity(intent);
            }
        });

        // 动态加载
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && !isLoadingMore) { // isLoadingMore是一个标志位，表示是否正在加载更多数据
                    isLoadingMore = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MyRequest myRequest = new MyRequest();
                            String url = URL_GET_GOODS + "?page=" + page + "&pageSize=" + pageSize;
                            String data = myRequest.get(url);
                            Message message = new Message();
                            message.what = 3; // 新的标志位，用于区分加载更多的数据
                            message.obj = data;
                            handler.sendMessage(message);
                        }
                    }).start(); // 调用加载更多数据的方法
                }
            }
        });
    }


}