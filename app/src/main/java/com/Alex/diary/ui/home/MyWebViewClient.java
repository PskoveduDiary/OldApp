package com.Alex.diary.ui.home;

import android.annotation.TargetApi;
import com.Alex.diary.ui.home.HomeFragment;
import android.os.Build;
import android.util.Log;
import android.util.LogPrinter;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        //Log.d("WebClient", request.getUrl().toString());
        if (request.getUrl().toString().equals("https://one.pskovedu.ru/edv/index/error/access_denied")){
            HomeFragment.Login(); //To login, because access denied
            //Log.d("WebClient", "Login!");
        }
        else if (request.getUrl().toString().equals("https://one.pskovedu.ru/")) {
            //Log.d("WebClient", "Diary!");
            HomeFragment.Pass(); //Logged, to diary!
        } else{
            view.loadUrl(request.getUrl().toString());

        }
        return true;
    }

    // For old devices
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.equals("https://one.pskovedu.ru/edv/index/error/access_denied")){
            HomeFragment.Login();
            //Log.d("WebClient", "Login!");
        }
        else if (url.equals("https://one.pskovedu.ru/")) {
            //Log.d("WebClient", "Diary!");
            HomeFragment.Pass(); //Logged, to diary!
        }
        else{
            view.loadUrl(url);

        }
        return true;
    }
}
