package com.newtranx.cloud.edit.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


public class HttpUtil {
    public static HttpResponse post(String url, Object registerDto) {
        HttpResponse response;
        try {
            response = Request.Post(url).bodyString(JSON.toJSONString(registerDto), ContentType.APPLICATION_JSON).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse put(String url, Object param) {
        HttpResponse response;
        try {
            response = Request.Put(url).bodyString(JSON.toJSONString(param), ContentType.APPLICATION_JSON).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse post(String url, Object registerDto, String authorization) {
        HttpResponse response;
        try {
            response = Request.Post(url).bodyString(JSON.toJSONString(registerDto), ContentType.APPLICATION_JSON)
                    .addHeader("Authorization", "Bearer " + authorization).connectTimeout(5000).socketTimeout(5000).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse get(String url) {
        HttpResponse response;
        try {
            response = Request.Get(url).connectTimeout(5000).socketTimeout(5000).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse get(String url, String authorization) {
        HttpResponse response;
        try {
            response = Request.Get(url).addHeader("Authorization", "Bearer " + authorization).connectTimeout(5000).socketTimeout(5000).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse put(String url, Object paramObj, String accessToken) {
        HttpResponse response;
        try {
            response = Request.Put(url).addHeader("Authorization", "Bearer " + accessToken).bodyString(JSON.toJSONString(paramObj), ContentType.APPLICATION_JSON).execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse uploadFile(String url, File file, String accessToken) {
        HttpResponse response;
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.RFC6532)
                    .setCharset(Charset.forName("utf-8"));
            builder.addBinaryBody("file", file);
            response = Request.Post(url).addHeader("Authorization", "Bearer " + accessToken)
                    .body(builder.build())
                    .execute().returnResponse();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
