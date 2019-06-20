package com.example.wechattab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wechattab.MainActivity;
import com.example.wechattab.R;
import com.example.wechattab.utils.LogUtil;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static interface OnTitleClickListener{
        void onClick(String title);
    }

    private OnTitleClickListener mListener;

    public void setOnTitleClickListener(OnTitleClickListener listener){
        mListener = listener;
    }

    //第三步
    public static TabFragment newInstance(String title){//静态的类似工厂方法
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);//界面的销毁和恢复过程中，有着不可替代的优势
        return tabFragment;
    }

    @Override   //第四步
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argumentsBundle = getArguments();
        if(argumentsBundle != null){
            mTitle = argumentsBundle.getString(BUNDLE_KEY_TITLE,"");
        }
        LogUtil.d("1.onCreate, title = "+ mTitle);
    }

    @Nullable
    @Override   //第一步
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("2.onCreateView, title = "+ mTitle);
        return inflater.inflate(R.layout.fragment_tab,container,false);
    }

    @Override   //第二步
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        LogUtil.d("3.onViewCreated, title = "+ mTitle);
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//2.通信2：fragment --> activity的方法
                //方法一：获取activity对象，再调用其方法
//                MainActivity mainActivity = (MainActivity)getActivity();
//                mainActivity.changeWeChatTab("F调A()");
                if(mListener != null){
                    mListener.onClick("微信changed！");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("4.onDestroyView, title = "+ mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("5.onDestroy, title = "+ mTitle);
    }

    public void changeTitle(String title){
        if(!isAdded()){
            return;
        }
        mTvTitle.setText(title);
    }
}
