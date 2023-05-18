package responses;

import com.google.gson.annotations.SerializedName;
import data.BaseData;

public class SingleResponse<T extends BaseData> extends BaseResponse{
    @SerializedName("data") public T data;
}
