package com.fuhui.aidingyun.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * <pre>
 *     project name: aidingyun
 *     author      : 李琼
 *     e-mail      : 1265137718@qq.com
 *     create time : 2018/1/5 15:45
 *     desc        : 描述--//AutoStartBroadcastReceiver
 *     URL         : CSDN:http://blog.csdn.net/qq_22945151
 *                   GitHub:https://github.com/a1265137718
 *     Reference   ://
 *     modifier               :
 *     modification time      :
 *     modify remarks         :
 *
 *     @version: --//1.0
 * </pre>
 */


public class AutoStartBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AutoStartBroadcastReceiver";

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

        if (intent.getAction().equals(ACTION_BOOT)) {
            Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
        }
    }
}
