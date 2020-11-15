import java.util.Random;

public class Monkey implements Runnable {

    private int GET_ROPE_TIME = 1000;
    private int GET_TRAVEL_TIME = 4000;
    private String side;
    private int monkeyId;

    public Monkey(String side, int monkeyId) {
        this.side = side;
        this.monkeyId = monkeyId;
    }

    public void run() {

    }

    private String getInitialSide() {
        final int MIN_LIMIT = 1;
        final int MAX_LIMIT = 10;
        final int SIDE_THRESHOLD = 5;

        Random ran = new Random();
        int initialSide = ran.nextInt((MAX_LIMIT - MIN_LIMIT) + 1) + MIN_LIMIT;

        return initialSide <= SIDE_THRESHOLD ? "East" : "West";
    }

    private int getSpawnTime() {
        final int MIN_TIME = 1;
        final int MAX_TIME = 8;

        Random ran = new Random();
        int time = ran.nextInt((MAX_TIME - MIN_TIME) + 1) + MIN_TIME;

        return time * 1000;
    }

    public static void main(String[] args) {
        
    }
}
