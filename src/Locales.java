
import java.util.List;
import com.google.gson.annotations.SerializedName;

class Locales extends BaseData{
    @SerializedName("available_locales") List<String> availableLocales;
    @SerializedName("default_locale") String defaultLocale;
}