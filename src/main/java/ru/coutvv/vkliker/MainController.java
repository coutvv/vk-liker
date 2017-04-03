package ru.coutvv.vkliker;

import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.util.LagUtil;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * @author coutvv
 */
public class MainController extends Observable {

    final static String FILENAME = "app.properties";
    final static String STOP_ERROR = "Can't stop thread";

    private ExecutorService likerThread;

    private State currentState = State.stop;

    private MainController() {}

    public static MainController getInstance() {
        return Holder.instance;
    }

    /**
     * Стартуем лайканье
     * @throws IOException
     */
    public void start() throws IOException {
        if(currentState == State.stop) {
            FeedManager fm;
            Factory fac = null;
            fac = new Factory(FILENAME);
            fm = fac.createFeedManager();
            likerThread = fm.scheduleLike(15);
            currentState = State.start;
        } else {
            throw new RuntimeException("Процесс запущен!!");
        }
        setChanged();
        this.notifyObservers(currentState);
    }

    /**
     * Останавливаем лайканье
     * @throws Exception
     */
    public void stop() throws Exception {
        if(likerThread != null && currentState == State.start) {
            likerThread.shutdown();
            LagUtil.lag(500);
            if(!likerThread.isShutdown()) {
                Logger.log(STOP_ERROR);
                throw new Exception(STOP_ERROR);
            }
            currentState = State.stop;
        }
        setChanged();
        this.notifyObservers(currentState);
    }


    public State getCurrentState() {
        return currentState;
    }

    private static class Holder {
        private static MainController instance = new MainController();
    }

    public enum State {
        start, stop;
    }


}
