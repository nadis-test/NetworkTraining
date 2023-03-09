import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class CommonInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("Device-Type", "android")
                .addHeader("Device-Id", "1669807052602-1096312346268349948")
                .build();

        return chain.proceed(newRequest);
    }
}

        //headers.put("Device-Type",	"android");
              //  headers.put("Device-Id",	"1669807052602-1096312346268349948");

