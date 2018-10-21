package zp.com.keepAliveMaster;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class ScheduleService extends JobService {
    private static final String TAG = ScheduleService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        boolean isServiceRunning = ServiceAliveUtils.isServiceAlice(getApplicationContext());
        if (!isServiceRunning) {
            Intent i = new Intent(this, DownloadService.class);
            startService(i);
            Log.d(TAG, "ScheduleService启动了DownloadService");
        }
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}