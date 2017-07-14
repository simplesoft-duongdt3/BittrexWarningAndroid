package duongmh3.bittrexmanager.common.task;

import android.os.AsyncTask;

/**
 * Created by admin on 7/11/17.
 */

public interface ITaskManager {
    <P> void executeTaskSingleMode(AsyncTask<P, ?, ?> task, P... params);

    void destroy();
}
