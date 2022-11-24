import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;

public class Network {
    private static final Gson gson = new Gson();
    private static <R extends BaseData> BaseResponse<R> get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } else {
            System.out.println("GET request not worked");
        }
        return gson.fromJson(response.toString(), BaseResponse.class);

    }

    public static void main(String[] args) throws IOException {
        LocalesResponse locales = get <Locales> ("https://api.binomo.com/platform/locales");
        Locales data = locales.data;
        Assertions.assertEquals("en", data.defaultLocale);
        Assertions.assertTrue (data.availableLocales.contains("ru"));
    }
}
