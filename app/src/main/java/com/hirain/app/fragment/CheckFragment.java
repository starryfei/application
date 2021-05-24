package com.hirain.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.hirain.app.R;
import com.hirain.app.activity.ExperienceActivity;
import com.hirain.app.adapter.ModePagerAdapter;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hirain.app.common.Constants.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Deprecated
public class CheckFragment extends Fragment{
    @BindView(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.btn_start)
    RoundButton startSelectBtn;
    @BindView(R.id.dynamic_layout)
    LinearLayout dynamicLayout;

    private LayoutInflater inflater;

    private PagerAdapter adapter;
    private UltraViewPager.Orientation gravity_indicator;

    private Unbinder unbinder;
    private FragmentActivity mContext;
    private View view;

    public CheckFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static CheckFragment newInstance() {
        CheckFragment fragment = new CheckFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;

        view = inflater.inflate(R.layout.fragment_check, container, false);
        unbinder = ButterKnife.bind(this, view);
        this.mContext = getActivity();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        viewPagerListener();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        DynamicViewFactory.INSTANCE.clearView();
    }

    private void initView() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new ModePagerAdapter(CHECK_LIST);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.setMultiScreen(0.35f);
//        ultraViewPager.setItemRatio(2f);
//        ultraViewPager.setRatio(2.0f);
//        ultraViewPager.setMaxHeight(500);
//        ultraViewPager.setAutoMeasureHeight(true);
        ultraViewPager.setInfiniteLoop(true);

        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;
        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());
        //内置indicator初始化
        ultraViewPager.initIndicator();
        //设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        ultraViewPager.getIndicator().build();
    }

    private void viewPagerListener() {
        ultraViewPager.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            dynamicLayout.removeAllViews();
            int currentItem = ultraViewPager.getCurrentItem();
            textView.setText(CHECK_LIST.get(currentItem));
        });
    }

    @OnClick(R.id.btn_start)
    public void startBtn(){
        int currentItem = ultraViewPager.getCurrentItem();
        String s = CHECK_LIST.get(currentItem);

        Intent intent = new Intent(getContext(), ExperienceActivity.class);
        getContext().startActivity(intent);

    }


}