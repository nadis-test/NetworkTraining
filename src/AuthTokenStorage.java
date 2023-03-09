import java.io.IOException;

public class AuthTokenStorage {
    private AuthData authData;

    public void loadAuthToken(ApiServices apiServices) throws IOException {
        SignInData signInData = new SignInData();
        signInData.email = "martest0112@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        retrofit2.Call<SignInResponse> signInCall = apiServices.signIn(signInData);
        retrofit2.Response<SignInResponse> signInResponse = signInCall.execute();

        SignInResponse response = signInResponse.body();
        authData = response.data;
    }

    public String getAuthToken(){

        return authData != null ? authData.authtoken : "";
    }
}
