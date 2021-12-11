package com.Alex.diary.ui.home;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MyWebViewClient extends WebViewClient {
    String cookies;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        String Error = "\n" +
                "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>    Ошибка\n" +
                "</title>\n" +
                "<link href=\"https://one.pskovedu.ru/app/eservicescheduleview/build/es_schedule_error_v575486431459E88EA6CFBDFF86B88638.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\"/>" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"container\">\n" +
                "        <br>\n" +
                "        <div class=\"alert alert-danger\">\n" +
                "            Доступ к странице запрещен. <br>Возможные причины: <br>" +
                "            - Вы не вошли в аккаунт <br>" +
                "            - Расписание доступно только ученикам  <br>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
        Log.d("WebClient", request.getUrl().toString());
        if (request.getUrl().toString().equals("https://one.pskovedu.ru/edv/index/error/access_denied")){
            HomeFragment.Login(); //To login, because access denied
            //Log.d("WebClient", "Login!");
        }
        else if (request.getUrl().toString().equals("https://one.pskovedu.ru/")) {
            //Log.d("WebClient", "Diary!");
            HomeFragment.Pass(); //Logged, to diary!
        }
        else if (request.getUrl().toString().equals("https://one.pskovedu.ru/schedule/index/error/access_denied")) {
            //Log.d("WebClient", "Error!");
            view.loadData(Error, "text/html; charset=UTF-8", null); //Logged, but user is parent or Not logged
        }
        else if(request.isRedirect() && view.getUrl().equals("https://one.pskovedu.ru/#messaging")){
            view.loadData(Error, "text/html; charset=UTF-8", null); //Logged, but user is parent or Not logged
        }
        else{
            if (request.getUrl().toString().contains("marks")) Download(request.getUrl().toString());
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
    public void Download(String url){}
}
