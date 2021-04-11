import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    // component - private instance variables
    private TextPanel textPanel;
    private JButton button;
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
        button = new JButton("Click Me");


        //add listener // when click the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.appendText("Hello\n"); // call the method appendText
            }
        });

        // place the object
        add(textPanel, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }
}
