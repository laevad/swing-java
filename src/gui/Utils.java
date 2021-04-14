package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Utils {
    public static String getFileExtension(String name){
        int pointIndex = name.lastIndexOf(".");

        if(pointIndex == -1){
            return null;
        }

        if (pointIndex == name.length()-1){
            return null;
        }
        return name.substring(pointIndex+1, name.length());
    }
    public static ImageIcon createIcon(String path){
        URL url = System.class.getResource(path);
        if (url == null){
            System.err.println("Unable to load "+path);
        }
        ImageIcon icon = new ImageIcon(url);
        return icon;
    }
    public static Font createFont(String path) throws IOException, FontFormatException {
        URL url = System.class.getResource(path);
        if (url == null){
            System.err.println("Unable to Font "+path);
        }
        Font font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());

        return font;
    }
}
