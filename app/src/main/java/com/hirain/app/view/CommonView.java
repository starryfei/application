package com.hirain.app.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hirain.app.R;
import com.hirain.app.activity.ExperienceActivity;
import com.hirain.app.common.Constants;
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.MODL_LIST;

public class CommonView implements DynamicView {
    private Context context;
    private int index;
    public CommonView(Context context,int index) {
        this.context = context;
        this.index = index;
    }

    @Override
    public void show(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.common_layout, null, false);
        LinearLayout ly = (LinearLayout) inflate.findViewById(R.id.common_layout);
        TextView text = ly.findViewById(R.id.des_text);
        text.setText(Constants.MODE_DESC.get(index));
        RadiusImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.mode0+index);
        if(index == 7){
            new MaterialDialog.Builder(context)
                    .content("敬请期待")
                    .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                    .negativeColorRes(R.color.dialog_cancel_color)
                    .positiveText("确定").backgroundColorRes(R.color.dialog_color)
                    .show();
        } else {
            MaterialDialog show = new MaterialDialog.Builder(context)
                    .customView(inflate, true)
                    .title(MODL_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                    .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)
                    .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                    .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {


                        String command = "{\n" +
                                "    \"command\":\"mode" + (index + 1) + "\"" +
                                "}";
                        Log.d(APP_LOG, command);

                        new SendMessageTask(message -> {
                            if(parseMessage(context, message)) {
                                Intent intent = new Intent(context, ExperienceActivity.class);
                                intent.putExtra("image", R.drawable.mode0 + index);
                                context.startActivity(intent);
                            }

                        }).execute(command);
//                        Intent intent = new Intent(context, ExperienceActivity.class);
//                        context.startActivity(intent);

                    }).show();
        }
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
