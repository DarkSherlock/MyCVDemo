package com.liang.tind.mycv.http;

import android.app.Application;
import android.content.Context;

import com.liang.tind.mycv.BuildConfig;
import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.http.download.ApiInfo;
import com.liang.tind.mycv.http.download.FileCallBack;
import com.liang.tind.mycv.http.download.FileSubscriber;
import com.liang.tind.mycv.http.download.ProgressInterceptor;
import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.model.JobInfoBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sherlock on 2017/9/2.
 */

public class RetrofitClient {
    private ApiServices apiService;
    private ApiInfo mDownLoadService;
    private static RetrofitClient mRetrofitClient;
    private static final int DEFAULT_TIMEOUT = 20;
    private Context mContext;

    public synchronized static RetrofitClient getInstance(Context context) {
        if (mRetrofitClient == null) {
            mRetrofitClient = new RetrofitClient(context);
        }
        return mRetrofitClient;
    }

    private RetrofitClient(Context context) {
        if (context instanceof Application) {
            mContext = context;
        } else {
            mContext = context.getApplicationContext();//将context 转化为application 避免内存泄漏
        }
    }

    public ApiServices getApiService() {
        if (apiService == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder
                    .addNetworkInterceptor(new CaheInterceptor(mContext))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(httpLoggingInterceptor);
            }

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.URL.BASE_URL)
                    .build();

            apiService = retrofit.create(ApiServices.class);
        }

        return apiService;
    }

    private ApiInfo getDownLoadApiService() {
        if (mDownLoadService == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder
                    .addInterceptor(new ProgressInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(httpLoggingInterceptor);
            }

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URL.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mDownLoadService = retrofit.create(ApiInfo.class);
        }

        return mDownLoadService;
    }
    public void get(String url, Observer<JobInfoBean> consumer) {
        getApiService().getJobInfo(url)
                .compose(applySchedulers(schedulersTransformer()))
                .subscribe(consumer);
    }

    public void getCvInfo(String url, Observer<CvInfoBean> observer){
        getApiService().getCvInfo(url)
                       .compose(applySchedulers(schedulersTransformer()))
                       .subscribe(observer);
    }
    public void download(String url, final FileCallBack<ResponseBody> callBack) {
        getDownLoadApiService().download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(callBack::saveFile)
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<>(callBack, mContext));
    }

    private static ObservableTransformer schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final ObservableTransformer IO_TRANSFORMER = new ObservableTransformer() {
        @Override public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };

    public static final <T> ObservableTransformer<T, T> applySchedulers(ObservableTransformer transformer){
        return (ObservableTransformer<T, T>)transformer;
    }


//    public <T> ObservableTransformer<BaseBean<T>, T> transformer() {
//
//        return new ObservableTransformer() {
//            @Override
//            public ObservableSource apply(@NonNull Observable upstream) {
//                return ((Observable) upstream).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
//            }
//
//        };
//    }

//    private class HandleFuc<T> implements Function<ResponseBody, T> {
//        @Override
//        public T call(BaseResponse<T> response) {
//            if (!response.isOk()) throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg(): "");
//            return response.getData();
//        }
//
//        @Override
//        public <T extends BaseBean<T>>apply(@NonNull ResponseBody responseBody) throws Exception {
//            Gson gson = new Gson();
//            return gson.fromJson(responseBody.toString(), T);
//        }
//    }
}
