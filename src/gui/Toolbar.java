package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {
    //create private instance
    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener textListener;

    public Toolbar(){
        //creating object
        saveButton = new JButton("Save");
        refreshButton = new JButton("Refresh");

        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);

        /*
        FlowLayout - FlowLayout is the default layout manager for every JPanel.
        It simply lays out components in a single row,
        starting a new row if its container is not sufficiently wide.
        (in short left to right)
         */
        //to get them to the left just add parameter to constructor
        setLayout(new FlowLayout(FlowLayout.LEFT));
        Border innerBorder = BorderFactory.createEtchedBorder();
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 5 , 0);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        // add object to layout
        add(saveButton);
        add(refreshButton);
    }

    public void setToolbarListener(ToolbarListener listener){
        this.textListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* System.out.println("A button"); use this to test the button
        System.out.println(e.getSource());
        System.out.println("\n"+ helloBtn);
         */
        JButton clicked = (JButton) e.getSource();
        if (clicked == saveButton){
            if(textListener != null){
                textListener.saveEventOccured();
            }
        }else if (clicked == refreshButton){
            if(textListener != null){
                textListener.refreshEventOccured();
            }
        }

    }
}
