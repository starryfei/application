package com.hirain.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.hirain.app.R;
import com.hirain.app.adapter.CheckAdapter;
import com.hirain.app.entity.Mode;
import com.hirain.app.view.DriverCheckModeView;
import com.hirain.app.view.CheckModeView;
import com.hirain.app.view.VoiceCheckModeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hirain.app.common.Constants.CHECK_LIST;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckGridFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.mode_grid)
    GridView gridView;
//    @BindView(R.id.ultra_viewpager)
//    UltraViewPager ultraViewPager;
    private PagerAdapter adapter;

    public CheckGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckGridFragment newInstance(String param1, String param2) {
        CheckGridFragment fragment = new CheckGridFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_check_mode, container, false);
        unbinder = ButterKnife.bind(this, inflate);

//        initView();
        List<Mode> modes = new ArrayList<>(CHECK_LIST.size());
        for (String str: CHECK_LIST){
            modes.add(new Mode(str));

        }
        final Context context = getContext();
        gridView.setAdapter(new CheckAdapter(modes, context));
        DriverCheckModeView view1 = new DriverCheckModeView(context);
        CheckModeView checkModeView = new CheckModeView(context);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if(position ==0){
                view1.show(position);
            } else if(position == 5){
                VoiceCheckModeView modeView = new VoiceCheckModeView(context);
                modeView.show(position);
            }
            else {
                checkModeView.show(position);
            }

        });
        return inflate;
    }

//    private void initView() {
//        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//        adapter = new ImageAdapter();
//        ultraViewPager.setAdapter(adapter);
//        ultraViewPager.setMultiScreen(1.0f);
//        ultraViewPager.setFilterTouchesWhenObscured(true);
//        ultraViewPager.setInfiniteLoop(true);
//        ultraViewPager.setAutoScroll(10000);
//        //内置indicator初始化
//        ultraViewPager.initIndicator();
//
//        //设置indicator样式
//        ultraViewPager.getIndicator()
//                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
//                .setFocusColor(Color.GREEN)
//                .setNormalColor(Color.WHITE)
//                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
//        //设置indicator对齐方式
//        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//
//        ultraViewPager.getIndicator().build();
//
////        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

}