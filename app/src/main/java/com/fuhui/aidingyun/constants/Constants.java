package com.fuhui.aidingyun.constants;

import java.util.ArrayList;

/**
 * <pre>
 *     project name: Ruisi
 *     author      : 李琼
 *     create time : 2017/10/3 上午2:27
 *     desc        : 描述--//Constants
 *     Reference   :
 *     modifier               :
 *     modification time      :
 *     modify remarks         :
 *     @version: --//1.0
 * </pre>
 */


public class Constants {
    public static final String IS_FIRST_LOGIN = "islogin";//是否初次登陆


    //对应的值不要改
    public static final String REQUESTPARAM_PLATFORM = "platform";
    public static final String REQUESTPARAM_USERID = "userId";
    public static final String REQUESTPARAM_TOKEN = "token";
    public static final String REQUESTPARAM_DEVICEID = "deviceId";


    public static final int USER_AUTH_FAILED = 21002; //用户token校验失败
    public static final int USER_NOT_LOGIN = 21003;   //用户没有登录
    public static final int USER_DIFF_LOGIN = 21004;  //用户异地登录
    public static final int USER_LOGIN_EXPIRED = 21005;//用户登录信息过期
    public static final int TOKEN_EXPIRED = 21007;     //token过期，请重新登录
    public static final int ACCOUNT_YET_FREEZE = 21012;//该用户已被冻结

    public static final int PAGE_COUNT = 15;
    public static final String DATABASE_NAME = "AIDINGYUN_DB";

    private static ArrayList<Integer> accountException = new ArrayList<Integer>();

    static {
        accountException.add(USER_AUTH_FAILED);
        accountException.add(USER_NOT_LOGIN);
        accountException.add(USER_DIFF_LOGIN);
        accountException.add(USER_LOGIN_EXPIRED);
        accountException.add(TOKEN_EXPIRED);
        accountException.add(ACCOUNT_YET_FREEZE);
    }

    public static ArrayList<Integer> getAccountException() {
        return accountException;
    }

}
