package com.yueliao.core.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;
import com.yueliao.core.kit.Kits;
import com.yueliao.core.net.progress.ProgressHelper;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanglei on 2016/12/24.
 */

public class XApi {
    private static NetProvider sProvider = null;

    private Map<String, NetProvider> providerMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();

    public static final long connectTimeoutMills = 4 * 1000L;
    public static final long readTimeoutMills = 4 * 1000L;

    private static XApi instance;

    private XApi() {

    }

    public static XApi getInstance() {
        if (instance == null) {
            synchronized (XApi.class) {
                if (instance == null) {
                    instance = new XApi();
                }
            }
        }
        return instance;
    }


    public static <S> S get(String baseUrl, Class<S> service) {
        return getInstance().getRetrofit(baseUrl, true).create(service);
    }

    public static void registerProvider(NetProvider provider) {
        XApi.sProvider = provider;
    }

    public static void registerProvider(String baseUrl, NetProvider provider) {
        getInstance().providerMap.put(baseUrl, provider);
    }


    public Retrofit getRetrofit(String baseUrl, boolean useRx) {
        return getRetrofit(baseUrl, null, useRx);
    }


    public Retrofit getRetrofit(String baseUrl, NetProvider provider, boolean useRx) {
        if (Kits.Empty.check(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) {
            return retrofitMap.get(baseUrl);
        }

        if (provider == null) {
            provider = providerMap.get(baseUrl);
            if (provider == null) {
                provider = sProvider;
            }
        }
        checkProvider(provider);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addConverterFactory(GsonConverterFactory.create());
        if (useRx) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }

        Retrofit retrofit = builder.build();
        retrofitMap.put(baseUrl, retrofit);
        providerMap.put(baseUrl, provider);

        return retrofit;
    }

    private OkHttpClient getClient(String baseUrl, NetProvider provider) {
        if (Kits.Empty.check(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (clientMap.get(baseUrl) != null) {
            return clientMap.get(baseUrl);
        }

        checkProvider(provider);
        // 构建 OkHttpClient 时,将 OkHttpClient.Builder() 传入 with() 方法,进行拦截器配置
        OkHttpClient.Builder builder = ProgressManager.getInstance().with(new OkHttpClient.Builder());

        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);

        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

        //添加stetho的拦截器->Facebook的网络监听工具 方便开发时拦截查看返回的数据
        builder.addNetworkInterceptor(new StethoInterceptor());

        RequestHandler handler = provider.configHandler();
        if (handler != null) {
            builder.addInterceptor(new XInterceptor(handler));
        }

        if (provider.dispatchProgressEnable()) {
            builder.addInterceptor(ProgressHelper.get().getInterceptor());
        }

        Interceptor[] interceptors = provider.configInterceptors();
        if (!Kits.Empty.check(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (provider.configLogEnable()) {
            LogInterceptor logInterceptor = new LogInterceptor();
            builder.addInterceptor(logInterceptor);
        }

        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);
        providerMap.put(baseUrl, provider);
        return client;
    }


    private void checkProvider(NetProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static NetProvider getCommonProvider() {
        return sProvider;
    }

    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    public Map<String, OkHttpClient> getClientMap() {
        return clientMap;
    }

    public static void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }

    /**
     * 线程切换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 线程切换
     *
     * @return
     */


    public static <T extends IModel> FlowableTransformer<T, T> getRetryWhen() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                // 当前已重试次数
                final int[] currentRetryCount = {0};
                final int[] MAX_CONNECT_COUNT = {5};// 可重试次数
                return upstream.retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(@NonNull Flowable<Throwable> throwable) throws Exception {
                        // 输出异常信息
                        Logger.d("发生异常 = " + throwable.toString());

                        /**
                         * 需求：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */

                        /**
                         * 限制重试次数
                         * 即，当已重试次数 < 设置的重试次数，才选择重试
                         */
                        if (currentRetryCount[0] < MAX_CONNECT_COUNT[0]) {

                            currentRetryCount[0]++;// 记录重试次数
                            /**
                             * 设置重试等待时间
                             * 需求：实现重试
                             * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                             *
                             * 需求：延迟1段时间再重试
                             * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                             *
                             * 需求：遇到的异常越多，时间越长
                             * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                             */
                            return Flowable.just(1).delay(1000 + currentRetryCount[0] * 1000, TimeUnit.MILLISECONDS);
                        } else {
                            // 若重试次数已 > 设置重试次数，则不重试 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                            return Flowable.error(new Throwable("重试次数已超过设置次数 = " + MAX_CONNECT_COUNT[0] + "，即 不再重试"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T extends IModel> FlowableTransformer<T, T> getApiTransformer() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(T model) throws Exception {

                        if (model == null || model.isNull()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.NoDataError));
                        } else if (model.isAuthError()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.AuthError));
                        } else if (model.isBizError()) {
                            return Flowable.error(new NetError(model.getErrorMsg(), NetError.BusinessError));
                        } else {
                            return Flowable.just(model);
                        }
                    }
                });
            }
        };
    }


}
