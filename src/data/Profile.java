package data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Profile extends BaseData {
    @SerializedName("id") public Long id;
    @SerializedName("email") public String email;
    @SerializedName("registration_country_iso") public String registration_country_iso;
    @SerializedName("status_group") public String status_group;
    @SerializedName("registered_at") public Date registered_at;
    @SerializedName("first_name") public String first_name;
    @SerializedName("last_name") public String last_name;
    @SerializedName("gender") public String gender;


}
