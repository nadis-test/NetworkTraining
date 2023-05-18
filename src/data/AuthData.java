package data;

import com.google.gson.annotations.SerializedName;

public class AuthData extends BaseData{
    @SerializedName("authtoken") public String authtoken;
    @SerializedName("user_id") public Long id;
}
