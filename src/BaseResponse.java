import com.google.gson.annotations.SerializedName;

import java.util.List;

class BaseResponse {

    @SerializedName("errors") List<Error> errors; // ошибки возвращаются для каждой модели
}
