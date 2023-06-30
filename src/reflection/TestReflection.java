package reflection;

import data.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

public class TestReflection {
    @Test
    public void checkStatusByReflection() throws IllegalAccessException {
        Object status = new Status();
        //пользуемся интерцепцией, чтобы узнать о свойствах класса
        Field[] fields = status.getClass().getDeclaredFields();
        System.out.println(fields[0].getDeclaredAnnotations()[0]);
        System.out.println(fields[0].getType());
        System.out.println(fields[0].getName());

        System.out.println(((Status) status).threshold);

        //используем рефлексию, чтобы засетить значение поля в классе
        //для тестов ок, но не для прода
        Long value = 11000L;
        fields[0].set(status, value);
        System.out.println(((Status) status).threshold);
        System.out.println(((Status) status));
    }

    @Test
    public void checkTestObject() throws IllegalAccessException {
        Object testObject = new TestObject();
        Field[] fields = testObject.getClass().getDeclaredFields();
        int i=0;
        while ((i<fields.length) && !fields[i].getName().equals("privateString")){
            i++;
        }

        //мы меняем модификатор доступа c private на public, чтобы заработал set
        fields[i].setAccessible(true);

        fields[i].set(testObject, "changedString");
        Assertions.assertEquals("changedString", ((TestObject) testObject).getStringValue(), "Value not changed");
    }

    @Test
    public void getMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object testObject = new TestObject();
        //ищем метод в классе
        Method method = testObject.getClass().getMethod("setStringValue", String.class);
        //вызываем найденный метод
        method.invoke(testObject, "print str");
        Assertions.assertEquals("print str", ((TestObject) testObject).getStringValue(), "unexpected string");

        //TO DO метод в классе переопределить
    }

    @Test
    public void overrideMethod() throws NoSuchMethodException, ClassNotFoundException {

        Class<?> testInterface = Class.forName("reflection.TestInterface");
        TestInterface testProxy= (TestInterface) Proxy.newProxyInstance(TestInterface.class.getClassLoader(),new Class<?>[]{TestInterface.class},new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                if (method.getName().equals("getValue")) return "any string";
                return null;
            }
        });
        Assertions.assertEquals("any string", testProxy.getValue(), "expected 'any string'");
    }
}
