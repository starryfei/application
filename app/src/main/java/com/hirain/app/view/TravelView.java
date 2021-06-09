package com.hirain.app.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.R;
import com.hirain.app.activity.DriverTravelActivity;
import com.hirain.app.common.Constants;
import com.hirain.app.task.SendMessageTask;
import com.hirain.app.util.DialogUtil;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import org.apache.commons.lang3.StringUtils;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.MODL_LIST;

public class TravelView implements DynamicView {
    private Context context;

    public TravelView(Context context) {
        this.context = context;
    }

    @Override
    public void show(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.travel_layout, null);
        LinearLayout ly = (LinearLayout) inflate.findViewById(R.id.travel_layout);
        TextView text = ly.findViewById(R.id.des_text);
        text.setText(Constants.MODE_DESC.get(index));
        RadiusImageView imageView = ly.findViewById(R.id.mode_image);
        imageView.setImageResource(R.drawable.mode2);
        MaterialSpinner spinner = ly.findViewById(R.id.travel_spinner);
        spinner.setOnItemSelectedListener((view, position, id1, item) -> {
            if(position !=0) {
                Toast.makeText(context,"暂未开放",Toast.LENGTH_SHORT).show();
            }
            spinner.setSelectedIndex(0);

        });
        new MaterialDialog.Builder(context)
                .customView(inflate, true)
                .title(MODL_LIST.get(index)).titleGravity(GravityEnum.CENTER)
                .positiveText(R.string.lab_submit).backgroundColorRes(R.color.dialog_color)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .negativeText(R.string.lab_cancel).onPositive((dialog, which) -> {


            String selectedItem = spinner.getSelectedItem();
            String command = "{\n" +
                    "    \"command\": \"mode3\" ,\n" +
                    "    \"time\": \"18:00\"\n" +
                    "}";
            Log.d(APP_LOG,command);

            new SendMessageTask(message -> {
                if(StringUtils.isNoneBlank(message)) {
                    JSONObject parse = (JSONObject) JSONObject.parse(message);
                    Log.d(APP_LOG, message);
                    String status = parse.getString("status");
                    if (StringUtils.equalsIgnoreCase(status, "success")) {
                        DialogUtil.toast(context,status);
                        Intent intent = new Intent(context, DriverTravelActivity.class);
                        intent.putExtra("image",R.drawable.mode2);
                        context.startActivity(intent);
                    }
                } else {
                    Log.e(APP_LOG,"connect server fail");
                    DialogUtil.toast(context,"connect server fail");

                }

            }).execute(command);
//            Intent intent = new Intent(context, DriverActivity.class);
//            context.startActivity(intent);
        }).show();

    }



}
