package com.commit451.youtubeextractor.extraction;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * {@link okhttp3.OkHttpClient} version of Downloader
 */
public class OkHttpDownloader implements Downloader {

    OkHttpClient client;

    public OkHttpDownloader() {
        client = new OkHttpClient();
    }

    @Override
    public String download(String siteUrl) throws IOException {
        return dl(siteUrl, null);
    }

    @Override
    public String download(String siteUrl, String language) throws IOException {
        return dl(siteUrl, language);
    }

    private String dl(String siteUrl, @Nullable String language) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(siteUrl);
        if (language != null) {
            requestBuilder.addHeader("Accept-Language", language);
        }
        Response response = client.newCall(requestBuilder.build()).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Response not success");
        }
        return response.body().string();
    }
}
