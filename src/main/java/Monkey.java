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

    private int getSpawnTime() {
        final int MIN_TIME = 1;
        final int MAX_TIME = 8;

        Random ran = new Random();
        int time = ran.nextInt((MAX_TIME - MIN_TIME) + 1) + MIN_TIME;

        return time;
    }

    public static void main(String[] args) {
        
    }
}
