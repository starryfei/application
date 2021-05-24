package com.hirain.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.tabs.TabLayout;
import com.hirain.app.R;
import com.hirain.app.fragment.CheckGridFragment;
import com.hirain.app.fragment.ModeGridFragment;
import com.hirain.app.task.LostThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModeActivity extends FloatButtonActivity {


    @BindView(R.id.menu_yellow)
    FloatingActionMenu floatingActionMenu;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
//


    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> titles = Arrays.asList("模式体验", "功能体验");

    private ModeActivity modeActivity;
    private LostThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
//        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
//        fragmentList.add(new ModeFragment());
        fragmentList.add(new ModeGridFragment());
        fragmentList.add(new CheckGridFragment());
        floatingActionMenu.setClosedOnTouchOutside(true);


        modeActivity = this;
        initTabLayout();
        thread = new LostThread(this);
        thread.start();



    }

    private void initTabLayout() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        thread.closeConnect();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return true;
    }




}