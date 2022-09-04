package com.Alex.diary.ui.messages;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.Alex.diary.API;
import com.Alex.diary.MessageService;
import com.Alex.diary.ProgramAdapter;
import com.Alex.diary.ProgramAdapterContacts;
import com.Alex.diary.PurshaseCore;
import com.Alex.diary.R;
import com.Alex.diary.XLSParser;
import com.Alex.diary.ui.home.MyWebViewClient;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class messages extends Fragment implements API.MyCallback {
        public PurshaseCore pc = new PurshaseCore();
        public static WebView webView;
        API api;
        static List<List> All;
        static View view;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            api = new API();
            view = inflater.inflate(R.layout.fragment_messages, vg, false);
            /*if(!pc.Check(this.getContext()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Intent intent = new Intent(getActivity(), PurshaseCore.class);
                startActivity(intent);
            }
            webView = (WebView) view.findViewById(R.id.webViewMsg);
            webView.setWebViewClient(new MyWebViewClient() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onPageFinished(WebView view, String url) {
                    //webView.loadUrl("javascript:document.getElementsByClassName(\"ux-notification-light\")[0].remove()"); // hide notifications

                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-maximize\")[0].click()"); //on full screeni.
                    //webView.loadUrl("javascript:document.getElementById(\"ext-comp-1017-body\").remove()"); //hide background buttons

                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-minimize\")[0].remove()"); //hide head buttons
                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-close\")[0].remove()");
                    //webView.loadUrl("javascript:document.getElementById(\"messaging_header-innerCt\").remove()"); // hide head button
                    //webView.loadUrl("javascript:document.getElementById(\"taskbar-1024-innerCt\").style.display = \"none\""); //hide taskbar
                    webView.evaluateJavascript("document.getElementsByClassName(\"x-panel x-fit-item x-panel-default\")[0].style.visibility=\"hidden\"", null ); // hide taskbar
                    //webView.evaluateJavascript("document.getElementById(\"messaging_header-innerCt\").remove();", null); // show add button

                    webView.loadUrl("javascript:document.getElementById(\"tool-1092\").hidden = true"); //hide groups button
                    webView.loadUrl("javascript:document.getElementById(\"button-1151\").remove()"); //hide groups button
                    webView.loadUrl("javascript:document.getElementById(\"button-1144\").remove()"); //hide groups button
                    webView.loadUrl("javascript:document.getElementById(\"button-1088\").remove()"); //hide groups button
                    //webView.evaluateJavascript("document.getElementById(\"messaging\").style=\"top: 50px; left:0px;\";", null);//move window to up(now not work)
                    // webView.evaluateJavascript("document.getElementById(\"messaging\").style=\"top: 50px; left:0px;\";", null);//move window to up(now not work)
                    /*webView.evaluateJavascript("(async function() " +
                            "{ let response = await fetch(\"https://one.pskovedu.ru/extjs/direct\", {" +
                            "  method: 'POST',headers: {" +
                            "      'Accept': 'application/json'," +
                            "      'Content-Type': 'application/json'" +
                            "    }," +
                            "body: '{\"action\":\"X1API\",\"method\":\"direct\",\"data\":[{\"service\":\"messaging\",\"method\":\"getContacts\",\"params\":[],\"ctx\":{}}],\"type\":\"rpc\",\"tid\":15}'});" +
                            "" +
                            "if (response.ok) {" +
                            "  let json = await response.json();" +
                            "console.log(json);" +
                            " bridge.onAnnotations(json);" +
                            "} else {" +
                            "  bridge.onAnnotations(\"Ошибка HTTP: \" + response.status)" +
                            "} })();", null);
                    super.onPageFinished(view, url);

                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                    }
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    // process
                    break;
            } //Dark theme
            try {
                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view,
                                                  int newProgress) {

                        if (newProgress >= 100) {

                            webView.scrollTo(0,0);

                        }

                        super.onProgressChanged(view, newProgress);
                    };

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(this, "bridge");
            webView.loadUrl("https://one.pskovedu.ru/#messaging")
            webView.setVisibility(View.GONE);
                          KEEP
            document.getElementById("messaging").style="width: 1090px; height: 907px; left: 0px; top: 30px; z-index: 19011";
            document.getElementById("taskbar-1024").style.display = 'none'
            document.getElementById("ext-gen1180").style.display = 'none'
            document.getElementById("messaging_header").style.display = 'none'
            String b = "{\"action\":\"X1API\",\"method\":\"direct\",\"data\":[{\"service\":\"messaging\",\"method\":\"getMessages\",\"params\":{\"user\":\"1B120E187EA450E1E1C2877F679EE1EB\",\"isGroup\":\"\",\"lastTimestamp\":1662151845661},\"ctx\":{}}],\"type\":\"rpc\",\"tid\":36}";
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(b));
            MessageService.getInstance()
                    .getJSONApi()
                    .getMessages(body)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            ResponseBody message = response.body();
                            try {
                                Log.d("log", String.valueOf(message.string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                            Log.d("log", "Error occurred while getting request!");
                            t.printStackTrace();
                        }
                    });*/

            api.GetCotacts(this);
            return view;
        }

    @Override
    public void Contacts(List<String> logins, List<String> names, List<Integer> unreaded, List<Boolean> isGroup) {
            ListView listView = view.findViewById(R.id.ContactsList);
            ProgramAdapterContacts adapter = new ProgramAdapterContacts(this.getContext(), logins, names, unreaded, isGroup);
            listView.setAdapter(adapter);
        }
}
