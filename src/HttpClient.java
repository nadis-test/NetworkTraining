import okhttp3.OkHttpClient;

public class HttpClient {
    public static OkHttpClient createOkHttpClient()
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        return client;
    }
}
