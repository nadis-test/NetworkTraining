import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.jupiter.api.Assertions;

public class Network {
    private static String getLocales() throws IOException {
        URL obj = new URL("https://api.binomo.com/platform/locales");
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
        connection.disconnect();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        String locales = getLocales();
        Gson gson = new Gson();
        BaseResponse response = gson.fromJson(locales, BaseResponse.class);
        Assertions.assertEquals("en", response.data.defaultLocale);
        Assertions.assertTrue (response.data.availableLocales.contains("ru"));
    }
}
