package com.hirain.app.activity;

import com.github.clans.fab.FloatingActionButton;
import com.hirain.app.R;
import com.hirain.app.util.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class FloatButtonActivity extends BaseActivity{

    @BindView(R.id.open_door)
    FloatingActionButton openDoor;
    @BindView(R.id.close_door)
    FloatingActionButton closeDoor;
    @BindView(R.id.sign_out_user)
    FloatingActionButton signOutUser;

    @BindView(R.id.voice)
    FloatingActionButton voiceBtn;
    @BindView(R.id.tired)
    FloatingActionButton tiredBtn;
    @BindView(R.id.check)
    FloatingActionButton checkBtn;


    @OnClick(R.id.open_door)
    public void openDoor() {
        DialogUtil.openDoor(this);
    }

    @OnClick(R.id.close_door)
    public void closeDoor() {
        DialogUtil.closeDoor(this);
    }
    @OnClick(R.id.sign_out_user)
    public void signOutUser() {
        DialogUtil.signOutUser(this);
    }
    @OnClick(R.id.check)
    public void check(){
        DialogUtil.check(this);
    }
    @OnClick(R.id.voice)
    public void voice(){
        DialogUtil.voice(this);
    }
    @OnClick(R.id.tired)
    public void tireBtn(){
        DialogUtil.tired(this);
    }
}
