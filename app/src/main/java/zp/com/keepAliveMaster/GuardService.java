package zp.com.keepAliveMaster;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class GuardService extends Service {

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext());
            if (!isServiceRunning) {
                Intent i = new Intent(GuardService.this, DownloadService.class);
                startService(i);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 断开链接
            startService(new Intent(GuardService.this, StepService.class));
            // 重新绑定
            bindService(new Intent(GuardService.this, StepService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new KeepAliveConnection.Stub() {
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        // 绑定建立链接
        bindService(new Intent(this, StepService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

}