package com.wma.library.select;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/12/17 0017
 */
public class SelectUtils {

    final String TAG = SelectUtils.class.getSimpleName();

    private Context mContext;

    public SelectUtils(Context context) {
        this.mContext = context;
    }

    public List<FileItem> Select(String mSelectType) {
        Uri uri = null;
        String order = "";
        switch (mSelectType) {
            case FileType.AUDIO:
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                order = MediaStore.Audio.AudioColumns.DATE_MODIFIED;
                return selectByAudio(uri, order);
            case FileType.IMAGE:
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                order = MediaStore.Images.ImageColumns.DATE_MODIFIED;
                return selectByImage(uri, order);
            case FileType.FILE:
                uri = MediaStore.Files.getContentUri("external");
                order = MediaStore.Files.FileColumns.DATE_MODIFIED;
                return selectByFile(uri, order);
            case FileType.VIDEO:
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                order = MediaStore.Video.VideoColumns.DATE_MODIFIED;
                return selectByVideo(uri, order);
            default:
                return selectByImage(uri, order);
        }
    }

    private List<FileItem> selectByVideo(Uri uri, String order) {
        List<FileItem> list = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, order + "  desc");
        while (cursor.moveToNext()) {
            FileItem fileItem = new FileItem();
            fileItem.setFileId(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)));
            fileItem.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED)));
            fileItem.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)));
            fileItem.setSize(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)));
            fileItem.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)));
            fileItem.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
            list.add(fileItem);
        }
        return list;
    }

    private List<FileItem> selectByFile(Uri uri, String order) {
        List<FileItem> list = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, order + "  desc");
        while (cursor.moveToNext()) {
            FileItem fileItem = new FileItem();
            fileItem.setFileId(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)));
            fileItem.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)));
            fileItem.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)));
            fileItem.setSize(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)));
            fileItem.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)));
            fileItem.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
            list.add(fileItem);
        }
        return list;
    }


    private List<FileItem> selectByImage(Uri uri, String order) {
        List<FileItem> list = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, order + "  desc");
        while (cursor.moveToNext()) {
            FileItem fileItem = new FileItem();
            fileItem.setFileId(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));
            fileItem.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)));
            fileItem.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)));
            fileItem.setSize(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)));
            fileItem.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
            fileItem.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
            list.add(fileItem);
        }
        return list;
    }

    private List<FileItem> selectByAudio(Uri uri, String order) {
        List<FileItem> list = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, order + "  desc");
        while (cursor.moveToNext()) {
            FileItem fileItem = new FileItem();
            fileItem.setFileId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)));
            fileItem.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_ADDED)));
            fileItem.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            fileItem.setSize(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)));
            fileItem.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
            fileItem.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
            list.add(fileItem);
        }
        return list;
    }
}
