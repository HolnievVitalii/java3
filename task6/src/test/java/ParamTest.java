import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParamTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {4, 1, 1}, // return true, TEST PASSED!!!
                {1, 1, 1}, // return false, TEST FAILED!!!
                {4, 4, 4}, //return false, TEST FAILED!!!
        });
    }

    private int a, b, c;

    public ParamTest(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private ForTests forTests;

    @Before
    public void prepare() {
        forTests = new ForTests();
    }


    @Test
    public void arrTest() {
        int[] arr = new int[]{a, b, c};
        Assert.assertTrue(forTests.check(arr));
    }


}
