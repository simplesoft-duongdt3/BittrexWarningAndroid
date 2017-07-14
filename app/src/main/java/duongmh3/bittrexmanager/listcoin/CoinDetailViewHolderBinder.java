package duongmh3.bittrexmanager.listcoin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import duongmh3.bittrexmanager.R;
import duongmh3.bittrexmanager.common.event.EventFireUtil;
import duongmh3.bittrexmanager.common.event.OnActionData;
import duongmh3.bittrexmanager.common.list.RecyclerButterKnifeViewHolder;
import duongmh3.bittrexmanager.common.list.ViewHolderButterKnifeBinder;

/**
 * Created by duongmatheo on 7/12/17.
 */

public class CoinDetailViewHolderBinder extends ViewHolderButterKnifeBinder<CoinViewModel, CoinDetailViewHolderBinder.ViewHolder> {
    @Nullable
    private OnActionData<CoinViewModel> onClickItem;

    public CoinDetailViewHolderBinder(@Nullable OnActionData<CoinViewModel> onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.newInstance(inflater, parent);
        viewHolder.itemView.setFocusable(true);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull CoinViewModel item) {
        @DrawableRes
        int imgStatus = R.drawable.up;
        int colorPercent = Color.parseColor("#91DC5A");

        if (item.getStatus() == CoinViewModel.Status.DOWN) {
            colorPercent = Color.parseColor("#D80027");
            imgStatus = R.drawable.down;
        }

        String marketInfo = item.getMarketName() + " " + item.getStatusDescription() + "%";
        Spanny spannyCoinInfo = new Spanny(marketInfo, new ForegroundColorSpan(colorPercent), new StyleSpan(Typeface.BOLD));
        setText(viewHolder.tvCoinName, spannyCoinInfo);

        int colorMin = Color.DKGRAY;
        int colorMax = Color.DKGRAY;

        if (item.getWarningStatus() == CoinViewModel.Status.DOWN) {
            colorMin = Color.parseColor("#D80027");
        } else if (item.getWarningStatus() == CoinViewModel.Status.UP) {
            colorMax = Color.parseColor("#D80027");
        }

        Spanny spannyBid = new Spanny("BID: ", new StyleSpan(Typeface.BOLD)).append(item.getCurrentBid());
        setText(viewHolder.tvLastBid, spannyBid);
        Spanny spannyAsk = new Spanny("ASK: ", new StyleSpan(Typeface.BOLD)).append(item.getCurrentAsk());
        setText(viewHolder.tvLastAsk, spannyAsk);
        Spanny spannyMin = new Spanny("Min: " + item.getConfigMin(), new ForegroundColorSpan(colorMin));
        setText(viewHolder.tvConfigMin, spannyMin);
        Spanny spannyMax = new Spanny("Max: " + item.getConfigMax(), new ForegroundColorSpan(colorMax));
        setText(viewHolder.tvConfigMax, spannyMax);

        viewHolder.ivStatus.setImageResource(imgStatus);

        Glide.with(viewHolder.itemView.getContext()).load(item.getCoinImageUrl()).into(viewHolder.ivCoin);
        viewHolder.itemView.setOnClickListener(view -> EventFireUtil.fireEvent(onClickItem, item));
    }

    static class ViewHolder extends RecyclerButterKnifeViewHolder {
        @BindView(R.id.ivCoin)
        AppCompatImageView ivCoin;
        @BindView(R.id.tvCoinName)
        TextView tvCoinName;
        @BindView(R.id.ivStatus)
        ImageView ivStatus;
        @BindView(R.id.tvLastBid)
        TextView tvLastBid;
        @BindView(R.id.tvLastAsk)
        TextView tvLastAsk;
        @BindView(R.id.tvConfigMin)
        TextView tvConfigMin;
        @BindView(R.id.tvConfigMax)
        TextView tvConfigMax;

        public ViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, @LayoutRes int layoutId) {
            super(inflater, parent, layoutId);
        }

        public static ViewHolder newInstance(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new ViewHolder(inflater, parent, R.layout.coin_detail_view_holder);
        }
    }
}
