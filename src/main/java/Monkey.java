import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monkey implements Runnable {

    private int GET_ROPE_TIME = 1000;
    private int GET_TRAVEL_TIME = 4000;
    private int MAX_MONKEYS_ON_ROPE = 5;
    private String side;
    private int monkeyId;

    static Semaphore mutex = new Semaphore(1, true);
    static Semaphore eastQueue = new Semaphore(0, true);
    static Semaphore westQueue = new Semaphore(0, true);
    static String ropeSide = "East";
    static int westMonkeysWaiting = 0;
    static int eastMonkeysWaiting = 0;
    //static int monkeysOnRope = 0;
    static int westMonkeysOnRope = 0;
    static int eastMonkeysOnRope = 0;

    public Monkey(int monkeyId) {
        this.side = getInitialSide();
        this.monkeyId = monkeyId;
    }

    public void run() {
        System.out.println("Hello, it's monkey " + this.monkeyId +
                " and my side is " + this.side);

        if(this.side == "East") {
            travelWest();
        } else {
            travelEast();
        }
    }

    private void travelWest() {
        try {
            //Thread.sleep(getSpawnTime());
            System.out.println("Hello, it's monkey " + this.monkeyId +
                " and i want to cross");

            mutex.acquire();
            while(westMonkeysOnRope > 0) {
                eastMonkeysWaiting++;
                mutex.release();
                System.out.println("Me enfado y no respiro " + this.monkeyId);
                eastQueue.acquire();
                mutex.acquire();
            }

            if(eastMonkeysOnRope < MAX_MONKEYS_ON_ROPE) {
                Thread.sleep(GET_ROPE_TIME);
                eastMonkeysOnRope++;
                System.out.println("Hello, it's monkey " + this.monkeyId +
                        " and i'm crossing");
                mutex.release();
                mutex.acquire();
                eastMonkeysOnRope--;
                System.out.println("Hello, it's monkey " + this.monkeyId +
                        " and i've crossed");
            }
            mutex.release();

            //Thread.sleep(GET_TRAVEL_TIME);

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private void travelEast() {
        try {
            //Thread.sleep(getSpawnTime());
            System.out.println("Hello, it's monkey " + this.monkeyId +
                    " and i want to cross");

            mutex.acquire();
            while(eastMonkeysOnRope > 0) {
                westMonkeysWaiting++;
                mutex.release();
                System.out.println("Me enfado y no respiro " + this.monkeyId);
                westQueue.acquire();
                mutex.acquire();
            }

            if(westMonkeysOnRope < MAX_MONKEYS_ON_ROPE) {
                Thread.sleep(GET_ROPE_TIME);
                westMonkeysOnRope++;
                System.out.println("Hello, it's monkey " + this.monkeyId +
                        " and i'm crossing");
                mutex.release();
                mutex.acquire();
                westMonkeysOnRope--;
                System.out.println("Hello, it's monkey " + this.monkeyId +
                        " and i've crossed");
            }
            mutex.release();

            //Thread.sleep(GET_TRAVEL_TIME);

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
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
        int totalMonkeys = 5;
        for (int i = 0; i < totalMonkeys; i++) {
            new Thread(new Monkey(i)).start();
        }
    }
}
