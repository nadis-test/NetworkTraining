package modules.auth;

import data.AuthData;

public class AuthTokenStorage {
    private AuthData authData;


    public void setAuthData(AuthData authData){
        this.authData = authData;
    }

    public String getAuthToken(){

        return authData != null ? authData.authtoken : "";
    }
}
