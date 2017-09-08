package com.example.lingyi.seacher_op;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lingyi.seacher_op.util.HttpUtil;
import com.example.lingyi.seacher_op.zxing.utils.DialogUtils;

import net.sf.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by lingyi on 2017/8/31.
 */

public class GoodsDetaileActivity extends AppCompatActivity {
  public static final String APPKEY = "0e46d3655f525cd0";// 你的appkey
  public static final String URL = "http://api.jisuapi.com/barcode2/query";
  private int GETSTATUS;
  private static int GET_OK=1000;
  private static int GET_NO=1001;
  public String barcode;
    private Dialog mWeiboDialog;
  @Bind(R.id.txt_goodsname)
  TextView txt_goodsname;
  @Bind(R.id.txt_barcode)
  TextView txt_goodsbarcode;
//  @Bind(R.id.txt_goodsdetail)
//  TextView txt_goodsdetail;
  @Bind(R.id.txt_selectdistance)
  TextView txt_distanceselect;
  @Bind(R.id.txt_selectprice)
  TextView getTxt_distanceselect;
  @Bind(R.id.img_back)
    ImageView img_back;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodsdetaile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        barcode=bundle.getString("result");
            Log.i("商品详情Activity",bundle.getString("result"));
            // new Thread(runnable).start();
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    txt_goodsname.setText(bundle.getString("result"));
//                    //txt_goodsDetaile.setText("123456789");
//                }
//            });

        mWeiboDialog = DialogUtils.createLoadingDialog(GoodsDetaileActivity.this, "加载中...");

        Thread t =new Thread(runnable);
        Log.i("THREAD","线程开始");
        t.start();
        Log.i("THREAD","线程kaikaiakai");

    }

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.i("TTTT","加载完成");
               DialogUtils.closeDialog(mWeiboDialog);
                if (GETSTATUS==GET_OK){
                    Goods goods = (Goods) msg.obj;
                    txt_goodsname.setText(goods.getName());
                    // txt_goodsdetail.setText(goods.getNetcontent());
                    txt_goodsbarcode.setText(goods.getBarcode());
                }



            }
        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
                    Get();
            // TODO: http request.

        }
    };

    public void Get() {
        Log.i("BEGIN","GET方法开始");
        String result = null;
        String url = URL + "?appkey=" + APPKEY + "&barcode=" + barcode;
        try { //result = HttpUtil.sendGet(url,"utf-8");
            Log.i("BEGIN","TRY1");
            result = HttpUtil.sendGet(url,"utf-8");
            Log.i("BEGIN","TRY2");
            JSONObject json = JSONObject.fromObject(result);
            if (json.getInt("status") != 0) {
                GETSTATUS=GET_NO;
                Log.i("TEST1",json.getString("msg"));
                if (json.getString("msg")=="条码为空"){
                    Toast.makeText(this, "条码为空", Toast.LENGTH_SHORT).show();
                }if(json.getString("msg")=="条码不正确"){
                    Toast.makeText(this, "条码不正确", Toast.LENGTH_SHORT).show();
                }else if (json.getString("msg")=="没有信息"){
                    Toast.makeText(this, "没有信息", Toast.LENGTH_SHORT).show();
                }

            } else {
                GETSTATUS=GET_OK;
                Log.i("BEGIN","TRY3");
                JSONObject resultarr = json.optJSONObject("result");
                String barCode = resultarr.getString("barcode");
                String name = resultarr.getString("name");
                String origincountry = resultarr.getString("origincountry");
               // String pic = resultarr.getString("pic");
                Goods good=new Goods(barCode,name,origincountry);
//                if (pic!="null"|pic!=null){
//                    good.pic=pic;
//                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj=good;
                Log.i("TEST2",barCode+"  "+good.getName()+"  "+origincountry+"   ");
                mHandler.sendMessage(msg);

            }


        }catch (Exception e) {
            e.printStackTrace();
            Log.i("BEGIN","有错误");
        }

    }
    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(GoodsDetaileActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
