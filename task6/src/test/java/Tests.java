import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests {
    private List<Integer> data;
    ForTests tests;
    static Random random;
    static int[] arr;

    @Before
    public void init() {
        arr = new int[10];
        tests = new ForTests();
        data = new ArrayList<>();
        random = new Random();
        int[] list = {1,4,1,1,4,1};

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10);
        }

        for (int i = 0; i < 10; i++) {
            data.add(random.nextInt(10));
        }
    }

    @Test
    public void sortTest() {
        List<Integer> sortData = tests.sort(data);
        System.out.println(tests.sort(data));
        for (int i = 0; i < sortData.size() - 1; i++) Assert.assertTrue(sortData.get(i) <= sortData.get(i) + 1);

    }

    @Test
    public void binarySearchTest() {
        int x = 6;
        int n = arr.length;
        int result = tests.binarySearch(arr, 0, n - 1, x);
        Assert.assertTrue(result!= -1);
    }

    @Test
    public void yearDividing4IsLeap() throws Exception
    {
        Assert.assertTrue(tests.isLeap(2008));
        Assert.assertTrue(tests.isLeap(2012));
        Assert.assertFalse(tests.isLeap(2011));
    }


}




