package com.hirain.app.activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.clans.fab.FloatingActionButton;
import com.hirain.app.R;
import com.hirain.app.util.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hirain.app.common.CommandConstants.CLOSE_DOOR_COMMAND;
import static com.hirain.app.common.CommandConstants.DRIVER_CHECK_COMMAND;
import static com.hirain.app.common.CommandConstants.OPEN_DOOR_COMMAND;
import static com.hirain.app.common.CommandConstants.TIRED_COMMAND;

public class FloatButtonActivity extends BaseActivity{

    @BindView(R.id.open_door)
    FloatingActionButton openDoor;
    @BindView(R.id.close_door)
    FloatingActionButton closeDoor;
    @BindView(R.id.sign_out_user)
    FloatingActionButton signOutUser;

//    @BindView(R.id.voice)
//    FloatingActionButton voiceBtn;
    @BindView(R.id.tired)
    FloatingActionButton tiredBtn;
    @BindView(R.id.check)
    FloatingActionButton checkBtn;
    @BindView(R.id.move)
    FloatingActionButton moveBrn;


    @OnClick(R.id.open_door)
    public void openDoor() {
        DialogUtil.commonDialog(this,"是否打开车门", OPEN_DOOR_COMMAND);
    }

    @OnClick(R.id.close_door)
    public void closeDoor() {
        DialogUtil.commonDialog(this,"是否关闭车门", CLOSE_DOOR_COMMAND);
    }
    @OnClick(R.id.sign_out_user)
    public void signOutUser() {
        DialogUtil.signOutUser(this);
    }
    @OnClick(R.id.check)
    public void check(){
        DialogUtil.commonDialog(this,"是否开启乘员健康检测", DRIVER_CHECK_COMMAND);
    }
//    @OnClick(R.id.voice)
//    public void voice(){
//        DialogUtil.commonDialog(this,"是否开启唇音融合", VOICE_COMMAND);
//    }
    @OnClick(R.id.tired)
    public void tireBtn(){
        DialogUtil.commonDialog(this,"是否开启疲劳检测", TIRED_COMMAND);
    }

    @OnClick(R.id.move)
    public void move(){
        DialogUtil.moveDialog(this);
    }

    public String getName(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");
        return account;
    }

}
