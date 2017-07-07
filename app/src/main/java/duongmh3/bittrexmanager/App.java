package duongmh3.bittrexmanager;

import android.app.Application;

import duongmh3.bittrexmanager.service.AlarmSyncUtil;
import io.paperdb.Paper;

/**
 * Created by admin on 7/7/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);

        AlarmSyncUtil.startAlarm(this);
    }
}
