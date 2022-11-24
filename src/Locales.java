
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Locales {
    @SerializedName("available_locales") List<String> availableLocales;
    @SerializedName("default_locale") String defaultLocale;
}