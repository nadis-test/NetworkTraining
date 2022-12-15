import com.google.gson.annotations.SerializedName;

import java.util.List;

class BaseResponse<T extends BaseData> {
    @SerializedName("data") T data;
    @SerializedName("errors") List<Error> errors;
}
