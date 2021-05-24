package com.hirain.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.hirain.app.R;
import com.hirain.app.task.HealthThread;
import com.hirain.app.task.SendMessageTask;
import com.hirain.app.util.NetwrokUtil;
import com.hirain.app.util.SpeakerUtil;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.VOICE;

public class DriverActivity extends FloatButtonActivity {
    @BindView(R.id.sign_out_btn)
    Button signOutBtn;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.express_image)
    RadiusImageView imageView;

    private HealthThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        ButterKnife.bind(this);
        int image = getIntent().getIntExtra("image", 0);
        imageView.setImageResource(image);
        if(NetwrokUtil.isNetworkAvailable(this)) {
            SpeakerUtil.startSpeaking(this,VOICE);
        } else {
            new MaterialDialog.Builder(this)
                    .title("网络异常")
                    .content("请检查网络是否已经连接")
                    .positiveText("确定")
                    .show();
        }
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name = userInfo.getString("account", "");
//        healthInfoTask = new HealthInfoTask();
//        healthInfoTask.execute(name);

        thread = new HealthThread(name,this);
        thread.start();

    }

    @OnClick(R.id.sign_out_btn)
    public void signOut() {
        String command = "{\n" +
                "    \"command\": \"control\",\n" +
                "    \"status\": \"stop\"\n" +
                "}";
        Log.d(APP_LOG,command);
        new SendMessageTask(message -> {
           Log.d(APP_LOG,message);
        }).execute(command);
        thread.closeConnect();
        finish();
    }

}