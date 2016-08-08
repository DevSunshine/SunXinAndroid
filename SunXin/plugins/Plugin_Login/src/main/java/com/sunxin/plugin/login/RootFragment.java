package com.sunxin.plugin.login;
// Copyright (c) 2016 ${ORGANIZATION_NAME}. All rights reserved.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sunshine.sun.lib.socket.SSResponse;
import com.sunshine.sun.lib.socket.request.SSITaskListener;
import com.sunshine.sun.lib.socket.request.SSTask;
import com.sunshine.sunxin.BaseFragment;
import com.sunshine.sunxin.TestJava;

/**
 * Created by 钟光燕 on 2016/8/4.
 * e-mail guangyanzhong@163.com
 */
public class RootFragment extends BaseFragment {

    private TextView mTextView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("zgy","======onCreateView=========z钟光燕====") ;
        return inflater.inflate(R.layout.plugin_login_layout,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getTitleView().hide();
        Log.v("zgy","======onViewCreated=====中心宏观========") ;
        view.findViewById(R.id.id_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestJava.login("jxxfzgy", "jxxfzgy", new SSITaskListener() {
                            @Override
                            public void onProgress(SSTask ssTask, int i, int i1) {

                            }

                            @Override
                            public void onComplete(SSTask ssTask, SSResponse ssResponse) {

                            }

                            @Override
                            public void onError(SSTask ssTask, int i) {

                            }
                        });
                    }
                }).start();

                Toast.makeText(getActivity().getApplicationContext(),"SB, 点我干嘛呢，哈哈！",Toast.LENGTH_LONG).show();

            }
        });
    }
}
