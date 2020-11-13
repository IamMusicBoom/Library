package com.wma.library.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wma.library.R;
import com.wma.library.log.Logger;
import com.wma.library.utils.JsonUtils;
import com.wma.library.widget.titlebar.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public abstract class BaseLoadFragment<T extends BaseModule, B extends ViewDataBinding> extends BaseFragment<B> implements Callback.CommonCallback<String> {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void init(Bundle savedInstanceState) {
        loadData();
        mSwipeRefreshLayout.setEnabled(canRefresh());
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    /**
     * 是否可以刷新
     *
     * @return
     */
    protected abstract boolean canRefresh();

    protected abstract void loadData();

    @Override
    public View generateRootView() {
        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        LinearLayout rootView = new LinearLayout(mContext);
        mSwipeRefreshLayout.addView(rootView);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (null != getTitleStr()) {// 生成title
            View title = getLayoutInflater().inflate(R.layout.title_bar_view, rootView, false);
            mTitleBar = new TitleBar(mContext, title);
            mTitleBar.setTitleText(getTitleStr());
            mTitleBar.setOnTitleBarClickListener(this);
            rootView.addView(title);
        }
        if (getLayoutId() != 0) {// 生成内容区域
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), mParent, false);
            rootView.addView(mBinding.getRoot());
        }
        return mSwipeRefreshLayout;
    }


    public Type getType() {
        ParameterizedType genType = (ParameterizedType) getClass().getGenericSuperclass();

        Type[] actualTypeArguments = ((ParameterizedType) genType).getActualTypeArguments();

        return actualTypeArguments[0];
    }

    @Override
    public void onSuccess(String result) {
        Logger.d(TAG, "onSuccess: result = " + result);
        try {
            Object object = new JSONTokener(result).nextValue();
            Type type = getType();
            if (object instanceof JSONArray) {
                JSONArray dataJsonArray = (JSONArray) object;
                List<T> list = new ArrayList<>();
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = null;
                    jsonObject1 = dataJsonArray.getJSONObject(i);
                    T t = new Gson().fromJson(jsonObject1.toString(), type);
                    list.add(t);
                }
                handleBySuccess(list);
            } else if (object instanceof JSONObject) {
                JSONObject jo = ((JSONObject) object);
                if (jo.has("result")) {
                    Object o = jo.get("result");
                    if (o instanceof JSONArray) {
                        JSONArray dataJsonArray = (JSONArray) o;
                        List<T> list = new ArrayList<>();
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = dataJsonArray.getJSONObject(i);
                            T t = new Gson().fromJson(jsonObject1.toString(), type);
                            list.add(t);
                        }
                        handleBySuccess(list);
                    } else if (o instanceof JSONObject) {
                        if(((JSONObject) o).has("data")){
                            Object data = ((JSONObject) o).get("data");
                            if(data instanceof JSONArray){
                                JSONArray dataJsonArray = (JSONArray) data;
                                List<T> list = new ArrayList<>();
                                for (int i = 0; i < dataJsonArray.length(); i++) {
                                    JSONObject jsonObject1 = null;
                                    jsonObject1 = dataJsonArray.getJSONObject(i);
                                    T t = new Gson().fromJson(jsonObject1.toString(), type);
                                    list.add(t);
                                }
                                handleBySuccess(list);
                            }else{
                                T t = new Gson().fromJson(o.toString(), type);
                                handleBySuccess(t);
                            }
                        }else{
                            T t = new Gson().fromJson(o.toString(), type);
                            handleBySuccess(t);
                        }
                    }else if(o == null || o.toString().equals("null")){
                        // error_code = 207301 城市不能为空或者暂时不支持该城市
                        handleByFail(jo.getString("error_code"));
                    }
                } else {
                    T t = new Gson().fromJson(object.toString(), type);
                    handleBySuccess(t);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handleByFail(e.toString());
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Logger.e(TAG, "onError: ex = " + ex);
        handleByFail(ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        Logger.d(TAG, "onCancelled: cex = " + cex);
    }

    @Override
    public void onFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void handleBySuccess(T result) {

    }

    public void handleBySuccess(List<T> result) {
    }


    public void handleByFail(String msg) {
    }


}
