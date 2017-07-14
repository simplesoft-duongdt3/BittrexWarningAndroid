package duongmh3.bittrexmanager.listcoin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import duongmh3.bittrexmanager.R;
import duongmh3.bittrexmanager.common.Util;
import duongmh3.bittrexmanager.common.list.RecyclerItems;
import duongmh3.bittrexmanager.model.WarningResultModel;
import me.drakeet.multitype.MultiTypeAdapter;

public class ListCoinActivity extends AppCompatActivity {

    @NonNull
    private final RecyclerItems<CoinViewModel> coinViewModels = new RecyclerItems<>();
    @NonNull
    private final MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(coinViewModels);

    @BindView(R.id.rvListCoin)
    RecyclerView rvListCoin;

    @NonNull
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WarningResultModel result = (WarningResultModel) intent.getSerializableExtra("result");
            if (result != null) {
                renderWithData(result);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coin);
        ButterKnife.bind(this);

        initListView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("WarningBroadcast"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void initData() {
        WarningResultModel warningResultModel = (WarningResultModel) getIntent().getSerializableExtra("data");
        renderWithData(warningResultModel);
    }

    private void renderWithData(WarningResultModel warningResultModel) {
        coinViewModels.clear();
        ArrayList<CoinViewModel> coinViewModelsFromIntent = warningResultModel.getCoinViewModels();
        for (CoinViewModel coinViewModel : coinViewModelsFromIntent) {
            coinViewModel.setSpanSize(1);
        }
        this.coinViewModels.addAll(coinViewModelsFromIntent);
        multiTypeAdapter.notifyDataSetChanged();
    }

    private void initListView() {
        boolean isTablet = getResources().getBoolean(R.bool.tablet);
        int spanSize = isTablet ? 2 : 1;
        rvListCoin.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanSize);
        gridLayoutManager.setSpanSizeLookup(getSpanSizeLookup());
        rvListCoin.setLayoutManager(gridLayoutManager);
        multiTypeAdapter.register(CoinViewModel.class, new CoinDetailViewHolderBinder(this::onClickCoinViewItem));
        rvListCoin.setAdapter(multiTypeAdapter);
    }

    private void onClickCoinViewItem(CoinViewModel coinViewModel) {
        Util.showWebBrowserByMarketName(this, coinViewModel.getMarketName());
    }

    @NonNull
    private GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                CoinViewModel coinViewModel = coinViewModels.getNullableAt(position);
                int span = 1;
                if (coinViewModel != null) {
                    span = coinViewModel.getSpanSize();
                }
                return span;
            }

        };
    }
}
