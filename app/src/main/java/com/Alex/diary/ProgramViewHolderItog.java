package com.Alex.diary;

import android.view.View;
import android.widget.TextView;

public class ProgramViewHolderItog {

    TextView ItemsItog;
    TextView I;
    TextView II;
    TextView III;
    TextView IV;
    // Get the handles by calling findViewById() on View object inside the constructor
    ProgramViewHolderItog(View v)
    {
        ItemsItog = v.findViewById(R.id.ItemItog);
        I = v.findViewById(R.id.I);
        II = v.findViewById(R.id.II);
        III = v.findViewById(R.id.III);
        IV = v.findViewById(R.id.IV);
    }
}
