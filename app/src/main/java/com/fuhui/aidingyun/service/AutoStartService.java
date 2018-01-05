package com.fuhui.aidingyun.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * <pre>
 *     project name: aidingyun
 *     author      : 李琼
 *     e-mail      : 1265137718@qq.com
 *     create time : 2018/1/5 15:45
 *     desc        : 描述--//AutoStartService
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


public class AutoStartService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub


        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("TAG2","test service");
    }
}
