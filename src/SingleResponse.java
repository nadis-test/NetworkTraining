import com.google.gson.annotations.SerializedName;

public class SingleResponse<T extends BaseData> extends BaseResponse{
    @SerializedName("data") T data;
}
