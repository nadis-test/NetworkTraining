package data;

import com.google.gson.annotations.SerializedName;

public class Status extends BaseData {
    @SerializedName("threshold") public Long threshold;
    @SerializedName("status_group") public String status_group;
}
