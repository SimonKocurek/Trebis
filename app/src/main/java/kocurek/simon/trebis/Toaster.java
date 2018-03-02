package kocurek.simon.trebis;

import android.widget.Toast;

public class Toaster {

    public void createToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

}
