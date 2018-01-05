package com.yueliao.core.base;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.droidlover.xdroidmvp.R;

/**
 * <pre>
 *     project name: Ruisi
 *     author      : 李琼
 *     e-mail      : 1265137718@qq.com
 *     create time : 2017/12/1 18:57
 *     desc        : 描述--//GlideManger
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


public class GlideManger {

    public static GlideManger getInstance() {
        return GlideMangerHolder.instance;
    }

    private static class GlideMangerHolder {
        static GlideManger instance = new GlideManger();
    }

    public RequestOptions userPicOptions = RequestOptions.circleCropTransform().placeholder(R.drawable.default_head)
            .error(R.drawable.default_head).diskCacheStrategy(DiskCacheStrategy.ALL);

    public RequestOptions imgOptions = RequestOptions.errorOf(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL);

}
