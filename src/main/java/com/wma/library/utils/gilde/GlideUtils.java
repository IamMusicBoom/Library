package com.wma.library.utils.gilde;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.wma.library.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * create by wma
 * on 2020/12/29 0029
 */
public class GlideUtils {
    public static GlideUtils mUtils;

    private static int mErrorRes = R.mipmap.ic_image_error;
    private static int mPlaceholderRes = R.mipmap.ic_loading;


    public static GlideUtils getInstance() {
        if (mUtils == null) {
            mUtils = new GlideUtils();
        }
        return mUtils;
    }


    public static GlideUtils getInstance(int errorRes, int placeholderRes) {
        mErrorRes = errorRes;
        mPlaceholderRes = placeholderRes;
        if (mUtils == null) {
            mUtils = new GlideUtils();
        }
        return mUtils;
    }

    public GlideUtils() {

    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircle(Context context, String url, ImageView imageView) {
        RequestOptions transform = new RequestOptions().transform(new CircleCrop());
        Glide.with(context).load(url).error(mErrorRes).placeholder(mPlaceholderRes).apply(transform).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param roundConner
     */
    public void loadRoundConner(Context context, String url, ImageView imageView, int roundConner) {
        RequestOptions transform = new RequestOptions().transform(new RoundedCorners(roundConner));
        Glide.with(context).load(url).error(mErrorRes).placeholder(mPlaceholderRes).apply(transform).into(imageView);
    }

    /**
     * 加载高斯模糊图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius
     */
    public void loadBlur(Context context, String url, ImageView imageView, int radius) {
        RequestOptions transform = new RequestOptions().transform(new BlurTransformation(radius, 1));
        Glide.with(context).load(url).error(mErrorRes).placeholder(mPlaceholderRes).apply(transform).into(imageView);
    }


    /**
     * 加载组合图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param transformations
     */
    public void loadMutilTransformatiom(Context context, String url, ImageView imageView, Transformation<Bitmap>... transformations) {
        RequestOptions transform = new RequestOptions().transform(transformations);
        Glide.with(context).load(url).error(mErrorRes).placeholder(mPlaceholderRes).apply(transform).into(imageView);
    }

    public void loadImage(Context context,Object url,ImageView imageView){
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide.with(context).load(url).placeholder(mPlaceholderRes).error(mErrorRes).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).into(imageView);

    }

}
