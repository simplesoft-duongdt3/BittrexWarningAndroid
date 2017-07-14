package duongmh3.bittrexmanager.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import duongmh3.bittrexmanager.listcoin.CoinViewModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static duongmh3.bittrexmanager.model.WarningResultModel.Result.ERROR;
import static duongmh3.bittrexmanager.model.WarningResultModel.Result.NORMAL;
import static duongmh3.bittrexmanager.model.WarningResultModel.Result.WARNING;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by admin on 7/8/17.
 */

@Setter
@Getter
public class WarningResultModel implements Serializable {
    @IntDef(value = {NORMAL, WARNING, ERROR})
    @Retention(RUNTIME)
    public @interface Result {
        int NORMAL = 1;
        int WARNING = 2;
        int ERROR = 3;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Result
    private int result;
    private long timeStart;
    private long timeEnd;
    private String errorLog;

    @NonNull
    private final ArrayList<CoinViewModel> coinViewModels = new ArrayList<>();

    @Result
    public int getResult() {
        return result;
    }

    public void setResult(@Result int result) {
        this.result = result;
    }

    public long getTotalTime() {
        return timeEnd - timeStart;
    }
}
