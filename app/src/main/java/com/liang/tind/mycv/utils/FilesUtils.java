/*
 * Copyright (C) 2007 The Android  Source Project
 *
 * Licensed under the RichenInfo License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.richeninfo.com/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liang.tind.mycv.utils;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilesUtils {


    public static String getMimeTypeFromUrl(String url) {
        String extension = getExtensionFromUrl(url);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public static long getSize(File f) {
        int size = -1;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            size = fis.available();
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return size;
    }

    public static String getExtensionFromUrl(String url) {

        String result = "";

        int lastIndex = url.lastIndexOf(".");
        result = url.substring(lastIndex + 1, url.length());
        result = result.toLowerCase(Locale.CHINA);

        return result;
    }

    public static boolean isDirHasFiles(String pathDir) {
        File imgDirs = new File(pathDir);
        if (imgDirs != null) {
            imgDirs.mkdirs();
            File[] files = imgDirs.listFiles();
            if (files != null && files.length > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean clearDirChildren(String pathDir) {
        File dir = new File(pathDir);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    delRecursive(f);
                }
                return true;
            }
        }

        return false;
    }

    public static void delRecursive(File obj) {
        if (obj != null) {
            if (obj.isDirectory()) {
                File[] children = obj.listFiles();
                if (children != null) {
                    for (File child : children) {
                        delRecursive(child);
                    }
                }
            }
            boolean flag = obj.delete();
        }
    }

    public static void renameDirRecursive(File sourceFile, File targetDir) {
        if (sourceFile != null && sourceFile.exists()) {
            // 2、移动
            if (sourceFile.isDirectory()) {
                // 目录 = 创建指定目录
                targetDir.mkdirs();
                File[] sourceChildren = sourceFile.listFiles();
                if (sourceChildren != null && sourceChildren.length > 0) {
                    for (File sourceChild : sourceChildren) {
                        // 文件夹，递归创建
                        renameDirRecursive(sourceChild, new File(targetDir,
                                sourceChild.getName()));
                    }
                }
            } else {
                // 文件 = rename文件
                sourceFile.renameTo(targetDir);
            }
        }
    }

    public static List<String> getImageNames(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for (int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if (fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg")
                        || suffix.toLowerCase().equals(".jpeg")
                        || suffix.toLowerCase().equals(".png")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }

    public static boolean deleteFile(File file) {
        boolean result = false;
        if (!file.exists()) {
            result = false;
        } else {
            if (file.isFile()) {
                file.delete();
                result = true;
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                } else {
                    for (File f : childFile) {
                        deleteFile(f);
                    }
                    file.delete();
                }
                result = true;
            }
        }
        return result;
    }

}
