import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {
    //create private instance
    private JButton helloBtn;
    private JButton goodbyeBtn;
    private TextPanel textPanel;

    public Toolbar(){
        //creating object
        helloBtn = new JButton("Hello");
        goodbyeBtn = new JButton("Goodbye");

        helloBtn.addActionListener(this);
        goodbyeBtn.addActionListener(this);

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

    public void setTextPanel(TextPanel textPanel) {
        this.textPanel = textPanel; // get the details of textPanel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* System.out.println("A button"); use this to test the button
        System.out.println(e.getSource());
        System.out.println("\n"+ helloBtn);
         */
        JButton clicked = (JButton) e.getSource();
        if (clicked == helloBtn){
            textPanel.appendText("Hello\n");
        }else if (clicked == goodbyeBtn){
            textPanel.appendText("Goodbye\n");
        }

    }
}
