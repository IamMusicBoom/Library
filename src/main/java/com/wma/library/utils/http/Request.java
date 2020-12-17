package com.wma.library.utils.http;

import org.xutils.http.HttpMethod;

import java.io.File;
import java.util.Map;

/**
 * create by wma
 * on 2020/12/7 0007
 */
public class Request {

    public static final String FROM_JU_HE = "FROM_JU_HE";//从聚合
    public static final String FROM_MYSELF = "FROM_MYSELF";//从我自己的服务

    /**
     * 请求地址
     */
    String url;

    /**
     * 请求参数
     */
    Map<String, String> parameters;

    /**
     * 每个服务器返回的json串不同，对应解析json的方式有所不同
     * 从哪个服务器请求参数，对应解析返回的json
     */
    String from;

    /**
     * 请求方式
     */
    HttpMethod method;

    /**
     * 附件
     */
    File file;


    public Request( HttpMethod method,String url, Map<String, String> parameters, String from) {
        this.url = url;
        this.parameters = parameters;
        this.from = from;
        this.method = method;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public HttpMethod getRequestType() {
        return method;
    }

    public void setRequestType(HttpMethod method) {
        this.method = method;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
