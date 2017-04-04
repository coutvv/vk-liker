package ru.coutvv.vkliker.util;

import java.time.format.DateTimeFormatter;

/**
 * @author coutvv
 */
public interface Consts {
    String APP_PROPERTIES_FILENAME = "app.properties";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
}
