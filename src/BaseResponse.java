import com.google.gson.annotations.SerializedName;
class BaseResponse<T extends BaseData> {
    @SerializedName("data") T data;
}
