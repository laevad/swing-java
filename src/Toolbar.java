import javax.swing.*;
import java.awt.*;

public class Toolbar extends JPanel {
    //create private instance
    private JButton helloBtn;
    private JButton goodbyeBtn;

    public Toolbar(){
        //creating object
        helloBtn = new JButton("Hello");
        goodbyeBtn = new JButton("Goodbye");

        /*
        FlowLayout - FlowLayout is the default layout manager for every JPanel.
        It simply lays out components in a single row,
        starting a new row if its container is not sufficiently wide.
        (in short left to right)
         */
        //to get them to the left just add parameter to constructor
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // add object to layout
        add(helloBtn);
        add(goodbyeBtn);
    }
}
