import java.util.Random;
import java.util.concurrent.Semaphore;

enum Direction {
    WEST,
    EAST;

    public static Direction getRandomDirection() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static Direction getOpposite(Direction dir) {
        switch(dir) {
            case WEST: return EAST;
            case EAST: return WEST;
            default: throw new IllegalStateException();
        }
    }
}

public class Monkey implements Runnable {

    private final int ROPE_TIME = 1000;
    private final int TRAVEL_TIME = 4000;
    final int MIN_ARRIVAL_TIME = 1000;
    final int MAX_ARRIVAL_TIME = 8000;

    private Direction side;
    private int monkeyId;

    static Semaphore mutex = new Semaphore(1, true);
    static Semaphore queue[] = {new Semaphore(0, true), new Semaphore(0, true)};
    static int waiting[] = {0, 0};
    static int onRope[] = {0, 0};

    public Monkey(int monkeyId) {
        this.side = Direction.getRandomDirection();
        this.monkeyId = monkeyId;
    }

    public void run() {
        try {
            int sleepTime = getArrivalTime();
            System.out.println("Monkey: " + this.monkeyId +
                    " from " + this.side +
                    " ArrivalWait: " + sleepTime);
            Thread.sleep(sleepTime);
            travel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void travel() {
        try {
            int direction = this.side.ordinal();
            int oppositeDirection = Direction.getOpposite(this.side).ordinal();
            System.out.println("Monkey: " + this.monkeyId +
                    " from " + this.side +
                    " wants to cross");
            mutex.acquire();
            while(onRope[oppositeDirection] > 0) {
                waiting[direction]++;
                System.out.println("Monkey: " + this.monkeyId +
                        " from " + this.side +
                        " is waiting");
                mutex.release();
                queue[direction].acquire();
                mutex.acquire();
            }
            Thread.sleep(ROPE_TIME);
            onRope[direction]++;
            System.out.println("Monkey: " + this.monkeyId +
                    " from " + this.side +
                    " is crossing");
            mutex.release();
            Thread.sleep(TRAVEL_TIME);
            mutex.acquire();
            onRope[direction]--;
            System.out.println("Monkey: " + this.monkeyId +
                    " from " + this.side +
                    " has crossed");
            if(onRope[direction] == 0) {
                while(waiting[oppositeDirection]-- > 0) {
                    queue[oppositeDirection].release();
                }
            }
            mutex.release();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private int getArrivalTime() {
        Random ran = new Random();
        int time = ran.nextInt((MAX_ARRIVAL_TIME - MIN_ARRIVAL_TIME) + 1) + MIN_ARRIVAL_TIME;

        return time;
    }

    public static void main(String[] args) {
        int totalMonkeys = 10;
        for (int i = 0; i < totalMonkeys; i++) {
            new Thread(new Monkey(i)).start();
        }
    }
}
