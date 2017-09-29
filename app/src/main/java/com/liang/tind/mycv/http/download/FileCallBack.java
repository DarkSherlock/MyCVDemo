package com.liang.tind.mycv.http.download;

import android.os.SystemClock;
import android.util.Log;

import com.liang.tind.mycv.http.RxBus;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by miya95 on 2016/12/5.
 */
public abstract class FileCallBack<T> {
    private static final String TAG = "FileCallBack";
    private String destFileDir;
    private String destFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long progress, long total);

    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();

            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        Subscriber<FileLoadEvent> subscriber = new Subscriber<FileLoadEvent>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                s.request(1);
                RxBus.getInstance().addSubscription(FileCallBack.this, s);
                Log.e(TAG, "onSubscribe: addSubscription" );
            }

            @Override
            public void onNext(FileLoadEvent fileLoadEvent) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        progress(fileLoadEvent.getBytesLoaded(), fileLoadEvent.getTotal());
                        SystemClock.sleep(30);
                        subscription.request(1);
                    }
                }.start();

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
                unsubscribe();
            }
        };
        RxBus.getInstance().doSubscribe(FileLoadEvent.class, subscriber);

    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {
        RxBus.getInstance().unSubscribe(this);
    }

}
