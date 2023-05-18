package data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class TradingToolsSettings {
    @SerializedName("standard") public Standard standard;

    public static class Standard{
        @SerializedName("schedule") public Map<String, String[][]> schedule;
    }

}

