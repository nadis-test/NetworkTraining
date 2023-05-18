package network;

import com.google.gson.Gson;
import modules.auth.AuthTokenStorage;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServicesBuilder {
    public static ApiServices createApiServices(AuthTokenStorage authTokenStorage)
    {
        final Gson gson = new Gson();
        String locale = "en";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CommonInterceptor(locale))
                .addInterceptor(new AuthInterceptor(authTokenStorage))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.binomo.com/") //обязательно на слэш завершать урл
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit.create(ApiServices.class);
    }


}
