package com.seuic.smartgateway;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Camera extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }
}
