package com.sc.e_commerce_platform.activity;

import static com.sc.e_commerce_platform.entity.URL.URL_LOGIN;
import static com.sc.e_commerce_platform.entity.URL.URL_REGISTER;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.sc.e_commerce_platform.R;
import com.sc.e_commerce_platform.entity.User;
import com.sc.e_commerce_platform.utils.FastJson;
import com.sc.e_commerce_platform.utils.MyRequest;


public class LoginActivity extends AppCompatActivity {
    EditText edt1, edt2;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt1 = findViewById(R.id.username);
        edt2 = findViewById(R.id.password);
        btn1 = findViewById(R.id.login);
        btn2 = findViewById(R.id.register);

        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        long id = sp.getLong("id", 0);
        //判断id是否存在
        if(id!=0){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Handler handler1 = new Handler(Looper.getMainLooper()){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                    {
                        case 1:
                            String data = (String)msg.obj;
                            Boolean flag = FastJson.flag(data);
                            String message = FastJson.parseJsonMessage(data);
                            if(flag){
                                User user = FastJson.parseJsonUser(data);
                                SharedPreferences.Editor editor= sp.edit();
                                editor.putLong("id",user.getId());
                                editor.putLong("number",user.getNumber());
                                editor.putString("name",user.getName());
                                editor.putString("introduction",user.getIntroduction());
                                editor.putString("sex",user.getSex());
                                editor.putString("address",user.getAddress());
                                editor.putString("birthday",user.getBirthday().toString());
                                editor.putString("pic",user.getPic());
                                editor.apply();
                                Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_LONG).show();
                                Intent intent =  new Intent();
                                intent.setClass(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 2:
                            String data1 = (String)msg.obj;
                            Boolean flag1 = FastJson.flag(data1);
                            String message1 = FastJson.parseJsonMessage(data1);
                            if(flag1){
                                Toast.makeText(LoginActivity.this,message1,Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,message1,Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
            }
        };

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String number = edt1.getText().toString();
                String password = edt2.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyRequest myRequest = new MyRequest();
                        String url = URL_LOGIN+"?number="+number+"&password="+password;
                        String data = myRequest.get(url);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = data;
                        Log.e("data",data);
                        handler1.sendMessage(message);
                    }
                }).start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = edt1.getText().toString();
                String password = edt2.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String URL = URL_REGISTER;
                        String Json = "{\"number\":"+number+",\"password\":"+password+"}";
                        MyRequest myRequest = new MyRequest();
                        String data = myRequest.post(URL, Json);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = data;
                        handler1.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}