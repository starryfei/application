package com.hirain.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.hirain.app.activity.BaseActivity;
import com.hirain.app.activity.ModeActivity;
import com.hirain.app.activity.UserListActivity;
import com.hirain.app.entity.User;
import com.hirain.app.task.NucMessageThread;
import com.hirain.app.util.ImageBase64Utils;
import com.hirain.app.util.SpeakerUtil;
import com.hirain.app.util.TimeUtil;
import com.hirain.app.zmq.NucPubSubServer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.ValidatorEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hirain.app.common.Constants.AGE_INPUTS;
import static com.hirain.app.common.Constants.APP_LOG;
import static com.hirain.app.common.Constants.HEiGHT_INPUTS;
import static com.hirain.app.common.Constants.WEIGHT_INPUTS;
import static com.hirain.app.util.NetwrokUtil.isNetworkAvailable;

public class MainActivity extends BaseActivity {
    private NucPubSubServer nucServer = NucPubSubServer.getInstance();
    private static final int REQUEST_IMAGE_CAPTURE = 0X1;
    @BindView(R.id.button)
    Button loginBtn;
    @BindView(R.id.weightSpinner)
    MaterialSpinner weightSpinner;
    @BindView(R.id.ageSpinner)
    MaterialSpinner ageSpinner;
    @BindView(R.id.sexSpinner)
    MaterialSpinner sexSpinner;
    @BindView(R.id.heightSpinner)
    MaterialSpinner heightSpinner;
    @BindView(R.id.exist_btn)
    RoundButton existBtn;
    @BindView(R.id.imageView)
    RadiusImageView imageView;

    @BindView(R.id.user_name)
    ValidatorEditText userNameEdit;
    private String currentPhotoPath;

    @Override
    protected void onStart() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
        }
        super.onStart();

        nucServer.initServer();
        //讯飞调试日志开启
//        Setting.setShowLog(true);
        //初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=b73b6b61");
        SpeakerUtil.init(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        XUI.initTheme(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    /**
     * 测试和终端交互的代码测试register
     */
    @OnClick(R.id.button)
    public void buttonClick() {

        if (StringUtils.isBlank(currentPhotoPath)) {
            Toast.makeText(this, "请先拍摄照片！", Toast.LENGTH_LONG).show();
            return;
        }
        String inputValue = userNameEdit.getInputValue();
        if (StringUtils.isNoneBlank(inputValue)) {
            User user = initUser(inputValue);
            String s = JSONObject.toJSONString(user);
            Log.d(APP_LOG, s);
            //

//            Intent intent = new Intent(this, ModeActivity.class);
//            startActivity(intent);
            NucMessageThread nucMessageThread = new NucMessageThread();
            nucMessageThread.sendMessage(s);

            nucMessageThread.recvMessage(message -> {
                //  parseUser(message);
                JSONObject object = JSONObject.parseObject(message);
                String status = object.getString("status");
                Context applicationContext = getApplicationContext();
                if (StringUtils.equalsIgnoreCase(status, "success")) {
                    //Toast.makeText(applicationContext, "注册成功！", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("account", user.getName());
                    editor.commit();//提交修改

                    Intent intent = new Intent(applicationContext, ModeActivity.class);
                    startActivity(intent);
                } else if (StringUtils.equalsIgnoreCase(status, "exist")) {
                    Toast.makeText(applicationContext, "用户已经存在！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(applicationContext, "注册失败，请重新拍摄照片！", Toast.LENGTH_LONG).show();
                }
            }, "register");

        } else {
            Toast.makeText(this, "请输入用户昵称！", Toast.LENGTH_LONG).show();
        }
    }

    @NotNull
    private User initUser(String inputValue) {
        User user = new User();
        user.setCommand("register");
        user.setName(inputValue);
        String age = AGE_INPUTS.get(ageSpinner.getSelectedItem());
        user.setAge(age);
        String height = HEiGHT_INPUTS.get(heightSpinner.getSelectedItem());
        user.setHeight(height);
        String weight = WEIGHT_INPUTS.get(weightSpinner.getSelectedItem());
        user.setWeight(weight);
        user.time(TimeUtil.getCurrent());
        String sex = sexSpinner.getSelectedItem();
        if (StringUtils.equals(sex, "男")) {
            user.setSex("0");
        } else if (StringUtils.equals(sex, "女")) {
            user.setSex("1");
        } else {
            user.setSex("2");
        }
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap bitmap1 = ImageBase64Utils.matrixBitMap(bitmap);
        String s1 = ImageBase64Utils.encodeImageToString(bitmap1);

//        FileUtil.saveImageToStream(bitmap,"aa");
        user.setImage(s1);
        return user;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * 测试已有用户
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @OnClick(R.id.exist_btn)
    public void existBtnClick() {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//
//        FileUtil.saveImage(this,"jun",bitmap);
//        FileUtil.saveFile(this,"jun","ECM","aa","hhhhhhhhhhhhhh");

    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.imageView)
    public void takePhoto() {
        new MaterialDialog.Builder(this)
                .backgroundColorRes(R.color.dialog_color)
                .titleColorRes(R.color.white).positiveColorRes(R.color.dialog_ok_color)
                .content("是否拍摄照片")
                .positiveText("确定").onPositive((dialog, which) -> {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.hirain.app.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        }).negativeText("取消").show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            Log.e(APP_LOG, "size" + imageBitmap.getByteCount() / 1024 + "kb");
            //(Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}