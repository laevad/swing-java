import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    // component - private instance variables
    private TextPanel textPanel;
    private Toolbar toolbar;
    public MainFrame(){
        super("Hello World"); // title bar
        setVisible(true); // make it visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate
        setSize(600,500); // to set size

        // Border Layout
        /*
          Every content pane is initialized to use a BorderLayout.
          (As Using Top-Level Containers explains,
          the content pane is the main container in all
          frames, applets, and dialogs.)
          A BorderLayout places components in up to five areas:
          top, bottom, left, right, and center.
          All extra space is placed in the center area.
          Tool bars that are created using
          JToolBar must be created within a
          BorderLayout container,
          https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#border
         */
        setLayout(new BorderLayout());

        // create new object
        textPanel = new TextPanel();
        toolbar = new Toolbar();

        // pass the construct and implement some stuff
        toolbar.setTextPanel(textPanel);

        // place the object
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
    }
}
