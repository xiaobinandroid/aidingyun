package com.yueliao.core;

import com.yueliao.core.kit.Kits;
import com.yueliao.core.router.Router;

/**
 * Created by wanglei on 2016/12/4.
 */

public class DroidConf {
    // #cache
    public static String CACHE_SP_NAME = "config";
    public static String CACHE_DISK_DIR = "cache";

    // #router
    public static int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static int ROUTER_ANIM_EXIT = Router.RES_NONE;

    // #dev model
    public static boolean DEV = true;

    /**
     * conf cache
     *
     * @param spName
     * @param diskDir
     */
    public static void configCache(String spName, String diskDir) {
        if (!Kits.Empty.check(spName)) {
            CACHE_SP_NAME = spName;
        }
        if (!Kits.Empty.check(diskDir)) {
            CACHE_DISK_DIR = diskDir;
        }
    }

    /**
     * config dev
     *
     * @param isDev
     */
    public static void devMode(boolean isDev) {
        DEV = isDev;
    }

}
