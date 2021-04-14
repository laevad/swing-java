package gui;

import model.Message;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
* this demonstrates using a arbitrary component as a list box renderer
* (probably overkill in this case to use jpanel when jlabel could be used directly)
 */
public class MessageListRenderer implements ListCellRenderer {
    private JPanel panel;
    private JLabel label;

    private Color selectedColor;
    private Color normalColor;
    public MessageListRenderer() throws IOException, FontFormatException {
        panel = new JPanel();
        label = new JLabel();
        label.setFont(Utils.createFont("/fonts/CrimewaveBB.ttf").deriveFont(Font.BOLD, 22));

        selectedColor = new Color(210, 210, 255);
        normalColor = Color.WHITE;

        label.setIcon(Utils.createIcon("/images/Information24.gif"));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);

    }
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Message message = (Message) value;
        label.setText(message.getTitle());
        panel.setBackground(cellHasFocus ? selectedColor: normalColor);

        return panel;
    }
}
