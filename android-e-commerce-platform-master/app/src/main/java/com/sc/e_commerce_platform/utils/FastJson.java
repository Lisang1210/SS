package com.sc.e_commerce_platform.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sc.e_commerce_platform.entity.Goods;
import com.sc.e_commerce_platform.entity.OrdersShow;
import com.sc.e_commerce_platform.entity.User;

import java.util.List;


public class FastJson {
    public static Boolean flag(String obj)
    {
        //获取obj中flag的内容
        return JSON.parseObject(obj).getBoolean("flag");
    }
    public static List<Goods> listParseJson(String obj)
    {
        /*// 解析JSON字符串
        JSONArray jsonArray = JSONArray.parseArray(obj);
        // 创建一个List来存储Good对象
        List<Goods> goodsList = new ArrayList<>();*/

        JSONObject jsonObject = JSONObject.parseObject(obj);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Goods> goodsList = jsonArray.toJavaList(Goods.class);
        // 遍历
        /*for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Goods good = new Goods();
            good.setId(jsonObject1.getLongValue("id"));
            good.setPicture(jsonObject1.getString("picture"));
            good.setTitle(jsonObject1.getString("title"));
            good.setPrice(jsonObject1.getFloatValue("price"));
            good.setSoldNum(jsonObject1.getString("soldNum"));
            goodsList.add(good);
        }*/

        return goodsList;
    }

    public static List<OrdersShow> ordersShows(String obj)
    {
        /*// 解析JSON字符串
        JSONArray jsonArray = JSONArray.parseArray(obj);
        // 创建一个List来存储Good对象
        List<Goods> goodsList = new ArrayList<>();*/

        JSONObject jsonObject = JSONObject.parseObject(obj);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<OrdersShow> goodsList = jsonArray.toJavaList(OrdersShow.class);
        // 遍历
        /*for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Goods good = new Goods();
            good.setId(jsonObject1.getLongValue("id"));
            good.setPicture(jsonObject1.getString("picture"));
            good.setTitle(jsonObject1.getString("title"));
            good.setPrice(jsonObject1.getFloatValue("price"));
            good.setSoldNum(jsonObject1.getString("soldNum"));
            goodsList.add(good);
        }*/

        return goodsList;
    }

    public static Goods parseJsonGood(String obj)
    {
        JSONObject jsonObject = JSONObject.parseObject(obj);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        Goods good = new Goods();
        good.setId(jsonObject1.getLongValue("id"));
        good.setPicture(jsonObject1.getString("picture"));
        good.setTitle(jsonObject1.getString("title"));
        good.setPrice(jsonObject1.getFloatValue("price"));
        good.setSoldNum(jsonObject1.getString("soldNum"));
        good.setDeliveryTime(jsonObject1.getString("deliveryTime"));
        good.setDistribution(jsonObject1.getString("distribution"));
        return good;

    }

    public static User parseJsonUser(String obj)
    {
        JSONObject jsonObject = JSONObject.parseObject(obj);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        User user = jsonObject1.toJavaObject(User.class);
        return user;

    }

    public static String parseJsonMessage(String obj){
        return JSON.parseObject(obj).getString("message");
    }
    public static String userToJson(User user){
        // 使用Fastjson将Map转换为JSON字符串
        String jsonString = JSON.toJSONString(user);
        return jsonString;
    }

}
