package com.Alex.diary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageService {
    private static MessageService mInstance;
    private static final String BASE_URL = "https://one.pskovedu.ru/extjs/";
    private Retrofit mRetrofit;
    private List<Cookie> cookies;

    private MessageService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    final Request original = chain.request();
                    final Request authorized = original.newBuilder()
                            .addHeader("Cookie", "PHPSESSID=51ff092a85a29a5fb7ba5afc6aebfd81; _ym_uid=1662281660406719927; _ym_d=1662281660; _ym_isad=1; X1_SSO=631468468602d815033b46ab")
                            .build();
                    return chain.proceed(authorized);
                })
                .addInterceptor(logging)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static MessageService getInstance() {
        if (mInstance == null) {
            mInstance = new MessageService();
        }
        return mInstance;
    }
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public MessageInterface getJSONApi() {
        return mRetrofit.create(MessageInterface.class);
    }
}
