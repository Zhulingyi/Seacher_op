package com.example.lingyi.seacher_op;


import android.util.Log;
import android.widget.Toast;

import com.example.lingyi.seacher_op.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 * Created by lingyi on 2017/9/4.
 */

public class Query {
    public static final String APPKEY = "0e46d3655f525cd0";// 你的appkey
    public static final String URL = "http://api.jisuapi.com/barcode2/query";
    public static final String barcode = "694931";
    public static void Get() {
        Log.i("BEGIN","GET方法开始");
        String result = null;
        String url = URL + "?appkey=" + APPKEY + "&barcode=" + barcode;
        try { //result = HttpUtil.sendGet(url,"utf-8");
            Log.i("BEGIN","TRY1");
            result = HttpUtil.sendGet(url,"utf-8");
            Log.i("BEGIN","TRY2");
            JSONObject json = JSONObject.fromObject(result);
            if (json.getInt("status") != 0) {
                Log.i("TEST1",json.getString("msg"));
                //Toast toast= Toast.makeText, "简单的提示信息", Toast.LENGTH_SHORT);

            } else {
                Log.i("BEGIN","TRY3");
                JSONObject resultarr = json.optJSONObject("result");
                String barCode = resultarr.getString("barcode");
                String name = resultarr.getString("name");
                String origincountry = resultarr.getString("origincountry");
                Log.i("TEST2",barCode+"  "+name+"  "+origincountry+"   ");

            }


        }catch (Exception e) {
            e.printStackTrace();
            Log.i("BEGIN","有错误");
        }

    }
}
