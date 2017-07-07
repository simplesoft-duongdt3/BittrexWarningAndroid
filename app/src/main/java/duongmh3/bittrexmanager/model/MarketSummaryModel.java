package duongmh3.bittrexmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/24/17.
 */
public class MarketSummaryModel implements Serializable {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
    private final static long serialVersionUID = -2660256404184555623L;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

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
        private double baseVolume;
        @SerializedName("TimeStamp")
        @Expose
        private String timeStamp;
        @SerializedName("Bid")
        @Expose
        private double bid;
        @SerializedName("Ask")
        @Expose
        private double ask;
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

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public double getBaseVolume() {
            return baseVolume;
        }

        public void setBaseVolume(double baseVolume) {
            this.baseVolume = baseVolume;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public double getBid() {
            return bid;
        }

        public void setBid(double bid) {
            this.bid = bid;
        }

        public double getAsk() {
            return ask;
        }

        public void setAsk(double ask) {
            this.ask = ask;
        }

        public int getOpenBuyOrders() {
            return openBuyOrders;
        }

        public void setOpenBuyOrders(int openBuyOrders) {
            this.openBuyOrders = openBuyOrders;
        }

        public int getOpenSellOrders() {
            return openSellOrders;
        }

        public void setOpenSellOrders(int openSellOrders) {
            this.openSellOrders = openSellOrders;
        }

        public String getPrevDay() {
            return prevDay;
        }

        public void setPrevDay(String prevDay) {
            this.prevDay = prevDay;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

    }
}