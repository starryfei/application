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
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.spinner.editspinner.EditSpinner;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.MODL_LIST;

public class MeetView implements DynamicView {
    private Context context;

    public MeetView(Context context) {
        this.context = context;
    }

    @Override
    public void show(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.meeting_layout, null, false);
        LinearLayout ly = (LinearLayout) inflate.findViewById(R.id.meeting_layout);
        MaterialSpinner editSpinner = ly.findViewById(R.id.meet_name);
        TextView text = ly.findViewById(R.id.des_text);
        text.setText("hhhhhh");
        RadiusImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.mode1);
        new MaterialDialog.Builder(context)
                .customView(inflate, true)
                .title(MODL_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {
            String command = "{\n" +
                    "    \"command\": \"mode2\",\n" +
                    "    \"time\": \"18:00\",\n" +
                    "    \"meeting_name\":\"" + editSpinner.getSelectedItem()+"\""+
                    "}";
            Log.d(APP_LOG,command);

            new SendMessageTask(message -> {
                if(parseMessage(context,message)) {
                    Intent intent = new Intent(context, ExperienceActivity.class);
                    intent.putExtra("image", R.drawable.mode1);
                    context.startActivity(intent);
                }

            }).execute(command);
//            Intent intent = new Intent(context, ExperienceActivity.class);
//            context.startActivity(intent);

        }).show();

    }

    private void initListener(LinearLayout ly) {
//        Button timeBtn = ly.findViewById(R.id.meeting_time);
        EditSpinner spinner = ly.findViewById(R.id.meet_name);
//        Button startBtn = ly.findViewById(R.id.meet_start);
//        TimePickerView mDatePicker = new TimePickerBuilder(context, (date, v) -> {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            String format = simpleDateFormat.format(date);
//            timeBtn.setText(format);
//        }).setType(TimePickerType.DATE)
//                .setTitleText("会议时间")
//                .isDialog(true)
//                .setOutSideCancelable(false)
//                .build();
//
//        timeBtn.setOnClickListener(v -> {
//            mDatePicker.show();
//        });
//
//        startBtn.setOnClickListener(v -> {
////            new SendMessageTask().execute("meet");
//            Intent intent = new Intent(context, ExperienceActivity.class);
//            context.startActivity(intent);
//        });
    }


}
