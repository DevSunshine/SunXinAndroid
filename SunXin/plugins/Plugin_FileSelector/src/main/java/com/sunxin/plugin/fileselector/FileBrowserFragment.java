package com.sunxin.plugin.fileselector;
// Copyright (c) 2016 ${ORGANIZATION_NAME}. All rights reserved.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunshine.sunxin.App;
import com.sunshine.sunxin.BaseFragment;
import com.sunxin.plugin.fileselecter.R;
import com.sunxin.plugin.fileselector.adapter.FileBrowserAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 钟光燕 on 2016/8/16.
 * e-mail guangyanzhong@163.com
 */
public class FileBrowserFragment extends BaseFragment {
    private RecyclerView mFileListView;
    private FileBrowserAdapter mAdapter;
    private Stack<ViewStatue> statueStack = new Stack<>();
    private String mRootPath;
    private String mCurrentPath;

    private TextView mTitle;
    private TextView mBackTitle;
    private TextView mRightBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.root_file_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = getTitleView().setTitle("文件浏览");
        mFileListView = (RecyclerView) view.findViewById(R.id.id_root_file_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mFileListView.setLayoutManager(manager);
        mAdapter = new FileBrowserAdapter(this);
        mFileListView.setAdapter(mAdapter);
        mBackTitle = getTitleView().addBackBtn("返回", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        mRootPath = "/" ;
        openRootDir();
    }

    public void openDir(FileInfo fileInfo){
        openDir(fileInfo.path);
    }
    private void openDir(final String path) {
        //show loading

        /**
         * 异步操作
         */
        final List<FileInfo> fileInfos = new ArrayList<>();
        mCurrentPath = path ;
        if (mCurrentPath == null || TextUtils.equals(mCurrentPath, mRootPath)) {
            mBackTitle.setText("返回");
            mTitle.setText("手机内存");
        } else {
            mBackTitle.setText("上一级");
            String name = new File(path).getName() ;
            mTitle.setText(name);
        }
        App.runBackground(new Runnable() {
            @Override
            public void run() {
                FileUtil.getFiles(fileInfos, path);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setFile(fileInfos);
                    }
                });
            }
        });


        //end loading
    }

    public void handleFile(FileInfo fileInfo) {

    }

    private void openRootDir() {
        openDir(mRootPath);
    }


    private void back() {
        if (mCurrentPath == null || TextUtils.equals(mCurrentPath, mRootPath)) {
            getActivity().finish();
        } else {
            String parentPath = new File(mCurrentPath).getParent() ;
            openDir(parentPath);
        }
    }

}