package com.mathcat.mathcat.util;

public class DebugLogger {
    private DebugLogger() {}  // private constructor to prevent instantiation
    private static boolean enabled = false;

    public static void enable() {
        enabled = true;
    }

    public static void log(String tag, String message) {
        if (enabled) System.out.println("[" + tag + "] " + message);
    }
}
