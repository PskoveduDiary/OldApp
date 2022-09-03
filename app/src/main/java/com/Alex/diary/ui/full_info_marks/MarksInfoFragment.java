package com.Alex.diary.ui.full_info_marks;

import static com.Alex.diary.XLSParser.contextt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.R;
import com.Alex.diary.XLSParser;

import java.util.List;


public class MarksInfoFragment extends Fragment {

    static TextView ItemNameTxt;
    static TextView VypiskaTxt;
    static TextView ItogTxt;
    static TextView NoShowTxt;
    static TextView PassTxt;
    static TextView DiseaseTxt;
    static TextView LatenessTxt;
    static String ItemName;
    static String Vypiska;
    static Double Itog;
    static String NoShow;
    static String Pass;
    static String Disease;
    static String Lateness;
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_full_marks, vg, false);
        ItemNameTxt = view.findViewById(R.id.NameItem);
        VypiskaTxt = view.findViewById(R.id.Vypiska);
        ItogTxt = view.findViewById(R.id.Itog);
        NoShowTxt = view.findViewById(R.id.NoShow);
        PassTxt = view.findViewById(R.id.Pass);
        DiseaseTxt = view.findViewById(R.id.Disease);
        LatenessTxt = view.findViewById(R.id.Lateness);

        Log.d("fullInfo", String.valueOf(ItemNameTxt.getText()));

        MarksInfoFragment.Load(XLSParser.PositionSelected);
        return view;
    }

    public static void Load(int pos) {

        List<List> All = XLSParser.Items;
        if (!All.isEmpty()){
        ItemName = (String) All.get(0).get(pos);
        Vypiska = (String) All.get(1).get(pos);
        Itog = (Double) All.get(2).get(pos);
        NoShow = (String) All.get(3).get(pos);
        Pass = (String) All.get(4).get(pos);
        Disease = (String) All.get(5).get(pos);
        Lateness = (String) All.get(6).get(pos);

        Log.d("fullInfo", String.valueOf(pos));
        ItemNameTxt.setText(ItemName);
        VypiskaTxt.setText(Vypiska);
            if (Itog >= 4.5) ItogTxt.setTextColor(contextt.getResources().getColor(R.color.Five));
            else if (Itog >= 3.5) ItogTxt.setTextColor(contextt.getResources().getColor(R.color.Four));
            else if (Itog >= 2.5) ItogTxt.setTextColor(contextt.getResources().getColor(R.color.Three));
            else if (Itog >= 1.5) ItogTxt.setTextColor(contextt.getResources().getColor(R.color.Two));
            else ItogTxt.setTextColor(contextt.getResources().getColor(android.R.color.tab_indicator_text));
        ItogTxt.setText(Itog.toString());
        NoShowTxt.setText(NoShow);
        PassTxt.setText(Pass);
        DiseaseTxt.setText(Disease);
        LatenessTxt.setText(Lateness);}
        else XLSParser.PutErrorCode("LIST_OF_MARKS_INFO_EMPTY");
    }
}
