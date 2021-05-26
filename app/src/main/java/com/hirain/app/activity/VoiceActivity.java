package com.hirain.app.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hirain.app.R;
import com.hirain.app.task.HealthThread;
import com.hirain.app.task.SendMessageTask;
import com.hirain.app.util.NetwrokUtil;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hirain.app.common.Constants.APP_LOG;

@RequiresApi(api = Build.VERSION_CODES.Q)

public class VoiceActivity extends FloatButtonActivity {
    @BindView(R.id.voice_input_btn)
    Button voiceInputBtn;
    @BindView(R.id.exit_btn)
    Button exitBtn;
    @BindView(R.id.image_view)
    RadiusImageView imageView;


    private  RecognizerDialog mIatDialog;
    private VoiceActivity voiceActivity;
    private HealthThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        voiceActivity = this;

        int image = getIntent().getIntExtra("image", 0);
        imageView.setImageResource(image);

        InitListener listener = i -> {
        };
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
       mIatDialog = new RecognizerDialog(this, listener);
       setParameter();
       voiceInputBtn.setOnClickListener(v -> {
           if(NetwrokUtil.isNetworkAvailable(voiceActivity)) {
               voiceInput();

           } else {
               new MaterialDialog.Builder(voiceActivity)
                       .title("网络异常")
                       .content("请检查网络是否已经连接")
                       .positiveText("确定")
                       .show();
           }
           String command = "{\n" +
                   "    \"command\": \"voice\",\n" +
                   "    \"voice\": \"pickmeup\"\n" +
                   "}";
           new SendMessageTask(message -> {
               Log.d(APP_LOG,message);
           }).execute(command);

       });


        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name = userInfo.getString("account", "");
//        healthInfoTask = new HealthInfoTask();
//        healthInfoTask.execute(name);

        thread = new HealthThread(name,this);
        thread.start();
    }
    /**
     * 解析语音json
     */
    public String parseJsonVoice(String resultString) {
        JSONObject jsonObject = JSON.parseObject(resultString);
        JSONArray jsonArray = jsonObject.getJSONArray("ws");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONArray jsonArray1 = jsonObject1.getJSONArray("cw");
            JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
            String w = jsonObject2.getString("w");
            stringBuffer.append(w);
        }
        Log.d(APP_LOG, stringBuffer.toString());


        return stringBuffer.toString();
    }


    @OnClick(R.id.exit_btn)
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

    public void voiceInput(){

        //开始识别并设置监听器
        mIatDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                String resultString = recognizerResult.getResultString();
                System.out.println(resultString);
                String s = parseJsonVoice(resultString);

            }

            @Override
            public void onError(SpeechError speechError) {

                Log.e(APP_LOG,speechError.getErrorDescription());
            }
        });

        //显示听写对话框
        mIatDialog.show();
    }

    private void setParameter() {
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数
        mIatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIatDialog.setParameter(SpeechConstant.SUBJECT, null);
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIatDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //设置语音输入语言，zh_cn为简体中文
        mIatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIatDialog.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIatDialog.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIatDialog.setParameter(SpeechConstant.ASR_PTT, "1");
    }

}