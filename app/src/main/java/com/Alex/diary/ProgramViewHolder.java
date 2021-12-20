package com.Alex.diary;

import android.view.View;
import android.widget.TextView;

public class ProgramViewHolder {

    TextView Items;
    TextView Vypiska;
    TextView Itog;
    // Get the handles by calling findViewById() on View object inside the constructor
    ProgramViewHolder(View v)
    {
        Items = v.findViewById(R.id.textView1);
        Vypiska = v.findViewById(R.id.textView2);
        Itog = v.findViewById(R.id.textView3);
    }
}
