import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(){
        //preferred size return object call Dimension
        Dimension dimension = getPreferredSize();
//        System.out.println(dimension);
        dimension.width = 250;
        setPreferredSize(dimension);

        // border with title
        // the problem with this is to close to other
        // in order to solve that add second border to create some space
//        setBorder(BorderFactory.createTitledBorder("Add Person"));

        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5 , 5);
        // combine the border together
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
}
