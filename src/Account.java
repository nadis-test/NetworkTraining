import com.google.gson.annotations.SerializedName;

public class Account extends BaseData {
    public static class Relation{
        @SerializedName("id") Long id;
    }

    @SerializedName("amount") Long amount;
    @SerializedName("currency") String currency;
    @SerializedName("account_type") String account_type;
    @SerializedName("balance_version") Long balance_version;
}

