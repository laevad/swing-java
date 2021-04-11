import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okayButton;
    private FormListener formListener;
    private JList<String> ageList;
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

        // create object or component
        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        okayButton = new JButton("Okay");
        ageList = new JList<>();

        DefaultListModel<String> ageModel = new DefaultListModel<>();
        ageModel.addElement("Under 18");
        ageModel.addElement("18 - 65");
        ageModel.addElement("65 or over");
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(115, 80));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(0);

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
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(ageList, gridBagConstraints);

//        Fourth row
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2;

        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(okayButton, gridBagConstraints);

        okayButton.addActionListener(e -> {
            String name = nameField.getText();
            String occupation = occupationField.getText();
            String ageCat = ageList.getSelectedValue();
            System.out.println(ageCat);
            FormEvent formEvent = new FormEvent(this, name, occupation);

            if (formListener != null) {
                formListener.formEventOccurred(formEvent);
            }
        });



    }

    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }
}
