import java.util.Random;

public class Monkey implements Runnable {

    public void run() {

    }

    private String getInitialSide() {
        final int MIN_LIMIT = 1;
        final int MAX_LIMIT = 10;
        final int SIDE_THRESHOLD = 5;

        Random ran = new Random();
        int side = ran.nextInt((MAX_LIMIT - MIN_LIMIT) + 1) + MIN_LIMIT;

        return side <= SIDE_THRESHOLD ? "East" : "West";
    }

    public static void main(String[] args) {
        
    }
}
