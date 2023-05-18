package data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Locales extends BaseData{
    @SerializedName("available_locales") public List<String> availableLocales;
    @SerializedName("default_locale") public String defaultLocale;
}