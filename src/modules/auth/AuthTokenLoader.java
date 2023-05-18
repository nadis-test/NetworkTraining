package modules.auth;

import data.AuthData;
import data.SignInData;
import network.ApiServices;
import responses.SignInResponse;

import java.io.IOException;

public class AuthTokenLoader {
    private ApiServices apiServices;

    public AuthTokenLoader(ApiServices apiServices){
        this.apiServices = apiServices;
    }

    public AuthData loadAuthToken() throws IOException {
        SignInData signInData = new SignInData();
        signInData.email = "martest0112@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        retrofit2.Call<SignInResponse> signInCall = apiServices.signIn(signInData);
        retrofit2.Response<SignInResponse> signInResponse = signInCall.execute();

        SignInResponse response = signInResponse.body();
        return response.data;
    }
}
