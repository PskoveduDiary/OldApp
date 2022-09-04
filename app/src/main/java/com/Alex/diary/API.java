package com.Alex.diary;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.*;

public class API {
    public interface MyCallback {
        void Contacts(List<String> logins, List<String> names, List<Integer> unreaded, List<Boolean> isGroup);
    }
    public void GetCotacts(MyCallback callback){
        String dict = "{'action':'X1API'," +
                "'method':'direct'," +
                "'data':[{'service':'messaging','method':'getContacts','params':[],'ctx':{}}]," +
                "'type':'rpc'," +
                "'tid':29}";
        dict = dict.replace('\'', '"');
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), dict);
        MessageService.getInstance()
                .getJSONApi()
                .getDirect(body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        ResponseBody message = response.body();
                        try {
                            List<String> logins = new ArrayList<String>();
                            List<String> names = new ArrayList<String>();
                            List<Integer> unread = new ArrayList<Integer>();
                            List<Boolean> group = new ArrayList<Boolean>();
                            String jsonString = message.string();
                            JSONObject obj = new JSONObject(jsonString);
                            JSONArray arr = obj.getJSONArray("data");
                            for (int i = 0; i < arr.length(); i++)
                            {
                                logins.add(arr.getJSONObject(i).getString("LOGIN"));
                                names.add(arr.getJSONObject(i).getString("NAME"));
                                unread.add(arr.getJSONObject(i).getInt("UNREAD"));
                                try {group.add(arr.getJSONObject(i).getBoolean("IS_GROUP"));}
                                catch (org.json.JSONException Exception) { group.add(false); }
                            }
                            callback.Contacts(logins, names, unread, group);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                        Log.d("log", "Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}
