package duongmh3.bittrexmanager;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import duongmh3.bittrexmanager.service.ServiceUtil;
import io.paperdb.Paper;

/**
 * Created by admin on 7/7/17.
 */

public class App extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        ServiceUtil.startServiceWarning(this);
    }
}
