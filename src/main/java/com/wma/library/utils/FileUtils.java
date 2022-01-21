package com.wma.library.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.wma.library.log.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * create by wma
 * on 2020/11/2 0002
 */
public class FileUtils {
    private final String TAG = FileUtils.class.getSimpleName();

    private Context mContext;


    public FileUtils(Context mContext) {
        this.mContext = mContext;
    }

    public String getAssetsFilePath(String fileName) {
        try {
            String path = mContext.getCacheDir().getAbsolutePath() + File.separator + fileName;
            AssetManager assets = mContext.getAssets();
            InputStream open = null;
            open = assets.open(fileName);

            if (copyFile(open, path)) {
                return path;
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public boolean copyFile(InputStream in, String newPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public boolean copyFile(String oldPath, String newPath) {
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            File originalFile = new File(oldPath);
            if (!originalFile.exists()) {
                LogUtil.e(TAG, "copyFile: 源文件不存在，无法复制");
                return false;
            }
            in = new FileInputStream(oldPath);
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
