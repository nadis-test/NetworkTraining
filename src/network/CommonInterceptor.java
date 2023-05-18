package network;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class CommonInterceptor implements Interceptor {
    private String locale;

    CommonInterceptor(String locale){
        this.locale = locale;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        // добавили в запрос локаль
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("locale", this.locale)
                .build();

        Request newRequest = request.newBuilder()
                .addHeader("Device-Type", "android")
                .addHeader("Device-Id", "1669807052602-1096312346268349948")
                .url(url)  // используем урл с добавленной локалью
                .build();

        return chain.proceed(newRequest);
    }
}


