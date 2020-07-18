package org.javaworld.cmsbackend.util;
public class OsDetector {

    final public static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("windows");
    }

}