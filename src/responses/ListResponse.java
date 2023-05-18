package responses;

import com.google.gson.annotations.SerializedName;
import data.BaseData;

import java.util.List;

public class ListResponse<T extends BaseData> extends BaseResponse{
    @SerializedName("data") public List<T> data;  //пример: data типа data.Account будет получена в responses.BankResponse

}
