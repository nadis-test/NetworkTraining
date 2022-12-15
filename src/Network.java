import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

public class Network {
    public static void main(String[] args) throws IOException {
        Network test = new Network();
        LocalesResponse locales = test.get("https://api.binomo.com/platform/locales", LocalesResponse.class, null);
        Locales data = locales.data;
        Assertions.assertEquals("en", data.defaultLocale);
        Assertions.assertTrue(data.availableLocales.contains("ru"));
    }

    private final Gson gson = new Gson();

    private <T extends BaseResponse> T request(String method, String url, Type type, String data, Map<String, String> headers) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(method);
        if (headers != null) {
            for (Map.Entry<String, String> it : headers.entrySet()) { //обходим список хэдеров
                connection.setRequestProperty(it.getKey(), it.getValue());
            }
        }

        if (data != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
            connection.setRequestProperty("Content-Type", "application/json");
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
                System.out.println(data);
            }
        }

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
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("GET request not worked");
        }
        System.out.println(response.toString());
        return gson.fromJson(response.toString(), type);
    }

    public <T extends BaseResponse> T get(String url, Type type, Map<String, String> headers) throws IOException {
        return request("GET", url, type, null, headers);
    }

    public <T extends BaseResponse> T post(String url, Type type, Object object, Map<String, String> headers) throws IOException {
        String data = gson.toJson(object);
        return request("POST", url, type, data, headers);
    }
}