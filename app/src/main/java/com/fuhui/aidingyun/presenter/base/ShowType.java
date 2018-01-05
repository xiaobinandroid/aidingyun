package com.fuhui.aidingyun.presenter.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({ShowType.SHOW_NONE, ShowType.SHOW_TOAST})
public @interface ShowType {
    int SHOW_TOAST = 0;
    int SHOW_NONE = 1;
}