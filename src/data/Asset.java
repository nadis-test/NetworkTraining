package data;

import com.google.gson.annotations.SerializedName;

public class Asset {
    @SerializedName("id") public int id;
    @SerializedName("name") public String name;
    @SerializedName("ric") public String ric;
    @SerializedName("type") public int type;
    @SerializedName("sort") public int sort;
    @SerializedName("active") public boolean active;
    @SerializedName("trading_tools_settings") public TradingToolsSettings trading_tools_settings;
}
