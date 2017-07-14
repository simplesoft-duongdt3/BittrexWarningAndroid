package duongmh3.bittrexmanager.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by duongmatheo on 7/12/17.
 */

public class Util {
    public static void checkAndCancelTasks(AsyncTask task) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }
    }

    public static void showWebBrowserByMarketName(Context context, String marketName) {
        String url = "https://bittrex.com/Market/Index?MarketName=" + marketName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
