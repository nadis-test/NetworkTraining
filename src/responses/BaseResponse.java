package responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {

    @SerializedName("errors") public List<Error> errors; // ошибки возвращаются для каждой модели
}
