package at.technikum.tourplanner;

public class ThreadMaker {
    public static void runInBackground(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void multiRunInBackground(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
