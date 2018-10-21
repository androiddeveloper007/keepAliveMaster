package zp.com.keepAliveMaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class SinglePixelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 1;
        attrParams.width = 1;
        mWindow.setAttributes(attrParams);
        ScreenManager.getInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {
        if (!MyApplication.isAppAlive(this, MyApplication.getPackageName(this))) {
            Intent intentAlive = new Intent(this, DownloadService.class);
            startService(intentAlive);
        }
        super.onDestroy();
    }
}
