package ru.coutvv.vkliker.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author coutvv
 */
public interface NewsManager {

    /**
     * Залайкать все новости в ленте за последние hours часов
     *
     * @param hours
     * @return Executor чтобы убить процесс
     */
    Future likeLastPosts(int hours);

    /**
     * Постоянно лайкать новости по расписанию
     *
     * @param period в минутах
     * @return Executor чтобы убить процесс
     */
    Future scheduleLike(int period);

    /**
     * Мутная хрень
     *
     * @param minutes
     * @param timeout
     * @return Executor чтобы убить процесс
     */
    Future commentWatching(int minutes, long timeout);

}
