package com.hirain.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.hirain.app.R;
import com.hirain.app.task.HealthThread;
import com.hirain.app.task.SendMessageTask;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hirain.app.common.Constants.APP_LOG;

@RequiresApi(api = Build.VERSION_CODES.Q)
/**
 * 唇音融合体验
 */
public class VoiceExperienceActivity extends FloatButtonActivity {
    @BindView(R.id.sign_out_btn)
    Button signOutBtn;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.express_image)
    RadiusImageView imageView;
//    @BindView(R.id.menu_yellow)
//    FloatingActionMenu actionMenu;
//    @BindView(R.id.move)
//    FloatingActionButton moveActionBtn;
    private HealthThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);
        ButterKnife.bind(this);
        int image = getIntent().getIntExtra("image", 0);
        imageView.setImageResource(image);
//        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
//        String name = userInfo.getString("account", "");
        String name = getName();
        textView.setText(name+": 体验中...");
        thread = new HealthThread(name,this);
        thread.start();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//       healthInfoTask.cancelTask();

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

        try {
            thread.closeConnect();
        } catch (Exception e){
            Log.e(APP_LOG,"stop thread");
        }
        finish();
    }

}