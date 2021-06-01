package com.hirain.app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hirain.app.R;
import com.hirain.app.adapter.UserListAdapter;
import com.hirain.app.entity.User;
import com.hirain.app.zmq.NucPubSubServer;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserListActivity extends FloatButtonActivity {
    private NucPubSubServer nucServer = NucPubSubServer.getInstance();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.search_user)
//    SearchView searchView;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.compare_by_name)
    TextView textView;
    private UserListAdapter adapter;
    private ArrayList<User> users;
    private UserListActivity userListActivity;
    boolean isSoft = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListActivity = this;
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        users = new ArrayList<>();
//        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        LoadUserAsyncTask asyncTask = new LoadUserAsyncTask();
        asyncTask.execute("");
        new Thread(()->{
            try {
                asyncTask.get(10, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
//                e.printStackTrace();
                asyncTask.getmMiniLoadingDialog().cancel();
                Toast.makeText(userListActivity, "请求超时", Toast.LENGTH_LONG).show();

            }
        }).start();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        //默认就是搜索框展开
        searchView.setIconified(false);
        //一直都是搜索框，搜索图标在输入框左侧（默认是内嵌的）
        searchView.setIconifiedByDefault(true);
        searchView.clearFocus();
        return true;
    }
    @OnClick(R.id.back)
    public void back(){
        finish();
    }


    class LoadUserAsyncTask extends AsyncTask<String, Void, Object>{
        MiniLoadingDialog mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(userListActivity);


        @Override
        protected void onPreExecute() {
            mMiniLoadingDialog.updateMessage("加载用户信息中...");
            mMiniLoadingDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... strings) {
            String command = "{  \n" +
                    "    \"command\": \"search\"\n" +
                    "}";
            nucServer.sendMessage(command);
            nucServer.recvMessage(UserListActivity.this::parseUser,"search");
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            mMiniLoadingDialog.cancel();
            adapter = new UserListAdapter(userListActivity,users);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            super.onPostExecute(o);
        }

        public MiniLoadingDialog getmMiniLoadingDialog() {
            return mMiniLoadingDialog;
        }
    }
    private void parseUser(String message) {
        JSONObject object = JSONObject.parseObject(message);
        JSONArray result = object.getJSONArray("result");
        for (int i = 0; i < result.size(); i++) {
            JSONObject jo = result.getJSONObject(i);
            String name = jo.getString("name");
            String time = jo.getString("time");
            String sex = jo.getString("sex");
            String age = jo.getString("age");
            String weight = jo.getString("weight");
            String height = jo.getString("height");
            String image = jo.getString("image");
            User user = new User();
            user.setName(name);
            user.setSex(sex);
            user.time(time);
            user.setAge(age);
            user.setWeight(weight);
            user.setHeight(height);
            user.setImage(image);

            users.add(user);

        }
        //adapter.setUsers(users);
    }


    @OnClick(R.id.compare_by_name)
    public void onClickByName(){
        if (!isSoft) {
            Collections.sort(users);
            isSoft = true;
        } else {
            isSoft =false;
            Collections.reverse(users);
        }
        adapter.notifyDataSetChanged();
    }

}