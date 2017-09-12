package com.liang.tind.mycv.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Tamic on 2016-07-11.
 */
public class DownLoadManager {

    private CallBack callBack;

    private static final String TAG = "DownLoadManager";

    private static final String APK_CONTENT_TYPE = "application/vnd.android.package-archive";

    private static final String PNG_CONTENT_TYPE = "image/png";

    private static final String JPG_CONTENT_TYPE = "image/jpg";

    private static  String mFileSuffix ="";

    private Handler handler;

    public DownLoadManager(CallBack callBack) {
        this.callBack = callBack;
    }

    private static DownLoadManager sInstance;

    /**
     *DownLoadManager getInstance
     */
    public static synchronized DownLoadManager getInstance(CallBack callBack) {
        if (sInstance == null) {
            sInstance = new DownLoadManager(callBack);
        }
        return sInstance;
    }



    public boolean  writeResponseBodyToDisk(Context context, ResponseBody body) {

        Log.d(TAG, "contentType:>>>>"+ body.contentType().toString());

        String type = body.contentType().toString();

        if (type.equals(APK_CONTENT_TYPE)) {

           mFileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENT_TYPE)) {
            mFileSuffix = ".png";
        } else if (type.equals(JPG_CONTENT_TYPE)) {
            mFileSuffix = ".jpg";
        }

        // 其他同上 自己判断加入

        final String name = System.currentTimeMillis() + mFileSuffix;
        final String path = context.getExternalFilesDir(null) + File.separator + name;

        Log.d(TAG, "path:>>>>"+ path);

        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                Log.d(TAG, "file length: "+ fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    if (callBack != null) {
                        handler = new Handler(Looper.getMainLooper());
                        final long finalFileSizeDownloaded = fileSizeDownloaded;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(finalFileSizeDownloaded);
                            }
                        });

                    }
                }

                outputStream.flush();
                Log.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                if (callBack != null) {
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> callBack.onSuccess(path, name, fileSize));
                    Log.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                }
                
                return true;
            } catch (IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            if (callBack != null) {
                callBack.onError(e);
            }
            return false;
        }
    }
}
