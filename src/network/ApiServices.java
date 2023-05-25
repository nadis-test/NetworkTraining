package network;

import data.ProfileEditableData;
import data.SignInData;
import responses.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiServices {
    @GET("platform/locales")
    Call<LocalesResponse> getLocales();
    @POST("passport/v2/sign_in")
    Call<SignInResponse> signIn(@Body SignInData signInData);
    @GET("bank/v1/read")
    Call<BankResponse> bankData();

    @GET("platform/private/v4/assets")
    Call<AssetsResponse> getAssetData();

    @GET("platform/private/statuses")
    Call<StatusResponse> getStatusesData();

    @GET("platform/private/v2/profile")
    Call<ProfileResponse> getProfileData();

    @PATCH("platform/private/v2/profile/edit")
    Call<Void> editProfileData(@Body ProfileEditableData profileData);
}

