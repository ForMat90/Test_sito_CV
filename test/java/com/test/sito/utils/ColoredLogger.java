package com.test.sito.utils;

import java.util.logging.Logger;

public class ColoredLogger {

    // Definiamo costanti per i colori
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[33m";
    public static void info(Logger logger, String message) {
        logger.info(YELLOW + message + RESET);
    }

}
