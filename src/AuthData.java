import com.google.gson.annotations.SerializedName;

public class AuthData extends BaseData{
    @SerializedName("authtoken") String authtoken;
    @SerializedName("id") Long id;
}
