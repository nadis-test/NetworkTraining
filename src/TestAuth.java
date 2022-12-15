import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAuth {

    public static void main(String[] args) throws IOException {
        Network test = new Network();
        LocalesResponse locales = test.get("https://api.binomo.com/platform/locales", LocalesResponse.class, null);
        Locales data = locales.data;
        Assertions.assertEquals("en", data.defaultLocale);
        Assertions.assertTrue(data.availableLocales.contains("ru"));
        //testAuth();
        testAuthErrors();
    }

    public static void testAuth() throws IOException{
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
        Assertions.assertNotNull(response.data.authtoken);
        Assertions.assertEquals(149320504, response.data.id);
    }

    public static void testAuthErrors() throws IOException{
        Map<String, String> headers = new HashMap<>();
        headers.put("Device-Type",	"android");
        headers.put("Device-Id",	"1669807052602-1096312346268349946");
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
