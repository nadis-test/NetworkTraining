package reflection;

public class TestObject {
    private String privateString;
    public boolean publicBoolean;

    public String getStringValue(){
        return privateString;
    }

    public void setStringValue(String stringValue){
        privateString=stringValue;
    }

    public void printPrivateString(String str){
        System.out.println(str);
    }
}
