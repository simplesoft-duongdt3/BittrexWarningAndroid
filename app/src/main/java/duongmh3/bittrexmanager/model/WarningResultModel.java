package duongmh3.bittrexmanager.model;

import android.support.annotation.IntDef;

import java.io.Serializable;

import static duongmh3.bittrexmanager.model.WarningResultModel.Result.ERROR;
import static duongmh3.bittrexmanager.model.WarningResultModel.Result.NORMAL;
import static duongmh3.bittrexmanager.model.WarningResultModel.Result.WARNING;

/**
 * Created by admin on 7/8/17.
 */

public class WarningResultModel implements Serializable {
    public void calTotalTime() {
        totalTimeProcess = timeEnd - timeStart;
    }

    @IntDef(value = {NORMAL, WARNING, ERROR})
    public @interface Result {
        int NORMAL = 1;
        int WARNING = 2;
        int ERROR = 3;
    }
    @Result
    private int result;
    private long timeStart;
    private long timeEnd;
    private long totalTimeProcess;

    @Result
    public int getResult() {
        return result;
    }

    public void setResult(@Result int result) {
        this.result = result;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getTotalTimeProcess() {
        return totalTimeProcess;
    }

}
