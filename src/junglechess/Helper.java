package junglechess;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {

    public static byte[] loadDataFromFile(File file) {
        try {
            return Files.readAllBytes(Paths.get(file.toURI()));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] loadDataFromResource(String resource) {
        URI uri = null;
        try {
            URL url = GameIO.class.getClassLoader().getResource(resource);
            if (url != null) {
                uri = url.toURI();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (uri == null) {
            return null;
        }
        try {
            return Files.readAllBytes(Paths.get(uri));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
