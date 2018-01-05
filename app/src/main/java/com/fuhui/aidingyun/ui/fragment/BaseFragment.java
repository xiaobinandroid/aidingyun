package com.fuhui.aidingyun.ui.fragment;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fuhui.aidingyun.R;
import com.fuhui.aidingyun.presenter.base.BaseFragmentPresent;
import com.yueliao.core.mvp.XLazyFragment;
import com.yueliao.core.net.NetError;


/**
 * **
 * author: 李琼
 * description:单个请求无下拉刷新操作的Fragment基类
 * version: 1.0
 */
public abstract class BaseFragment<P extends BaseFragmentPresent<? extends BaseFragment>> extends XLazyFragment<P> {

    protected void initToolBar(boolean isShowBack, String text) {
        View toolbar = getRootView().findViewById(R.id.myToolBar);
        if (toolbar != null) {
            ((TextView) toolbar.findViewById(R.id.title)).setText(text);
            if (isShowBack) {
                toolbar. findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            } else {
                toolbar.findViewById(R.id.logo).setVisibility(View.GONE);
            }
        }
    }

    protected RecyclerView initRecyclerView(RecyclerView.LayoutManager mLayoutManager,
                                            RecyclerView.Adapter adapter,
                                            RecyclerView.ItemAnimator itemAnimator) {
        RecyclerView mRecyclerView = getRootView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(adapter);
        return mRecyclerView;
    }

    public void showError(NetError error) {
        if (error != null) {
            StringBuilder errorMsg = new StringBuilder();
            switch (error.getType()) {
                case NetError.ParseError:
                    errorMsg.append("数据解析异常");
                    break;
                case NetError.AuthError:
                    errorMsg.append("身份验证异常");
                    break;

                case NetError.BusinessError:
                    errorMsg.append("业务异常");
                    break;

                case NetError.NoConnectError:
                    errorMsg.append("网络无连接");
                    break;

                case NetError.NoDataError:
                    errorMsg.append("数据为空");
                    break;
                case NetError.OtherError:
                    errorMsg.append("其他异常");
                    break;
                default:
                    errorMsg.append("其他异常");
                    break;
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_rv;
    }


}
