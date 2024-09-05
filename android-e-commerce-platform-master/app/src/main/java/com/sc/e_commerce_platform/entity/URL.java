package com.sc.e_commerce_platform.entity;

public class URL {
    static String url = "http://10.2.177.76:8080";
    public static final String URL_LOGIN = url+"/login";
    public static final String URL_REGISTER = url+"/register";
    public static final String URL_GET_GOODS = url+"/goods/getAllGoods";
    public static final String URL_SEEK_GOODS = url+"/goods/seekGoods?title=";
    public static final String URL_GOOD_Detail = url+"/goods/getGoodDetail?id=";
    public static final String URL_GET_SHOPPINGCART = url+"/shoppingCart/getAll?userId=";
    public static final String URL_ADD_SHOPPINGCART = url+"/shoppingCart/add";
    public static final String URL_DELETE_SHOPPINGCART = url+"/shoppingCart/delete";
    public static final String URL_SUBMIT_ORDER = url+"/orders/submitOrder";
    public static final String URL_GET_ORDERS = url+"/orders/getAllOrders?userId=";
    public static final String URL_ORDER_DELETE = url+"/orders/delete";
}
