package network;

import modules.auth.AuthTokenStorage;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private AuthTokenStorage authTokenStorage;
    AuthInterceptor(AuthTokenStorage authTokenStorage){
        this.authTokenStorage = authTokenStorage;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Request newRequest = request.newBuilder()
                .addHeader("Authorization-Token", authTokenStorage.getAuthToken())
                .build();

        return chain.proceed(newRequest);
    }
}

