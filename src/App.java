import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // use multi threading
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Hello World"); // title bar
                frame.setVisible(true); // make it visible
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate
                frame.setSize(600,500); // to set size
            }
        });

    }
}
