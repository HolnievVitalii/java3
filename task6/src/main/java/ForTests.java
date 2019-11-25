import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ForTests {

    //протестируйте функцию
    public List<Integer> sort(List<Integer> data) {
        return data.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public int binarySearch(int[] data, int key, int l, int r) {
        if (r >= 1) {
            int mid = r + (l - r) / 2;
            if (data[mid] == key) return mid;
            // чего то не хватает, протестируйте и найдите баг в функции
            if (data[mid] > key) return binarySearch(data, key, mid - l, mid);
            return binarySearch(data, key, mid + 1, r);
        }
        return -1;
    }

    public boolean isLeap(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то метод вернет false
     *
     * @param array
     * @return
     */
    public boolean check(int[] array) {
        return  (ArrayUtils.contains(array,1)
                && ArrayUtils.contains(array,4)

        );
    }
}
