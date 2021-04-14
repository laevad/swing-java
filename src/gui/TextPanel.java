package gui;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private JTextArea textArea;

    public TextPanel(){
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        setLayout(new BorderLayout());

        // make it center and add scrollPane
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
    //method for append text
    public void appendText(String text){
        textArea.append(text);
    }

    public void setText(String content) {
        textArea.setText(content);
    }
}
