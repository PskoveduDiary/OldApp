package com.Alex.diary.ui.error;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.R;


public class ErrorFragment extends Fragment {

    static TextView ErrorCodeTxt;
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.error_fragment, vg, false);
        ErrorCodeTxt = view.findViewById(R.id.ErrorCode);
        return view;
    }

    public static void Error(String ErrorMessage) {
        if (ErrorCodeTxt!=null) ErrorCodeTxt.setText(ErrorMessage);
    }
}
