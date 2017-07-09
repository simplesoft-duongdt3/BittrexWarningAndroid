package duongmh3.bittrexmanager.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import duongmh3.bittrexmanager.R;
import duongmh3.bittrexmanager.model.WarningResultModel;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

public class WarningChatHeadService extends Service implements FloatingViewListener {
    private static final String TAG = "ChatHeadService";

    private static final int NOTIFICATION_ID = 9083150;

    /**
     * FloatingViewManager
     */
    private FloatingViewManager mFloatingViewManager;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final WarningResultModel result = (WarningResultModel) intent.getSerializableExtra("result");
            if (result != null && iconView != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        iconView.setVisibility(View.VISIBLE);
                        String status = "";
                        @DrawableRes
                        int bgNotification;
                        if (result.getResult() == WarningResultModel.Result.NORMAL) {
                            status = "normal";
                            bgNotification = R.drawable.circle_success;
                            iconView.setBackgroundResource(R.drawable.circle_success);
                        } else if (result.getResult() == WarningResultModel.Result.WARNING) {
                            status = "warning";
                            iconView.setBackgroundResource(R.drawable.circle_warning);
                            bgNotification = R.drawable.circle_warning;
                        } else {
                            status = "error";
                            iconView.setBackgroundResource(R.drawable.circle_error);
                            bgNotification = R.drawable.circle_error;
                        }


                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(result.getTimeEnd());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                        String timeFormat = simpleDateFormat.format(calendar.getTime());
                        iconView.setText(timeFormat);
                        startForeground(NOTIFICATION_ID, createNotification(timeFormat + ": " + status, bgNotification));
                    }
                });
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
        options.animateInitialMove = true;
        mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        //options.overMargin = (int) (8 * metrics.density);
        mFloatingViewManager.addViewToWindow(iconView, options);
        startForeground(NOTIFICATION_ID, createNotification("Starting...", R.drawable.circle_success) );

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

    private Notification createNotification(String contentWarning, @DrawableRes int backgroundNotification) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(backgroundNotification);
        builder.setContentTitle("Bittrex warning");
        builder.setContentText(contentWarning);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);

        return builder.build();
    }
}
