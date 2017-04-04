package ru.coutvv.vkliker;

import ru.coutvv.vkliker.api.NewsManager;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.util.LagUtil;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.Future;

import static ru.coutvv.vkliker.util.Consts.APP_PROPERTIES_FILENAME;

/**
 * @author coutvv
 */
public class MainController extends Observable {

    final static String STOP_ERROR = "Can't stop thread";

    private Future likerThread;

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
            Factory fac = new Factory(APP_PROPERTIES_FILENAME);
            NewsManager nm = fac.createNewsManager();

            likerThread = nm.scheduleLike(15);
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
            likerThread.cancel(false);
            LagUtil.lag(500);
            if(!likerThread.isCancelled()) {
                Logger.log(STOP_ERROR);
                throw new Exception(STOP_ERROR);
            }
            currentState = State.stop;
        }
        setChanged();
        this.notifyObservers(currentState);
    }

    public void likeAllProfileNews(String id) {
        System.out.println(id);
        System.out.println(Thread.activeCount());
    }

    public State getCurrentState() {
        return currentState;
    }
    public enum State {
        start, stop;
    }

    private static class Holder {
        private static MainController instance = new MainController();
    }



}
