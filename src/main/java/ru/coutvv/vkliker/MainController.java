package ru.coutvv.vkliker;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.NewsManager;
import ru.coutvv.vkliker.api.WallManager;
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

    private NewsManager nm;
    private WallManager wallManager;

    private MainController() {
        try {
            Factory fac = new Factory(APP_PROPERTIES_FILENAME);
            nm = fac.createNewsManager();
            wallManager = fac.createWallManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainController getInstance() {
        return Holder.instance;
    }

    /**
     * Стартуем лайканье
     * @throws IOException
     */
    public void start() throws IOException {
        if(currentState == State.stop) {

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

    public void likeAllProfileNews(String id) throws ClientException, ApiException {
        System.out.println(id);
//        wallManager.getPosts(id);

        wallManager.likeWholeWall(id);
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
