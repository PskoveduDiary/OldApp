package com.Alex.diary.ui.all_marks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.ProgramAdapter;
import com.Alex.diary.R;
import com.Alex.diary.XLSParser;

import java.util.List;


/**
 * MarksAllFragment is a fragment that displays all the marks of the student
 */
public class MarksAllFragment extends Fragment {

    static List<List> All;
    static View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        view = inflater.inflate(R.layout.fragment_all_marks, vg, false);
        return view;
    }
    /**
     * This function loads the data from the xls file into the screen
     */
    public static void Load(){
        All = XLSParser.Items;
        if (view!=null) {
            ListView listView = view.findViewById(R.id.list);
            if (!All.isEmpty()){
            // This is a part of the code that is responsible for displaying the data in the list.
            ProgramAdapter adapter = new ProgramAdapter(XLSParser.contextt, All.get(0), All.get(1), All.get(2));
            listView.setAdapter(adapter);
            Log.d("reader", All.toString());}
            else XLSParser.PutErrorCode("LIST_OF_MARKS_UI_LIST_EMPTY");
        }
        else XLSParser.PutErrorCode("VIEW_NOT_ASSIGNED_IN_MARKS_LIST");
    }
}
