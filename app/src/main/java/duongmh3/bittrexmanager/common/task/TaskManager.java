package duongmh3.bittrexmanager.common.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import duongmh3.bittrexmanager.common.Util;


/**
 * Created by duongmatheo on 7/12/17.
 */

public class TaskManager implements ITaskManager {
    public static final int MODE_SINGLE = 1;
    public static final int MODE_MULTI = 2;
    @NonNull
    List<AsyncTask> tasks = new ArrayList();

    public TaskManager() {
    }

    public <P> void executeTaskSingleMode(AsyncTask<P, ?, ?> task, P... params) {
        this.executeTask(task, 1, params);
    }

    public <P> void executeTaskMultiMode(AsyncTask<P, ?, ?> task, P... params) {
        this.executeTask(task, 2, params);
    }

    public <P> void executeTask(AsyncTask<P, ?, ?> task, int mode, P... params) {
        if (mode == 1) {
            task.execute(params);
        }

        this.addTaskIntoQueue(task);
    }

    private void addTaskIntoQueue(AsyncTask task) {
        if (!this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    public void destroy() {
        checkAndCancelTasks(tasks);

        this.tasks.clear();
    }

    private void checkAndCancelTasks(AsyncTask... tasks) {
        if (tasks != null && tasks.length > 0) {
            for (AsyncTask task : tasks) {
                cancelTask(task);
            }
        }
    }

    private void cancelTask(AsyncTask task) {
        Util.checkAndCancelTasks(task);
    }

    private void checkAndCancelTasks(Collection<AsyncTask> tasks) {
        for (AsyncTask task : tasks) {
            cancelTask(task);
        }
    }
}
