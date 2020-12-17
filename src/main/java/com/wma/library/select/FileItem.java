package com.wma.library.select;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.wma.library.base.BaseModule;

/**
 * create by wma
 * on 2020/12/17 0017
 */
public class FileItem extends BaseModule implements Parcelable {

    /**
     * 文件ID
     */
    String fileId;
    /**
     * 文件名字
     */
    String fileName;
    /**
     * 文件路径
     */
    String filePath;

    /**
     * 文件大小
     */
    String size;

    /**
     * 文件添加时间
     */
    String date;

    /**
     * 是否选中
     */
    boolean isSelect;

    /**
     * 文件类型
     */
    String mimeType;

    public FileItem() {
    }


    protected FileItem(Parcel in) {
        fileId = in.readString();
        fileName = in.readString();
        filePath = in.readString();
        size = in.readString();
        date = in.readString();
        isSelect = in.readByte() != 0;
        mimeType = in.readString();
    }

    public static final Creator<FileItem> CREATOR = new Creator<FileItem>() {
        @Override
        public FileItem createFromParcel(Parcel in) {
            return new FileItem(in);
        }

        @Override
        public FileItem[] newArray(int size) {
            return new FileItem[size];
        }
    };

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            FileItem fileItem = ((FileItem) obj);
            if (!TextUtils.isEmpty(this.fileId) && this.fileId.equals(fileItem.fileId)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileId);
        dest.writeString(fileName);
        dest.writeString(filePath);
        dest.writeString(size);
        dest.writeString(date);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeString(mimeType);
    }
}
