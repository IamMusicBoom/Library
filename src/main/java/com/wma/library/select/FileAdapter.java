package com.wma.library.select;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.wma.library.R;
import com.wma.library.base.BaseRecyclerAdapter;
import com.wma.library.base.BaseRecyclerHolder;
import com.wma.library.databinding.ItemFileBinding;
import com.wma.library.log.Logger;
import com.wma.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/12/17 0017
 */
public class FileAdapter extends BaseRecyclerAdapter<FileItem, ItemFileBinding> {
    final String TAG = FileAdapter.class.getSimpleName();

    public FileAdapter(List<FileItem> mList, Context mContext) {
        super(mList, mContext);
    }

    private int mLimit;
    private String mSelectType;
    private ArrayList<FileItem> mSelectFileItems;

    @Override
    public void bindData(ItemFileBinding binding, final FileItem data, BaseRecyclerHolder holder, int position) {
        binding.setFileItem(data);
        binding.itemCbNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {// 选中
                    if (mSelectFileItems.size() >= mLimit) {
                        buttonView.setChecked(false);
                        ToastUtils.showToast(mContext, "选择数量超出限制.....");
                        return;
                    } else {
                        mSelectFileItems.add(data);
                    }
                } else {// 取消
                    mSelectFileItems.remove(data);
                }
                if (onSelectListener != null) {
                    onSelectListener.onSelect(mSelectFileItems);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public void setLimit(int limit) {
        mLimit = limit;
        mSelectFileItems = new ArrayList<>();
    }


    public ArrayList<FileItem> getSelectFileItems() {
        return mSelectFileItems;
    }

    OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setSelectType(String selectType) {
        mSelectType = selectType;
    }


    @BindingAdapter(value = {"showImageByUri","mimeType"}, requireAll = true)
    public static void showImageByUri(ImageView imageView, String imgPath, String mimeType) {
        if(TextUtils.isEmpty(mimeType)){
            Glide.with(imageView).load(R.mipmap.ic_unknown_file).placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_image_error).into(imageView);
        }else{
            if(mimeType.contains("text")){
                Glide.with(imageView).load(R.mipmap.ic_text_file).placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_image_error).into(imageView);
            }else{
                Glide.with(imageView).load(imgPath).placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_image_error).into(imageView);

            }
        }
    }

    @BindingAdapter("isShowIconByMIME")
    public static void isShowIconByMIME(ImageView imageView, String mime) {
        if (TextUtils.isEmpty(mime)) {
            imageView.setVisibility(View.GONE);
        } else {
            if (mime.contains("video")) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
    }

}
