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

public class CommuteView implements DynamicView {
    private Context context;

    public CommuteView(Context context) {
        this.context = context;
    }

    @Override
    public void show(int index) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.commute_layout, null, false);
        LinearLayout ly = (LinearLayout) inflate.findViewById(R.id.commute_layout);
        MaterialSpinner spinner = ly.findViewById(R.id.date_user);
        TextView text = ly.findViewById(R.id.des_text);
        RadiusImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.mode0);
        text.setText("hhhhhh");
        new MaterialDialog.Builder(context)
                .customView(inflate, true)
                .title(MODL_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {
                    String command = "{\n" +
                            "    \"command\": \"mode1\",\n" +
                            "    \"time1\": \"18:00\",\n" +
                            "    \"time2\": \"19:00\",\n" +
                            "    \"date_name\":\"" + spinner.getSelectedItem()+"\""+
                            "}";
                    Log.d(APP_LOG,command);

                    new SendMessageTask(message -> {
                        if(parseMessage(context,message)) {
                            Intent intent = new Intent(context, ExperienceActivity.class);
                            intent.putExtra("image", R.drawable.mode0);
                            context.startActivity(intent);
                        }
                    }).execute(command);
//            Intent intent = new Intent(context, ExperienceActivity.class);
//            context.startActivity(intent);


        }).show();
    }

    private void initListener(LinearLayout ly) {
//        Button workTime = ly.findViewById(R.id.work_time);
//        Button dateTime = ly.findViewById(R.id.date_time);
        EditSpinner dateUser = ly.findViewById(R.id.date_user);
//        Button startBtn = ly.findViewById(R.id.commute_start);
//
//        TimePickerView workTimePicker = new TimePickerBuilder(context, (date, v) -> {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            String format = simpleDateFormat.format(date);
//            workTime.setText(format);
//        })
//                .setType(TimePickerType.DATE)
//                .setTitleText("下班时间")
//                .isDialog(true)
//                .setOutSideCancelable(false)
//                .build();
//        TimePickerView dateTimePicker = new TimePickerBuilder(context, (date, v) -> {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            String format = simpleDateFormat.format(date);
//            dateTime.setText(format);
//        })
//                .setType(TimePickerType.DATE)
//                .setTitleText("约会时间")
//                .isDialog(true)
//                .setOutSideCancelable(false)
//                .build();
//        workTime.setOnClickListener(v -> {
//            workTimePicker.show();
//        });
//        dateTime.setOnClickListener(v -> {
//            dateTimePicker.show();
//        });
//        dateUser.setOnItemSelectedListener((view, position, id, item) -> {
//
//        });
//        startBtn.setOnClickListener(v -> {
////            new SendMessageTask().execute("commute");
//            Intent intent = new Intent(context, ExperienceActivity.class);
//            context.startActivity(intent);
//        });
    }
}
