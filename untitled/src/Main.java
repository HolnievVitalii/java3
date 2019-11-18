import java.util.LinkedList;


public class TaskRunner implements Runnable {


    final Object mutex;   //монитор синхронизации
    String message; //сообщение которое поток будет добавлять в список

    //у вас могут быть другие решения и другие переменные
    volatile int cnt;
    volatile int inc;
    static volatile int iter = 0;
    //этот список будет проверяться на предмет синхронизации
    //данных
    //а именно: [1, 2, 3, 1, 2, 3, 1, 2, 3....]
    //в список будут писать из 3 потоков
    static volatile LinkedList<String> list = new LinkedList<>();

    public TaskRunner(Object mutex, String msg, int cnt) {
        this.mutex = mutex;
        message = msg; //каждый поток может писать в список только
        //его сообщение!!!
        this.cnt = cnt;
        inc = 0;
    }

    static class Object {
        String current;
    }

    @Override
    public void run() {
        mutex.current = "1";
        for (int i = 0; i < 10; i++) {
            synchronized (mutex) {
                while (mutex.current != message) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                list.add(message);

                switch (message) {
                    case "1":
                        mutex.current = "1";
                        break;
                    case "2":
                        mutex.current = "2";
                        break;
                    case "3":
                        mutex.current = "3";
                        break;
                }
                mutex.notifyAll();
            }
        }

        Thread t1 = new Thread(new TaskRunner(mutex, "1", 1));
        Thread t2 = new Thread(new TaskRunner(mutex, "2", 2));
        Thread t3 = new Thread(new TaskRunner(mutex, "3", 3));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}