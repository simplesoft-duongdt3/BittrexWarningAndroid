package duongmh3.bittrexmanager.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import duongmh3.bittrexmanager.R;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

public class WarningChatHeadService extends Service implements FloatingViewListener {
    private static final String TAG = "ChatHeadService";

    /**
     * FloatingViewManager
     */
    private FloatingViewManager mFloatingViewManager;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            long timeRequest = intent.getLongExtra("time", 0);
            boolean result = intent.getBooleanExtra("result", false);
            if (iconView != null) {
                iconView.setVisibility(View.VISIBLE);
                if (result) {
                    iconView.setBackgroundResource(R.drawable.circle_success);
                } else {
                    iconView.setBackgroundResource(R.drawable.circle_fail);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeRequest);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                String timeFormat = simpleDateFormat.format(calendar.getTime());
                iconView.setText(timeFormat);
            }
        }
    };

    TextView iconView;
    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        final LayoutInflater inflater = LayoutInflater.from(this);
        iconView = (TextView) inflater.inflate(R.layout.widget_chathead, null, false);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click");
            }
        });

        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        options.shape = FloatingViewManager.SHAPE_CIRCLE;
        //options.overMargin = (int) (8 * metrics.density);
        mFloatingViewManager.addViewToWindow(iconView, options);
        //startForeground(NOTIFICATION_ID, createNotification());

        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver, new IntentFilter("WarningBroadcast"));
        return START_REDELIVER_INTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFinishFloatingView() {
        stopSelf();
        Log.d(TAG, "Finish");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (isFinishing) {
            Log.d(TAG, "Deleted");
        } else {
            Log.d(TAG, "x: " + x + " y: " + y);
        }
    }

    /**
     * Viewを破棄します。
     */
    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
