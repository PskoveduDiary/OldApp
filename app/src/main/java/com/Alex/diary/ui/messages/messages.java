package com.Alex.diary.ui.messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;



public class messages extends Fragment {
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            View view = inflater.inflate(R.layout.fragment_messages, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewMsg);
            webView.setWebViewClient(new MyWebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    webView.evaluateJavascript("document.getElementById(\"dataview-1069\").style.display = 'none'", null); //hide background buttons
                    webView.evaluateJavascript("document.getElementById('taskbar-1024').style.display = 'none'", null); // hide taskbar
                    webView.evaluateJavascript("document.getElementById(\"tool-1088\").style.display = 'none'", null); //hide head buttons
                    webView.evaluateJavascript("document.getElementById(\"tool-1091\").style.display = 'none'", null);
                    webView.evaluateJavascript("document.getElementById(\"button-1086\").style.display = 'none'", null); //hide groups button
                    webView.evaluateJavascript("document.getElementById(\"tool-1089-toolEl\").click()", null); //on full screen
                    webView.evaluateJavascript("document.getElementById(\"tool-1090\").style.display = 'none'", null); // hide head button
                    //webView.evaluateJavascript("document.getElementById(\"messaging\").style=\"top: 15px;\";", null);//move window to up(today not work)

                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://one.pskovedu.ru/#messaging");
            //              KEEP
            //document.getElementById("messaging").style="width: 1090px; height: 907px; left: 0px; top: 30px; z-index: 19011";
            //document.getElementById("taskbar-1024").style.display = 'none'
            //document.getElementById("ext-gen1180").style.display = 'none'
            //document.getElementById("messaging_header").style.display = 'none'

            return view;
        }
}