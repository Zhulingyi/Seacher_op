package com.example.lingyi.seacher_op;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lingyi.seacher_op.zxing.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public final static int SCANNING_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA = 0;//相机权限请求码
    private final int CAMERA_OK = 100;
    private final int CAMERA_NO = 101;
    private int CAMERA_STATUS;

    @Bind(R.id.img_home)
    ImageView img_home;
    @Bind(R.id.img_barcode)
    ImageView img_barcode;
    @Bind(R.id.img_history)
    ImageView img_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //防止软键盘将布局顶上去
//        img_barcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,CaptureActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//防止画面重复跳转
//                startActivity(intent);
//            }
//        });

    }
    @OnClick({R.id.img_barcode,R.id.img_history,R.id.img_home,R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_barcode:
                requestPermission();//相机权限请求
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    //若获得相机的权限
                    Intent intent=new Intent(MainActivity.this,CaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//防止画面重复跳转
                    startActivity(intent);
                }

                break;
            case R.id.img_history:
                Intent intent=new Intent(MainActivity.this,GoodsDetaileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//防止画面重复跳转
                intent.setFlags(111);
                startActivity(intent);
            default:
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                CAMERA_STATUS=CAMERA_OK;
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            }
        } else {
            Log.i("CAMERA","相机权限已经被禁止");
            CAMERA_STATUS=CAMERA_NO;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("CAMERA","相机权限已经申请");
                CAMERA_STATUS=CAMERA_OK;
            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
//    @Override
//    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        switch(resultCode){
//            case SCANNING_REQUEST_CODE:
//                if(resultCode==RESULT_OK){
//                    final Bundle bundle = data.getExtras();
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText(bundle.getString("result"));
//                        }
//                    });
//                }
//                break;
//            default:
//                break;
//        }
//        }

    }

