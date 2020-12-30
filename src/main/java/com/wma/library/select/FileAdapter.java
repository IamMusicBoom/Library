package com.wma.library.select;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.wma.library.R;
import com.wma.library.base.BaseRecyclerAdapter;
import com.wma.library.base.BaseRecyclerHolder;
import com.wma.library.databinding.ItemFileBinding;
import com.wma.library.utils.ToastUtils;
import com.wma.library.utils.gilde.GlideUtils;

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
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        if(TextUtils.isEmpty(mimeType)){
            GlideUtils.getInstance().loadImage(imageView.getContext(),R.mipmap.ic_unknown_file,imageView);
        }else{
            if(mimeType.contains("text")){
                GlideUtils.getInstance().loadImage(imageView.getContext(),R.mipmap.ic_text_file,imageView);
            }else{
                GlideUtils.getInstance().loadImage(imageView.getContext(),imgPath,imageView);

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
