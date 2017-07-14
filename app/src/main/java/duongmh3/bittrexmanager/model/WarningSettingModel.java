package duongmh3.bittrexmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 6/25/17.
 */
@Getter
@Setter
public class WarningSettingModel implements Serializable {
    @SerializedName("lstSettingWarning")
    @Expose
    private ArrayList<SettingWarningItem> lstSettingWarning = new ArrayList<>();

    @Getter
    @Setter
    public static class SettingWarningItem implements Serializable {

        @SerializedName("marketName")
        @Expose
        private String marketName;
        @SerializedName("min")
        @Expose
        private String min;
        @SerializedName("max")
        @Expose
        private String max;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;
    }
}
