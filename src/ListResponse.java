import com.google.gson.annotations.SerializedName;

import java.util.List;

class ListResponse<T extends BaseData> extends BaseResponse{
    @SerializedName("data") List<T> data;  //пример: data типа Account будет получена в BankResponse

}
