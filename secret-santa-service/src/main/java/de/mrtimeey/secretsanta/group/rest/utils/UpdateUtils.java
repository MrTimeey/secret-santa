package de.mrtimeey.secretsanta.group.rest.utils;

import java.util.function.Consumer;

public class UpdateUtils {

    public static <T> void processIfNotNull(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

}
