package duongmh3.bittrexmanager.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class SyncWakefulReceiver extends WakefulBroadcastReceiver {
    public static void startSyncService(Context context) {
        Intent serviceIntent = new Intent(context, BittrexCheckInfoIntentService.class);
        SyncWakefulReceiver.startWakefulService(context, serviceIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        startSyncService(context);
    }
}
