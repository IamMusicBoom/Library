package com.wma.library.utils.json;

import com.google.gson.Gson;
import com.wma.library.base.BaseModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/12/7 0007
 */
public class JsonParserUtils<T extends BaseModule> {

    ResultHandleListener mListener;

    public JsonParserUtils(ResultHandleListener listener) {
        mListener = listener;
    }

    public void parserByJuHe(String result, Type type) {
        try {
            Object object = new JSONTokener(result).nextValue();
            if (object instanceof JSONArray) {
                JSONArray dataJsonArray = (JSONArray) object;
                List<T> list = new ArrayList<>();
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject jsonObject1 = null;
                    jsonObject1 = dataJsonArray.getJSONObject(i);
                    T t = new Gson().fromJson(jsonObject1.toString(), type);
                    list.add(t);
                }
                mListener.success(list);
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
                        mListener.success(list);
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
                                mListener.success(list);
                            } else {
                                T t = new Gson().fromJson(o.toString(), type);
                                mListener.success(t);
                            }
                        } else {
                            T t = new Gson().fromJson(o.toString(), type);
                            mListener.success(t);
                        }
                    } else if (o == null || o.toString().equals("null")) {
                        // error_code = 207301 城市不能为空或者暂时不支持该城市
                        mListener.fail(jo.getString("error_code"));
                    }
                } else {
                    T t = new Gson().fromJson(object.toString(), type);
                    mListener.success(t);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mListener.fail(e.toString());
        }
    }


    public void parserByMySelf(String result, Type type) {
        try {
            Object object = new JSONTokener(result).nextValue();
            if (object instanceof JSONObject) {
                JSONObject jsonObject = ((JSONObject) object);
                String msg = "";
                if (jsonObject.has("msg") && jsonObject.get("msg") instanceof String) {
                    msg = jsonObject.getString("msg");
                }
                int code = -1;
                if (jsonObject.has("code") && jsonObject.get("code") instanceof Integer) {
                    code = jsonObject.getInt("code");
                }
                if (code != 200) {
                    mListener.fail(msg);
                    return;
                }
                if (jsonObject.has("data")) {
                    Object data = jsonObject.get("data");
                    if (data instanceof JSONObject) {
                        T t = new Gson().fromJson(data.toString(), type);
                        mListener.success(t);
                    } else if (data instanceof JSONArray) {
                        JSONArray dataJsonArray = (JSONArray) data;
                        List<T> list = new ArrayList<>();
                        for (int i = 0; i < dataJsonArray.length(); i++) {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = dataJsonArray.getJSONObject(i);
                            T t = new Gson().fromJson(jsonObject1.toString(), type);
                            list.add(t);
                        }
                        mListener.success(list);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mListener.fail(e.toString());
        }

    }
}
