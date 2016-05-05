package com.wuhk.note.activity.frame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuan.bigapple.lib.ioc.ViewUtils;

/**
 * Fra
 * Created by wuhk on 2016/5/5.
 */
public abstract class BaseFragment extends Fragment {
    private View mContentView;//界面切换缓存

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mContentView) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (null != parent) {
                // 父亲把我删除了把
                parent.removeView(mContentView);
            }
        } else {
            mContentView = LayoutInflater.from(getActivity()).inflate(initFragmentView(), null);
            ViewUtils.inject(this, mContentView);// 可以这样使用bigapple里面的注解注入
            initFragmentWidgets(inflater, container, savedInstanceState);
        }
        return mContentView;
    }

    /**
     * 子类实现，设置Fragment的View
     *
     * @return
     */
    protected abstract int initFragmentView();

    /**
     * 子类实现，在这里可以初始化数据
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    protected abstract void initFragmentWidgets(LayoutInflater inflater,
                                                ViewGroup container, Bundle savedInstanceState);

}
