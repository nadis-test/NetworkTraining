package data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assets extends BaseData {
    @SerializedName("assets") public List<Asset> assets;
}
