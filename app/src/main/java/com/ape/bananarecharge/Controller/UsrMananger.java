package com.ape.bananarecharge.Controller;

import android.content.Context;

import com.ape.bananarecharge.Datamodel.UsrInfo;

/**
 * Created by xiaoyue.wang on 2019/4/30.
 */

public class UsrMananger {

    public static void Login(Context context) {
        UsrInfo usrInfo = new UsrInfo(context);
        if (usrInfo.isHasLogin()) {

        }
    }
}
