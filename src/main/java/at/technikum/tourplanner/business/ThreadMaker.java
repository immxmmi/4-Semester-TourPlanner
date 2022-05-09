package at.technikum.tourplanner.business;

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

    public static void ThreadWait2000(Runnable runnable){
        Thread thread = new Thread(runnable);
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }



}
