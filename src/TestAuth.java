
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAuth {

    public static String authtoken;
    @BeforeAll
    public static void setUpTest() throws IOException{
        // сохраняем токен для использования в других тестов, например для тестов банка
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Type",	"android");
        headers.put("Device-Id",	"1669807052602-1096312346268349946");
        SignInData signInData = new SignInData();
        signInData.email = "martest0112@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        Network test = new Network();
        SignInResponse response = test.post("https://api.binomo.com/platform/v2/sign_in?locale=en", SignInResponse.class, signInData,headers);
        authtoken = response.data.authtoken;
    }

    @Test
    public void testAuth() throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Type",	"android");
        headers.put("Device-Id",	"1669807052602-1096312346268349945");
        SignInData signInData = new SignInData();
        signInData.email = "martest1512@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        Network test = new Network();
        SignInResponse response = test.post("https://api.binomo.com/platform/v2/sign_in?locale=en", SignInResponse.class, signInData,headers);
        Assertions.assertNotNull(response.data.authtoken);
        Assertions.assertEquals(149671731, response.data.id);
    }


    @Test
    public void testBankResponse() throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Type",	"android");
        headers.put("Device-Id",	"1669807052602-1096312346268349946");
        headers.put("Authorization-Token",	authtoken);

        Network test = new Network();
        BankResponse response = test.get("https://api.binomo.com/bank/v1/read?locale=en", BankResponse.class, headers);
        Assertions.assertFalse(response.data.isEmpty()); //проверяем, что вернулся непустой объект от банка
        String[] accounts = response.data.stream().map(account -> account.account_type).sorted().toArray(String[]::new);
        // обходим наш List, вытаскиваем account_type и складываем в массив со String и еще сортируем

        Assertions.assertArrayEquals(new String[]{"demo","real"}, accounts);
        Assertions.assertNull(response.errors);
    }

    @Test
    public void testAuthErrors() throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Type",	"android");
        headers.put("Device-Id",	"1669807052602-1096312346268349948");
        SignInData signInData = new SignInData();
        signInData.email = "test1872469812375498327@bit.ly";
        signInData.password = "qwe";
        signInData.remember_me = true;
        signInData.i_agree = true;

        Network test = new Network();
        SignInResponse response = test.post("https://api.binomo.com/platform/v2/sign_in?locale=en", SignInResponse.class, signInData,headers);
        Assertions.assertNull(response.data.authtoken);
        Assertions.assertNotNull(response.errors);
        for (Error er : response.errors) {
            Assertions.assertEquals("sign_in", er.field);
            Assertions.assertNotNull(er.message);
            Assertions.assertEquals("wrong_credentials", er.code);
        }
        //Assertions.assertEquals(149320504, response.data.id);
    }

}
