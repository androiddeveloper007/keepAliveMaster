package zp.com.keepAliveMaster;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by ZP-PC on 2018/10/21.
 */
public class ServiceAliveUtils {

    public static boolean isServiceAlice(Context mContext) {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("zp.com.keepAliveMaster.DownloadService".equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }
}
