package p32929.easypasscodelock.Utils;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by p32929 on 11/16/17.
 */

public class LockscreenHandler extends AppCompatActivity implements ComponentCallbacks2 {
    private static boolean wentToBg = false;
    private String packageName = "";
    private String TAG = "Fayaz";

    @Override
    public void onTrimMemory(int i) {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("topActivity", "CURRENT Activity ::"
                + taskInfo.get(0).topActivity.getClassName());

        if (taskInfo != null && taskInfo.size() > 0) {
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            packageName = componentInfo.getPackageName();
        }
        if (!packageName.equals("com.thamao") && i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            // We're in the Background
            wentToBg = true;
            Log.d(TAG, "wentToBg: " + wentToBg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (wentToBg && FayazSP.getString("password", null) != null) {
            // We're in the foreground & password != null
            wentToBg = false;
            Log.d(TAG, "wentToBg: " + wentToBg);

            EasyLock.checkPassword(this);
        }
    }
}
