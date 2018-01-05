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
 *     create time : 2018/1/5 17:00
 *     desc        : 描述--//ShutdownBroadcastReceiver
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


public class ShutdownBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ShutdownBroadcastReceiver";

    private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Shut down this system, ShutdownBroadcastReceiver onReceive()");

        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            Log.i(TAG, "ShutdownBroadcastReceiver onReceive(), Do thing!");
        }
    }
}
