package com.Alex.diary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageService {
    private static MessageService mInstance;
    private static final String BASE_URL = "https://one.pskovedu.ru/extjs/";
    private Retrofit mRetrofit;
    private List<Cookie> cookies;

    private MessageService() {
        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    final Request original = chain.request();
                    final Request authorized = original.newBuilder()
                            .addHeader("Cookie", "_ym_uid=1662142800682136986; _ym_d=1662142800; _ym_isad=2; PHPSESSID=02f85a561b3f9dfafa85e4ae664b75cd; X1_SSO=6312731f8602d815033b3012")
                            .build();
                    return chain.proceed(authorized);
                })
                //.addInterceptor(logging)
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
