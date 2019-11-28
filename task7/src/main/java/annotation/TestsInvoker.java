package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestsInvoker {

    public void invokeTests(Class c) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = Tests.class.getDeclaredMethods();
        for (Method o : methods) {
            o.invoke(null);

        }

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class clazz = Tests.class;
        new TestsInvoker().invokeTests(clazz);
    }
}
