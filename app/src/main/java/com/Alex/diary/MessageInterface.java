package com.Alex.diary;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageInterface {
    @GET("posts/{id}")
    public Call<Message> getPostWithID(@Path("id") int id);
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("direct")
    public  Call<ResponseBody> getMessages(@Body RequestBody params);
}