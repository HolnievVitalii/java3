import java.io.*;
import java.security.SecureRandom;
import java.util.*;

public class FileUtility {
    RandomAccessFile file;
    String pathToFile = "C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\file.txt";
    String getPathToFile2 = "C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\addedTestFile.txt";
    String path;

    public FileUtility(String path) {
        this.path = path;
    }

    /*
     * Структура файла ввода: в первой строке одно целое число - N
     * далее следует N целых чисел через пробел
     * Метод должен отсортировать элементы с четным значением,
     * а нечетные оставить на своих местах и вывести результат через пробел в файл вывода
     * Пример:
     * 5
     * 5 4 2 1 3
     * 5 2 4 1 3
     */
    public void sortEvenElements(File in, File out) throws IOException {
        List<Integer> list = new ArrayList<>();
        in = new File("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\array.txt");
        out = new File("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\arrayout.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
        while (bufferedReader.ready()) {
            int i = Integer.parseInt(bufferedReader.readLine());
            if (i % 2 == 0) {
                list.add(i);
                bufferedReader.close();
                Collections.sort(list);

            } else {
                list.add(i);
            }
        }
        try (final FileWriter writer = new FileWriter(out)) {
            for (int i = 0; i < list.size(); ++i) {
                final String s = Integer.toString(list.get(i));
                writer.write(s + " ");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
     * Генератор паролей, пароль должен отвечать требованиям:
     * длина не менее 6 и не более 12, включает минимум по одному символу
     * из наборов: a-z, A-Z, 0-9, {*,!,%}
     * все пароли должны быть разными
     * На вход метод получает файл с логинами пользователей
     * Метод должен записать в файл вывода записи с логинами и паролями
     * для каждого пользователя
     */

    public static String generatePassword(int length) {

        if (length < 4) {
            length = 6;
        }

        final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] numbers = "0123456789".toCharArray();
        final char[] symbols = "*!%".toCharArray();
        final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789*!%".toCharArray();

        Random random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length - 4; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }

        password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
        password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
        password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
        password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);
        return password.toString();
    }


    public void passwordGen(File in, File out) throws IOException {
        in = new File("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\login.txt");
        out = new File("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\pass.txt");
        try {
            FileInputStream fin = new FileInputStream(in);
            int i = 0;
            String s = "";

            while ((i = fin.read()) != 1) {
                s = s + (char) i;

            }
            FileOutputStream fout = new FileOutputStream(out);
            byte[] b = s.getBytes();
            fout.write(b);
            for (int k = 0; k < 8; k++) {
                fout.write(Integer.parseInt(generatePassword(12).getBytes().toString()));
                break;
            }
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    /*
     *  Метод должен дописать в переданный файл все
     * записи из списка по одной записи в строке
     * */
    public void appender(File file, List<String> records) throws IOException {
        file = new File("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\append.txt");
        FileWriter writer = new FileWriter(file);
        for (String s : records) writer.write(s + " " + System.getProperty("line.separator"));
    }

    /*
     * Метод возвращает список из N последних строк файла
     * строки можно дополнять пробелами до длины 80
     * тогда количество символов в файле будет предсказуемо
     * 10 строк это ровно 800 символов
     * Изучите класс RandomAccessFile для эффективного решения данной
     * задачи
     * Альтернативное решение: использование очереди или стека
     * */

    /**
     * читаем файл начиная с определнного символа
     *
     * @param numberSymbol
     * @return
     */
    public String readFrom(int numberSymbol) throws IOException {
        file = new RandomAccessFile(pathToFile, "r");
        String res = "";
        file.seek(numberSymbol);
        int b = file.read();
        while (b != -1) {
            res += (char) b;

            b = file.read();
        }
        file.close();
        return res;
    }

    public void write(String string) throws IOException {
        file = new RandomAccessFile(getPathToFile2, "rw");
        file.write(string.getBytes());
        file.close();
    }


    public List<String> getNString(String pathToFile, int n) throws IOException {
        List<String> list = new ArrayList<>();
        FileUtility worker = new FileUtility("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\file.txt");
        FileUtility worker2 = new FileUtility("C:\\Users\\holni\\Desktop\\lesson3\\task3\\src\\main\\resources\\addedTestFile.txt");
        list.add("Oleg message 900");
        String s = worker.readFrom(800);
        worker2.write(s);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(getPathToFile2));
        String sCurrentLine;

        while ((sCurrentLine = bufferedReader.readLine())!= null) {
            list.add(sCurrentLine);
        }
        return list;
    }
}
