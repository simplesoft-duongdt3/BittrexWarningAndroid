package duongmh3.bittrexmanager.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import duongmh3.bittrexmanager.R;
import duongmh3.bittrexmanager.model.MarketSummaryModel;
import duongmh3.bittrexmanager.model.WarningResultModel;
import duongmh3.bittrexmanager.model.WarningSettingModel;
import io.paperdb.Paper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BittrexCheckInfoIntentService extends IntentService {
    @NonNull
    private static final OkHttpClient client = new OkHttpClient();
    private final Handler handler;

    public BittrexCheckInfoIntentService() {
        super("BittrexCheckInfoIntentService");
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WarningResultModel warningResultModel = new WarningResultModel();
        warningResultModel.setResult(WarningResultModel.Result.ERROR);
        warningResultModel.setTimeStart(System.currentTimeMillis());
        if (intent != null) {
            Request request = new Request.Builder()
                    .url("https://bittrex.com/api/v1.1/public/getmarketsummaries")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    MarketSummaryModel marketSummaryModel = new Gson().fromJson(response.body().charStream(), MarketSummaryModel.class);
                    if (marketSummaryModel.isSuccess()) {
                        WarningSettingModel warningSettingViewModel = loadConfig();
                        HashMap<String, WarningSettingModel.SettingWarningItem> mapSetting = new HashMap<>();
                        List<WarningSettingModel.SettingWarningItem> lstSettingWarning = warningSettingViewModel.getLstSettingWarning();
                        for (WarningSettingModel.SettingWarningItem settingWarningItem : lstSettingWarning) {
                            mapSetting.put(settingWarningItem.getMarketName(), settingWarningItem);
                        }

                        boolean isWarning = false;
                        for (MarketSummaryModel.Result result : marketSummaryModel.getResult()) {
                            WarningSettingModel.SettingWarningItem settingWarningItem = mapSetting.get(result.getMarketName());
                            if (settingWarningItem != null) {
                                boolean isUpWarning = Double.valueOf(result.getLast()) >= Double.valueOf(settingWarningItem.getMax());
                                boolean isDownWarning = Double.valueOf(result.getLast()) <= Double.valueOf(settingWarningItem.getMin());

                                if (isDownWarning || isUpWarning) {
                                    isWarning = true;
                                    break;
                                }
                            }
                        }

                        if (isWarning) {
                            warningResultModel.setResult(WarningResultModel.Result.WARNING);
                            warningUser();
                        } else {
                            warningResultModel.setResult(WarningResultModel.Result.NORMAL);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                warningResultModel.setResult(WarningResultModel.Result.ERROR);
            }

            warningResultModel.setTimeEnd(System.currentTimeMillis());
            warningResultModel.calTotalTime();
            sendWarningBroadcast(warningResultModel);
            SyncWakefulReceiver.completeWakefulIntent(intent);
        }
    }

    private void warningUser() {
        if (Util.isPlaySoundWhenWarning()) {
            playWarningShow();
        }

        if (Util.isVibrateWhenWarning()) {
            vibrate();
        }
    }

    private void playWarningShow() {
        try {
            MediaPlayer player = MediaPlayer.create(this, R.raw.warning);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            player.setLooping(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void vibrate() {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(4000);
    }

    private static WarningSettingModel loadConfig() {
        WarningSettingModel warningSettingModel = Paper.book().read("config_warning");
        if (warningSettingModel == null) {
            getConfigWarningFromCloud();
            warningSettingModel = Paper.book().read("config_warning");
        }
        return warningSettingModel;
    }

    private static void getConfigWarningFromCloud() {
        Request request = new Request.Builder()
                .url("https://shebeauty.com.vn/bittrex_warning_config.json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                WarningSettingModel warningSettingModel = new Gson().fromJson(response.body().charStream(), WarningSettingModel.class);
                Paper.book().write("config_warning", warningSettingModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallbackGetConfig {
        void onDoneEvent(boolean success);
    }

    public static void getConfigWarningFromCloudAsync(@NonNull final CallbackGetConfig callbackGetConfig) {
        Request request = new Request.Builder()
                .url("https://shebeauty.com.vn/bittrex_warning_config.txt")
                .build();

        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callbackGetConfig.onDoneEvent(false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        WarningSettingModel warningSettingModel = new Gson().fromJson(response.body().charStream(), WarningSettingModel.class);
                        Paper.book().write("config_warning", warningSettingModel);
                        callbackGetConfig.onDoneEvent(true);
                    } else {
                        callbackGetConfig.onDoneEvent(false);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWarningBroadcast(WarningResultModel result) {
        Intent intent = new Intent("WarningBroadcast");
        intent.putExtra("result", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
