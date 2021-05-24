package com.hirain.app.view;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.util.DialogUtil;

import org.apache.commons.lang3.StringUtils;

import static com.hirain.app.common.Constants.APP_LOG;

public interface DynamicView {
    void show(int index);

    default boolean parseMessage(Context context, String message) {
        if(StringUtils.isNoneBlank(message)) {
            JSONObject parse = (JSONObject) JSONObject.parse(message);

            Log.d(APP_LOG, message);
            String status = parse.getString("status");
            if (StringUtils.equalsIgnoreCase(status, "success")) {
                DialogUtil.toast(context,status);
                return true;
            }
        } else {
            Log.e(APP_LOG,"connect server fail");
            DialogUtil.toast(context,"connect server fail");

        }
        return false;
//        Intent intent = new Intent(context, ExperienceActivity.class);
//        context.startActivity(intent);
    }


}
