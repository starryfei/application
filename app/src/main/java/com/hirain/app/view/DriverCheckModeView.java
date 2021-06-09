package com.hirain.app.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hirain.app.R;
import com.hirain.app.activity.ExperienceActivity;
import com.hirain.app.common.Constants;
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.CHECK_LIST;

/**
 * 驾驶员检测三合一
 */
public class DriverCheckModeView implements DynamicView {
    private Context context;

    public DriverCheckModeView(Context context) {
        this.context = context;
    }

    @Override
    public void show(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.common_layout, null, false);
        LinearLayout ly = inflate.findViewById(R.id.common_layout);
        TextView text = ly.findViewById(R.id.des_text);
        ImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.check0);
        text.setText(Constants.CHECK_DESC.get(0));

        new MaterialDialog.Builder(context)
                .customView(inflate, true)
                .title(CHECK_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .negativeColorRes(R.color.dialog_cancel_color)
                .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)

                .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {
            String command = "{\n" +
                    "    \"command\":\"function1" + (index + 1) + "\"" +
                    "}";
            new SendMessageTask(message -> {
                if (parseMessage(context, message)) {
                    Intent intent = new Intent(context, ExperienceActivity.class);
                    intent.putExtra("image", R.drawable.check0);
                    context.startActivity(intent);
                }

            }).execute(command);

            Log.d(APP_LOG, command);

        }).show();
//            Window dialogWindow = show.getWindow();
//            WindowManager m = dialogWindow.getWindowManager();
//            // 获取屏幕宽、高度
//            Display d = m.getDefaultDisplay();
//            // 获取对话框当前的参数值
//            WindowManager.LayoutParams p = dialogWindow.getAttributes();
//            // 高度设置为屏幕的0.6，根据实际情况调整
//
////            p.height = (int) (d.getHeight() * 0.8);
//            // 宽度设置为屏幕的0.65，根据实际情况调整
//            p.width = (int) (d.getWidth() * 0.8);
//            dialogWindow.setAttributes(p);

        // 调整dialog窗口大小
//        Window window = show.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//
//        lp.gravity = Gravity.CENTER;
//        lp.width = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW/2;//宽高可设置具体大小
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.width=600;
////        lp.height = 400;
//        window.setAttributes(lp);


    }


}
