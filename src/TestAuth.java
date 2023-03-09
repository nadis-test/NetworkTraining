
import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAuth {

    public static String authtoken;
    private static final AuthTokenStorage authTokenStorage = new AuthTokenStorage();
    private static final ApiServices apiServices = ApiServicesBuilder.createApiServices(authTokenStorage);
    @BeforeAll
    public static void setUpTest() throws IOException{
        // сохраняем токен для использования в других тестов, например для тестов банка
        authTokenStorage.loadAuthToken(apiServices);
    }

    @Test
    public void testAuth() throws IOException{
        SignInData signInData = new SignInData();
        signInData.email = "martest1512@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        retrofit2.Call<SignInResponse> signInCall = apiServices.signIn(signInData);
        retrofit2.Response<SignInResponse> signInResponse = signInCall.execute();

        SignInResponse response = signInResponse.body();
        ResponseBody errorBody = signInResponse.errorBody();
        Assertions.assertNotNull(response);
        Assertions.assertNull(errorBody);

        Assertions.assertNotNull(response.data.authtoken);
        Assertions.assertEquals(149671731, response.data.id);
    }


    @Test
    public void testBankResponse() throws IOException{


        retrofit2.Call<BankResponse> bankResponseCall = apiServices.bankData();
        retrofit2.Response<BankResponse> bankResponse = bankResponseCall.execute();


        BankResponse response = bankResponse.body();
        Assertions.assertFalse(response.data.isEmpty()); //проверяем, что вернулся непустой объект от банка
        String[] accounts = response.data.stream().map(account -> account.account_type).sorted().toArray(String[]::new);
        // обходим наш List, вытаскиваем account_type и складываем в массив со String и еще сортируем

        Assertions.assertArrayEquals(new String[]{"demo","real"}, accounts);
        Assertions.assertNull(response.errors);
    }

    @Test
    public void testAuthErrors() throws IOException{
        SignInData signInData = new SignInData();
        signInData.email = "test1872469812375498327@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        retrofit2.Call<SignInResponse> signInCall = apiServices.signIn(signInData);
        retrofit2.Response<SignInResponse> signInResponse = signInCall.execute();

        SignInResponse response = signInResponse.body();
        System.out.println(signInResponse.errorBody().string());

        ResponseBody errorBody = signInResponse.errorBody();
        Assertions.assertNotNull(errorBody);
        Assertions.assertNull(response);

        /*
        Assertions.assertNotNull(response.errors);
        for (Error er : response.errors) {
            Assertions.assertEquals("sign_in", er.field);
            Assertions.assertNotNull(er.message);
            Assertions.assertEquals("wrong_credentials", er.code);
        }

         */

    }

    @Test
    public void testHttpOk() throws IOException {
        OkHttpClient client = HttpClient.createOkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.binomo.com/platform/locales")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        Assertions.assertEquals( 200, response.code());
    }

    @Test
    public void testRetrofit() throws IOException {
 // ApiServices apiServices = ApiServicesBuilder.createApiServices(); - сделали полем класса, чтобы не создавать в каждом тесте
        retrofit2.Call<LocalesResponse> localesCall = apiServices.getLocales();
        retrofit2.Response<LocalesResponse> localesResponse = localesCall.execute();
        System.out.println(localesResponse.body().data.availableLocales);
        Locales locales = localesResponse.body().data;

        Assertions.assertEquals( 200, localesResponse.code());
        Assertions.assertEquals( "en", locales.defaultLocale);
    }

}
