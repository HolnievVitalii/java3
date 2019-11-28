package annotation;

public class Utility {

    private int a, b;

    public Utility(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    int sum(){
        return a + b;
    }

    int mul(){
        return a * b;
    }

    int sub(){
        return a - b;
    }

    int div(){
        return a / b;
    }

    int mod(){
        return a % b;
    }
}
