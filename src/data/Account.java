package data;

import com.google.gson.annotations.SerializedName;

public class Account extends BaseData {
    public static class Relation{
        @SerializedName("id") Long id;
    }

    @SerializedName("amount") public Long amount;
    @SerializedName("currency") public String currency;
    @SerializedName("account_type") public String account_type;
    @SerializedName("balance_version") public Long balance_version;
}

