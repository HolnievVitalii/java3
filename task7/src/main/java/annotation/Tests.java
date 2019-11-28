package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tests {

    Utility util;

    @Before
    public void starter(){
        util = new Utility(25, 13);
    }

    @Test(priority = 1)
    public void sumTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method sumMethod = Utility.class.getMethod("sum");
        sumMethod.setAccessible(true);
        sumMethod.invoke(Utility.class.getMethod("getA"),Utility.class.getMethod("getB") );
    }

    @Test(priority = 1)
    public void subTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method subMethod = Utility.class.getMethod("sub");
        subMethod.setAccessible(true);
        subMethod.invoke(Utility.class.getMethod("getA"),Utility.class.getMethod("getB") );
    }

    @Test(priority = 3)
    public void mulTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method mulMethod = Utility.class.getDeclaredMethod("mul");
        mulMethod.setAccessible(true);
        mulMethod.invoke(Utility.class.getMethod("getA"),Utility.class.getMethod("getB") );
    }

    @Test(priority = 3)
    public void divTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method divMethod = Utility.class.getMethod("div");
        divMethod.setAccessible(true);
        divMethod.invoke(Utility.class.getMethod("getA"),Utility.class.getMethod("getB") );
    }

    @Test(priority = 3)
    public void modTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method modMethod = Utility.class.getMethod("mod");
        modMethod.setAccessible(true);
        modMethod.invoke(Utility.class.getMethod("getA"),Utility.class.getMethod("getB") );
    }

    @After
    public void end() {
        System.err.println("All tests passed!");
    }
}
