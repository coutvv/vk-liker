package ru.coutvv.vkliker.api.repository;

import com.google.gson.JsonElement;

/**
 * @author coutvv
 */
public interface CommentRepository {

    /**
     * Получение комментариев
     *
     * @param ownerId -- ид владельца записи
     * @param postId -- ид записи
     * @param count -- количество записей
     * @param offset -- начиная с какого комментария получить записи
     * @return
     */
    JsonElement get(long ownerId, long postId, int count, int offset);

    /**
     * Получение всех постов под записью
     *
     * @param ownerId -- ид владельца записи
     * @param postId -- ид записи
     * @return
     */
    JsonElement getAll(long ownerId, long postId);

}
