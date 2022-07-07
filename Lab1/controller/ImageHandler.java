package controller;

import core.Launcher;

public class ImageHandler {
    public static String getImage(String URL) {
        if (Launcher.class.getResource(URL) == null) {

            Logger.log(String.format("Image not found, Requested: %s",URL), true);
            return Launcher.class.getResource("/assets/unknown.png").toString();
        }
        return Launcher.class.getResource(URL).toString();

    }
}
