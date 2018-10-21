package zp.com.keepAliveMaster;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mShowTimeTv;
    private DownloadService.DownloadBinder mDownloadBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
            mDownloadBinder.setOnTimeChangeListener(new DownloadService.OnTimeChangeListener() {
                @Override
                public void showTime(final String time) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mShowTimeTv.setText(time);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowTimeTv = findViewById(R.id.tv_show_time);
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //双守护线程，优先级不一样
//        startAllServices();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    /**
     * 开启所有守护Service
     */
    private void startAllServices() {
        startService(new Intent(this, StepService.class));
        startService(new Intent(this, GuardService.class));
    }
}
