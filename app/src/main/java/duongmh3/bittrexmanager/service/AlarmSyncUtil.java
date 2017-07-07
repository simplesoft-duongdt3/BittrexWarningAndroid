package duongmh3.bittrexmanager.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by doanthanhduong on 2/3/17.
 */

public class AlarmSyncUtil {
    private static final int REQUEST_CODE_START_SERVICE = 1000;
    private static final long INTERVAL_IN_MILLISECOND = 10 * 1000L;

    public static void startAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = createIntentStartBroadcastReceiver(context);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), INTERVAL_IN_MILLISECOND, pendingIntent);
    }

    public static void stopAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = createIntentStartBroadcastReceiver(context);
        alarmManager.cancel(pendingIntent);
    }

    private static PendingIntent createIntentStartBroadcastReceiver(Context context) {
        Intent intent = new Intent(context, SyncWakefulReceiver.class);
        return PendingIntent.getBroadcast(context, REQUEST_CODE_START_SERVICE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
