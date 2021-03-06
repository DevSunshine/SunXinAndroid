package com.sunshine.sun.lib.socket.toolbox;
// Copyright (c) 2016 ${ORGANIZATION_NAME}. All rights reserved.

import android.util.Log;

import com.sunshine.sun.lib.socket.SSClient;
import com.sunshine.sun.lib.socket.SSClientManager;
import com.sunshine.sun.lib.socket.SSClientMode;
import com.sunshine.sun.lib.socket.bean.UserAccount;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 钟光燕 on 2016/7/14.
 * e-mail guangyanzhong@163.com
 */
public class SSSocket {
    private UserAccount mUserAccount;

    public static SSSocket instance() {
        return InnerInstance.instance;
    }

    public static class InnerInstance {
        private static SSSocket instance = new SSSocket();
    }


    public synchronized void initSocket(UserAccount account) {
        mUserAccount = account;
        requestClient(SSClientMode.primary);

    }

    public synchronized void logoutSocket() {
        mUserAccount = null;
        SSClientManager.instance().closeClient();
    }

    public SSClient requestClient(SSClientMode mode) {
        SSClient client = null;
        if (mUserAccount == null) {
            if (mode == SSClientMode.loginMode) {
                // TODO: 16/8/2 登录的 ip 和端口需要确认
                try {
                    String host = InetAddress.getByName("www.baidu.com").getHostAddress();
                    Log.v("zgy","====baidu==host========"+host) ;
                    client = SSClientManager.instance().connect(host, 9527, mode);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // TODO: 16/8/2 如果是二级链路，不能这样操作 
            SSClientManager.instance().closeClient(mode);
            String address = mUserAccount.getAddress();
            int port = mUserAccount.getPort();
            client = SSClientManager.instance().connect(address, port, mode);
        }

        return client;
    }

    public SSClient getPrimaryClient() {
        return SSClientManager.instance().getPrimaryClient();
    }

    public SSClient getSecondlyClient() {
        return SSClientManager.instance().getSecondlyClient();
    }
    public SSClient getLoginClient() {
        return SSClientManager.instance().getLoginClient();
    }
}
