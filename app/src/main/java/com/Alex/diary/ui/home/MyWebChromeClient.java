package com.Alex.diary.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.Alex.diary.MainActivity;

final class MyWebChromeClient extends WebChromeClient {
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("LogTag", message);
        result.confirm();
        SharedPreferences sharedPref = new MainActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PrivateID", message);
        editor.apply();

        Log.d("prefs", message);
        return true;
    }
}