package gui;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // use multi threading
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });

    }
}
