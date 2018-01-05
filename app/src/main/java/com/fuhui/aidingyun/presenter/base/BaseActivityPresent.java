package com.fuhui.aidingyun.presenter.base;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.fuhui.aidingyun.constants.Constants;
import com.fuhui.aidingyun.ui.activity.BaseActivity;
import com.fuhui.aidingyun.ui.activity.HomeActivity;
import com.orhanobut.logger.Logger;
import com.yueliao.core.mvp.XPresent;

/**
 * <pre>
 *     project name: androidclient
 *     author      : 李琼
 *     e-mail      : 1265137718@qq.com
 *     create time : 2017/8/31 14:27
 *     desc        : 描述--//BaseActivityPresent
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


public abstract class BaseActivityPresent<V extends BaseActivity> extends XPresent<V> {

    public void authAccount(int errorCode, String errorMsg, Activity mActivity) {
        authAccount(errorCode, errorMsg, mActivity, ShowType.SHOW_TOAST);
    }

    public void authAccount(int errorCode, String errorMsg, Activity mActivity, int showType) {
        if (Constants.getAccountException().contains(errorCode)&&mActivity instanceof HomeActivity) {
            //检测到账号异地登录后注销当前账号信息返回登录界面。
            Intent intent = new Intent(mActivity, HomeActivity.class);
            intent.putExtra(HomeActivity.TAG_LOGIN_EXCEPTION, true);
            intent.putExtra(HomeActivity.TAG_LOGIN_EXCEPTION_MSG, errorMsg);
            intent.putExtra(HomeActivity.TAG_LOGIN_EXCEPTION_ERROR_CODE, errorCode);
            mActivity.startActivity(intent);
        } else {
            switch (showType) {
                case ShowType.SHOW_TOAST:
                    Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
                    break;

                case ShowType.SHOW_NONE:
                    Logger.d("ERROR_CODE: " + errorCode + " ERROR_MSG: " + errorMsg);
                    break;
                default:
                    break;
            }
        }
    }
}
