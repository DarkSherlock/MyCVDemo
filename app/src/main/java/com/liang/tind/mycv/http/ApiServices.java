package com.liang.tind.mycv.http;

import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.model.JobInfoBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Sherlock on 2017/9/2.
 */

public interface ApiServices {
    @GET("json/{url}")
    Observable<JobInfoBean> getJobInfo(@Path("url") String url);

    @GET("json/{url}")
    Observable<CvInfoBean> getCvInfo(@Path("url") String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    @Streaming
    @GET("json/{url}")
    Observable<Response<ResponseBody>> download(@Header("Range") String range, @Url String url);

}
