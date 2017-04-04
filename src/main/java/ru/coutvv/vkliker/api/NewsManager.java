package ru.coutvv.vkliker.api;

import java.util.concurrent.ExecutorService;

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
    ExecutorService likeLastPosts(int hours);

    /**
     * Постоянно лайкать новости по расписанию
     *
     * @param period в минутах
     * @return Executor чтобы убить процесс
     */
    ExecutorService scheduleLike(int period);

    /**
     * Мутная хрень
     *
     * @param minutes
     * @param timeout
     * @return Executor чтобы убить процесс
     */
    ExecutorService commentWatching(int minutes, long timeout);

}
