import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(){
        //preferred size return object call Dimension
        Dimension dimension = getPreferredSize();
//        System.out.println(dimension);
        dimension.width = 250;
        setPreferredSize(dimension);
    }
}
