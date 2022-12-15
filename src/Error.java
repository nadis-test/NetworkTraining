import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("field") String field;
    @SerializedName("message") String message;
    @SerializedName("code") String code;
}
