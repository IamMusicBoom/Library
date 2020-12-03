package com.wma.library.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
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
public abstract class BaseLoadFragment<T extends BaseModule, B extends ViewDataBinding> extends BaseFragment<B> implements Callback.CommonCallback<String>, OnRefreshListener, OnLoadMoreListener {

    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        mSmartRefreshLayout = getSmartRefreshLayout();
        setEnableRefresh(enableRefresh());
        setEnableLoadMore(enableLoadMore());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void autoRefresh(){
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    /**
     * 是否可以刷新
     *
     * @return
     */
    private void setEnableRefresh(boolean refresh) {
        if(mSmartRefreshLayout != null){
            mSmartRefreshLayout.setEnableRefresh(refresh);
            if(refresh){
                mSmartRefreshLayout.setRefreshHeader(getRefreshHeader());
                mSmartRefreshLayout.setOnRefreshListener(this);
            }
        }
    }

    /**
     * 是否可以加载更多
     *
     * @return
     */
    private void setEnableLoadMore(boolean loadMore) {
        if(mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setEnableLoadMore(loadMore);
            if (loadMore) {
                mSmartRefreshLayout.setRefreshFooter(getRefreshFooter());
                mSmartRefreshLayout.setOnLoadMoreListener(this);
            }
        }
    }

    /**
     * 是否可以刷新
     * @return
     */
    protected boolean enableRefresh(){
        return true;
    }

    /**
     * 是否可以加载更多
     * @return
     */
    protected boolean enableLoadMore(){
        return true;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    /**
     * 获取刷新头，重写该方法可以替换刷新头部
     *
     * @return
     */
    protected RefreshHeader getRefreshHeader() {
        return new BezierRadarHeader(getContext());
    }

    /**
     * 获取加载底，重写该方法可以替换加载底
     *
     * @return
     */
    protected RefreshFooter getRefreshFooter() {
        return new ClassicsFooter(getContext());
    }


    protected abstract SmartRefreshLayout getSmartRefreshLayout();

    protected abstract void loadData();


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
                        if (((JSONObject) o).has("data")) {
                            Object data = ((JSONObject) o).get("data");
                            if (data instanceof JSONArray) {
                                JSONArray dataJsonArray = (JSONArray) data;
                                List<T> list = new ArrayList<>();
                                for (int i = 0; i < dataJsonArray.length(); i++) {
                                    JSONObject jsonObject1 = null;
                                    jsonObject1 = dataJsonArray.getJSONObject(i);
                                    T t = new Gson().fromJson(jsonObject1.toString(), type);
                                    list.add(t);
                                }
                                handleBySuccess(list);
                            } else {
                                T t = new Gson().fromJson(o.toString(), type);
                                handleBySuccess(t);
                            }
                        } else {
                            T t = new Gson().fromJson(o.toString(), type);
                            handleBySuccess(t);
                        }
                    } else if (o == null || o.toString().equals("null")) {
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
        if(mSmartRefreshLayout != null) {
            if (mSmartRefreshLayout.isRefreshing()) {
                mSmartRefreshLayout.finishRefresh();
            }
            if (mSmartRefreshLayout.isLoading()) {
                mSmartRefreshLayout.finishLoadMore();
            }
        }
    }

    public void handleBySuccess(T result) {

    }

    public void handleBySuccess(List<T> result) {
    }


    public void handleByFail(String msg) {
    }


}
