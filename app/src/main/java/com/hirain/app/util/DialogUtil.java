package com.hirain.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.MainActivity;
import com.hirain.app.R;
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.apache.commons.lang3.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.hirain.app.common.Constants.APP_LOG;

public class DialogUtil {

    public static void commonDialog(Context context, String content, String command){
        new MaterialDialog.Builder(context)
                .content(content).titleGravity(GravityEnum.CENTER).backgroundColorRes(R.color.dialog_color)
                .positiveText("确定").onPositive((dialog, which) -> {

            Log.d(APP_LOG,command);

            new SendMessageTask(message -> {
                if(StringUtils.isNoneBlank(message)) {
                    JSONObject parse = (JSONObject) JSONObject.parse(message);

                    Log.d(APP_LOG, message);
                    String status = parse.getString("status");
                    if (StringUtils.equalsIgnoreCase(status, "success")) {
                        toast(context, status);
                    }
                } else {
                    toast(context, "connect server fail");

                }

            }).execute(command);

        }) .negativeText("取消").show();
    }

    public static void messageDialog(Context context,String message) {
        new MaterialDialog.Builder(context)
                .content(message)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .negativeColorRes(R.color.dialog_cancel_color)
                .positiveText("确定").backgroundColorRes(R.color.dialog_color)
                .show();
    }


    public static void signOutUser(Activity context) {
        new MaterialDialog.Builder(context)
                .content("是否退出当前用户").titleGravity(GravityEnum.CENTER)
                .backgroundColorRes(R.color.dialog_color)
                .positiveText("确定").onPositive((dialog, which) -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            context.finish();
            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.clear();
        }) .negativeText("取消").show();
    }




    public static void toast(Context context,String info){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //放在UI线程弹Toast
            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
        });


    }
    public static void uiDialog(Context context,String info){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            //放在UI线程弹Toast
            new MaterialDialog.Builder(context).titleColorRes(R.color.white)
                    .positiveColorRes(R.color.dialog_ok_color)
                    .title("⚠️警告")
                    .content(info)
                    .positiveText("确定")
                    .show();
        });

    }
    public static void imageDialogTimer(Context context, Bitmap bitmap){
        Handler handler = new Handler(Looper.getMainLooper());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.dialog_image_layout, null, false);
        ImageView ly = (ImageView) inflate.findViewById(R.id.dialog_image);
        ly.setImageBitmap(bitmap);
        handler.post(() -> {
            //放在UI线程弹Toast
            MaterialDialog dialog = new MaterialDialog.Builder(context).titleColorRes(R.color.white)
                    .positiveColorRes(R.color.dialog_ok_color).customView(inflate,true)
                    .title("图像").titleGravity(GravityEnum.CENTER)
                    .backgroundColorRes(R.color.dialog_color)
                    .positiveText("确定")
                    .show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width=300;
            lp.height = 250;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            },5000);
        });

    }
}
