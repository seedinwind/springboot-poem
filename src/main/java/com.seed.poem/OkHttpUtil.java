package com.seed.poem;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttpUtil {

    private OkHttpClient client;
    public OkHttpUtil() {
        createClient();
    }

    private void createClient() {
        client=new OkHttpClient.Builder()
                .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build();
    }

    public String postRequest(String url, RequestBody body) throws IOException {
        Request req=new Request.Builder()
                .url(url)
                .post(body)
                .build();
       Call call= client.newCall(req);
       Response res=call.execute();
      return res.body().string();
    }

    public String getRequest(String url) throws IOException {
        Request req=new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call= client.newCall(req);
        Response res=call.execute();
        return res.body().string();
    }
}
