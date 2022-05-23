package at.technikum.tourplanner.business.handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadMaker {

    //LOGGER
    private final static Logger log = LogManager.getLogger(ThreadMaker.class.getName());
    public static void multiRunInBackground(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void runInBackground(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    public static void ThreadWait2000(Runnable runnable) {
        Thread thread = new Thread(runnable);
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e);
        }
        thread.start();
    }

}
