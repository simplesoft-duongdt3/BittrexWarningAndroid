package duongmh3.bittrexmanager.listcoin;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import duongmh3.bittrexmanager.common.list.RecyclerViewHolder;
import duongmh3.bittrexmanager.common.list.RecyclerViewModel;
import duongmh3.bittrexmanager.model.MarketSummaryModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static duongmh3.bittrexmanager.listcoin.CoinViewModel.Status.DOWN;
import static duongmh3.bittrexmanager.listcoin.CoinViewModel.Status.NONE;
import static duongmh3.bittrexmanager.listcoin.CoinViewModel.Status.UP;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by duongmatheo on 7/12/17.
 */

@Getter
@Setter
public class CoinViewModel implements RecyclerViewModel {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @CoinViewModel.Status
    private int status;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @CoinViewModel.Status
    private int warningStatus;

    private String marketName;
    private String currentBid;
    private String currentAsk;
    private String configMin;
    private String configMax;
    private String coinImageUrl;
    private String statusDescription;
    private int spanSize;


    @IntDef(value = {UP, DOWN, NONE})
    @Retention(RUNTIME)
    public @interface Status {
        int UP = 1;
        int DOWN = 2;
        int NONE = 3;
    }

    @Status
    public int getStatus() {
        return status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }

    @Status
    public int getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(@Status int warningStatus) {
        this.warningStatus = warningStatus;
    }
}
