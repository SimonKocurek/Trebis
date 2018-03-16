package kocurek.simon.trebis.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public void createToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

}
