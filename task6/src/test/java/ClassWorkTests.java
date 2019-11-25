import classwork.Ex2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

@RunWith(Parameterized.class)
public class ClassWorkTests {

    @Parameterized.Parameters
    public static Integer [][] data(){
        Random rand = new Random();
        Integer [][] a = new Integer[10][4];
        for (int i = 0; i < 10; i++) {
            a[i][0] = rand.nextInt(100);
            a[i][1] = rand.nextInt(100);
            a[i][2] = a[i][0] + a[i][1];
            a[i][3] = a[i][0] - a[i][1];
        }
        return a;
    }

    public int a, b, c, d;
    Ex2 in;

    public ClassWorkTests(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        in = new Ex2();
    }

    @Test
    public void test1() {
        Assert.assertEquals(c, in.sum(a, b));
        Assert.assertEquals(1/3f, 0.3333333333333, 0.000000001);
        System.out.println(1 / 3f + " " + 5 / 15f);
    }

    @Test
    public void test2() {
        Assert.assertEquals(d, in.sub(a, b));
    }

    Ex2 inst;
    int [] arr;
    static Random r;
    @Before
    public void prepare() {
        inst = new Ex2();
        arr = new int[100000];
        r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(10000);
        }
    }

    @Test(timeout = 100)
    public void testSort() {
        inst.sort(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            Assert.assertTrue(arr[i] <= arr[i+1]);
        }
    }
}
