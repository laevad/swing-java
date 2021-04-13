package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Toolbar extends JToolBar implements ActionListener {
    //create private instance
    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener textListener;

    public Toolbar(){
        //creating object
        saveButton = new JButton();
        saveButton.setIcon(Utils.createIcon("/images/Save16.gif"));
        saveButton.setToolTipText("Save");
        refreshButton = new JButton();
        refreshButton.setIcon(Utils.createIcon("/images/Refresh16.gif"));
        refreshButton.setToolTipText("Refresh");

        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);

        /*
        FlowLayout - FlowLayout is the default layout manager for every JPanel.
        It simply lays out components in a single row,
        starting a new row if its container is not sufficiently wide.
        (in short left to right)
         */
        //to get them to the left just add parameter to constructor
//        setLayout(new FlowLayout(FlowLayout.LEFT));
//        Border innerBorder = BorderFactory.createEtchedBorder();
//        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 5 , 0);
//        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        //get rid of the border if you want the toolbar draggable.
        setBorder(BorderFactory.createEtchedBorder());

//        setFloatable(false);

        // add object to layout
        add(saveButton);
//        addSeparator();
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
