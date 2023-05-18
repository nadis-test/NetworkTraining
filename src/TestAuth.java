
import data.*;

import kotlin.Pair;
import modules.auth.AuthTokenLoader;
import modules.auth.AuthTokenStorage;
import network.ApiServices;
import network.ApiServicesBuilder;
import network.HttpClient;
import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import responses.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TestAuth {
    private static final AuthTokenStorage authTokenStorage = new AuthTokenStorage();
    private static final ApiServices apiServices = ApiServicesBuilder.createApiServices(authTokenStorage);
    @BeforeAll
    public static void setUpTest() throws IOException{
        // сохраняем токен для использования в других тестов, например для тестов банка
        AuthTokenLoader authTokenLoader = new AuthTokenLoader(apiServices); //передаем в конструктор, чтобы метод лоадера не зависел от способа загрузки
        AuthData authData =  authTokenLoader.loadAuthToken(); //загружаем autData через отдельный метод в отдельном классе, чтобы не было циклической зависимости
        authTokenStorage.setAuthData(authData);
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
        for (data.Error er : response.errors) {
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
    public void testLocales() throws IOException {
        retrofit2.Call<LocalesResponse> localesCall = apiServices.getLocales();
        retrofit2.Response<LocalesResponse> localesResponse = localesCall.execute();
        System.out.println(localesResponse.body().data.availableLocales);
        Locales locales = localesResponse.body().data;

        List<String> localesExpected = Arrays.asList("ru","en","id","kz");

        //value -> {return !localesExpected.contains(value);} = лямбда для фильтрации данных
        //все, что прошло через этот фильтр - дальше идет в стрим и там обрабатывается следующими методами
        //в нашем случе методом collect собирается в list
        List<String> localesNotExpected = locales.availableLocales.stream().distinct()
                .filter(value -> {return !localesExpected.contains(value);})
                .collect(Collectors.toList());

        Assertions.assertEquals( 200, localesResponse.code());
        Assertions.assertEquals( "en", locales.defaultLocale);
        Assertions.assertTrue(localesNotExpected.isEmpty(), "Unexpected locales are present: " + localesNotExpected);

    }

    @Test
    public void checkAssetRespose() throws IOException {
        retrofit2.Call<AssetsResponse> assetsCall = apiServices.getAssetData();
        retrofit2.Response<AssetsResponse> assetsResponse = assetsCall.execute();

        Assets assets = assetsResponse.body().data;
        Asset assetEURMXN = assets.assets.stream().filter(asset -> asset.name.equals("EUR/MXN")).findFirst().get();

        Assertions.assertEquals(1, assetEURMXN.type, "Type is unexpected");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mon", "tue", "sun"})
    public void checkAssetSchedule(String day) throws  IOException{
        retrofit2.Call<AssetsResponse> assetsCall = apiServices.getAssetData();
        retrofit2.Response<AssetsResponse> assetsResponse = assetsCall.execute();

        Asset asset = assetsResponse.body().data.assets.get(0);
        String start_hour  = asset.trading_tools_settings.standard.schedule.get(day)[0][0];
        String end_hour  = asset.trading_tools_settings.standard.schedule.get(day)[0][1];
        String start_hh = start_hour.split(":")[0];
        String start_mm = start_hour.split(":")[0];


        Map<String, List<Pair<LocalDate, LocalDate>>> schedule = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
/*
        for (Map.Entry<String, String[][]> set : asset.trading_tools_settings.standard.schedule.entrySet()) {
            String key = set.getKey();
            String[][] value = set.getValue();
            List<Pair<LocalDate, LocalDate>> list = new ArrayList<>();

            for (String[] string : value) {
                LocalDate dateStart = LocalDate.parse(string[0], formatter);
                LocalDate dateEnd = LocalDate.parse(string[1], formatter);
                Pair<LocalDate, LocalDate> pair = new Pair<>(dateStart, dateEnd);
                list.add(pair);
            }

            schedule.put(key, list);
        }

 */

        asset.trading_tools_settings.standard.schedule.entrySet().stream()
                .map(set -> {
                    List<Pair<LocalDate, LocalDate>> list = Arrays.stream(set.getValue())
                            .map(value -> {
                                LocalDate dateStart = LocalDate.parse(value[0], formatter);
                                LocalDate dateEnd = LocalDate.parse(value[1], formatter);
                                return new Pair<>(dateStart, dateEnd);
                            })
                            .collect(Collectors.toList());
                    return list;
                });


        List<Integer> testlist = Arrays.asList(1,3,5,7);

        //перобразуем массив интов в массив строк
         List<String> listString = testlist.stream()
                .map(Object::toString)
                 .collect(Collectors.toList());


         listString.forEach(string -> {System.out.println(string);});

         //поиск значения по условию
         int number = testlist.stream()
                 .filter(value -> value == 5)
                 .findFirst()
                 .get();

         //все ли значения в массиве нечетные
        boolean checkOdd = testlist.stream()
                .allMatch(value -> (value % 2 != 0));

        //перобразуем массив интов в массив строк - через ссылку на метод
        List<String> listString1 = testlist.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        //while (schedule.)


        //assertTrue();
    }

    @Test
    public void checkStatuses() throws IOException {
        retrofit2.Call<StatusResponse> statusCall = apiServices.getStatusesData();
        retrofit2.Response<StatusResponse> statusResponse = statusCall.execute();

        List<String> statusList = statusResponse.body().data.stream()
                .map(status -> {
                    return status.status_group;
                })
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList("free", "standard", "gold", "vip"),statusList, "lists are not equal: \n" + statusList);

        //на следующий раз: сделать мапу и проверить пороги для статусов

        //превращаем объект в мапу
        Map<String, Long> statuses = statusResponse.body().data.stream()
                .collect(Collectors.toMap(
                    status -> status.status_group,
                        status -> status.threshold));

        Map<String, Long> statusesExpected = new HashMap<>();
        statusesExpected.put("free", 0L);
        statusesExpected.put("standard", 5000L);
        statusesExpected.put("gold", 250000L);
        statusesExpected.put("vip", 500000L);
        //данные по порогам

        Assertions.assertEquals(statusesExpected, statuses, "The threshold values are not as expected");

        //тесты на профиль и его редактирование

    }

}
