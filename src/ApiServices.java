import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ApiServices {
    @GET("platform/locales")
    Call<LocalesResponse> getLocales();
    @POST("passport/v2/sign_in")
    Call<SignInResponse> signIn(@Body SignInData signInData);
    @GET("bank/v1/read")
    Call<BankResponse> bankData();
}

