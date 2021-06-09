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
import com.hirain.app.activity.VoiceExperienceActivity;
import com.hirain.app.common.CommandConstants;
import com.hirain.app.common.Constants;
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.CHECK_LIST;

/**
 * 唇音融合/ 需要跳转到有移动中岛
 */
public class VoiceCheckModeView implements DynamicView {
    private Context context;

    public VoiceCheckModeView(Context context) {
        this.context = context;
    }

    @Override
    public void show(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.common_layout, null, false);
        LinearLayout ly = inflate.findViewById(R.id.common_layout);
        TextView text = ly.findViewById(R.id.des_text);
        ImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.check2);

            text.setText(Constants.CHECK_DESC.get(index));
            new MaterialDialog.Builder(context)
                    .customView(inflate, true)
                    .title(CHECK_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                    .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                    .negativeColorRes(R.color.dialog_cancel_color)
                    .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)

                    .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {

                new SendMessageTask(message -> {
                    if (parseMessage(context, message)) {
                        Intent intent = new Intent(context, VoiceExperienceActivity.class);
                        intent.putExtra("image", R.drawable.check2);
                        context.startActivity(intent);
                    }

                }).execute(CommandConstants.VOICE_COMMAND);


                Log.d(APP_LOG, CommandConstants.VOICE_COMMAND);


            }).show();


    }


}
