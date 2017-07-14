package duongmh3.bittrexmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 6/24/17.
 */
@Getter
@Setter
public class MarketSummaryModel implements Serializable {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<Result> result = new ArrayList<>();
    private static final long serialVersionUID = -2660256404184555623L;

    @Getter
    @Setter
    public static class Result implements Serializable {

        @SerializedName("MarketName")
        @Expose
        private String marketName;
        @SerializedName("High")
        @Expose
        private double high;
        @SerializedName("Low")
        @Expose
        private double low;
        @SerializedName("Volume")
        @Expose
        private String volume;
        @SerializedName("Last")
        @Expose
        private String last;
        @SerializedName("BaseVolume")
        @Expose
        private String baseVolume;
        @SerializedName("TimeStamp")
        @Expose
        private String timeStamp;
        @SerializedName("Bid")
        @Expose
        private String bid;
        @SerializedName("Ask")
        @Expose
        private String ask;
        @SerializedName("OpenBuyOrders")
        @Expose
        private int openBuyOrders;
        @SerializedName("OpenSellOrders")
        @Expose
        private int openSellOrders;
        @SerializedName("PrevDay")
        @Expose
        private String prevDay;
        @SerializedName("Created")
        @Expose
        private String created;
        private final static long serialVersionUID = 899819910919323443L;
    }
}