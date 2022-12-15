import com.google.gson.annotations.SerializedName;

public class SignInData{
   @SerializedName("email") public String email;
    @SerializedName("password") public String password;
    @SerializedName("remember_me") public Boolean remember_me;
    @SerializedName( "i_agree") public Boolean i_agree;
}
