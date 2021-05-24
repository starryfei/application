package com.hirain.app.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hirain.app.R;
import com.hirain.app.adapter.ModePagerAdapter;
import com.hirain.app.common.Constants;
import com.hirain.app.view.DynamicView;
import com.hirain.app.view.DynamicViewFactory;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Deprecated
public class ModeFragment extends Fragment {
    @BindView(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;
    @BindView(R.id.text)
    TextView textView;
//    @BindView(R.id.mode_select)
//    RoundButton modeSelectBtn;
    @BindView(R.id.dynamic_layout)
    LinearLayout dynamicLayout;

    private LayoutInflater inflater;

    private PagerAdapter adapter;

    private Unbinder unbinder;
    private FragmentActivity mContext;
    private View view;

    public ModeFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ModeFragment newInstance() {
        ModeFragment fragment = new ModeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;

        view = inflater.inflate(R.layout.fragment_mode, container, false);
        unbinder = ButterKnife.bind(this, view);
        this.mContext = getActivity();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView();
//        viewPagerListener();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        DynamicViewFactory.INSTANCE.clearView();
    }

    private void initView() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new ModePagerAdapter(Constants.MODL_LIST);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.setMultiScreen(0.35f);
        ultraViewPager.setFilterTouchesWhenObscured(true);
        ultraViewPager.setInfiniteLoop(true);
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

        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());
    }

    private void viewPagerListener() {
        ultraViewPager.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            dynamicLayout.removeAllViews();
            int currentItem = ultraViewPager.getCurrentItem();
            textView.setText(Constants.MODL_LIST.get(currentItem));
//            DynamicView dynamicView = DynamicViewFactory.INSTANCE.createDynamicView(currentItem,getContext());
//            LinearLayout view = dynamicView.createView(inflater);
//            //设置加载布局的大小与位置
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            dynamicLayout.addView(view, lp);


        });

    }


}