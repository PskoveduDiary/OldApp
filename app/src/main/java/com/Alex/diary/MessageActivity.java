package com.Alex.diary;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Alex.diary.ui.messages.messages;

public class MessageActivity extends AppCompatActivity {
    public static Context contextt;
    public static FragmentManager fm;
    static messages messges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Сообщения");
        actionBar.show();
        contextt = getApplicationContext();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        messges = new messages();
        ft.replace(R.id.fragmentMSG, messges);
        ft.commit();
    }
}
