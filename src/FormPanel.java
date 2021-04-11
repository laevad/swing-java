import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okayButton;
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

//        setLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // create object
        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        okayButton = new JButton("Okay");

        //set positioning .. grid
//        first row
        //weigh mean how  much space it take up relative to, to the other cells
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0; // row
        gridBagConstraints.gridy =0; // column

        /*
        * can be set to horizontal , vertical, none or both
        * and that tells your components, whether to take up all the space
        * int the cell , or not, and I probably don't want mine to so none
        * */
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0,0,0,5); // add padding for label
        add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0,0,0,0);  // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(nameField,gridBagConstraints);

//        second row
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(occupationLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(occupationField, gridBagConstraints);

//        third row
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2;

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(okayButton, gridBagConstraints);



    }
}
