import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel occupationLabel;
    private JTextField nameField;
    private JTextField occupationField;
    private JButton okayButton;
    private FormListener formListener;
    private JList<AgeCategory> ageList;
    private JComboBox<Object> empCombo;
    private JCheckBox citizenCheck;
    private JTextField taxField;
    private JLabel taxLabel;
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

        // create object or component
        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        okayButton = new JButton("Okay");
        ageList = new JList<>();
        empCombo = new JComboBox<>();
        citizenCheck = new JCheckBox();
        taxField = new JTextField(10);
        taxLabel = new JLabel("Tax ID: ");

        // combo box (next row)
        DefaultComboBoxModel<Object> empModel = new DefaultComboBoxModel<>();
        empModel.addElement("employed");
        empModel.addElement("self employed");
        empModel.addElement("unemployed");
        empCombo.setModel(empModel);

        // set up tax id
        taxLabel.setEnabled(false);
        taxField.setEnabled(false);

        citizenCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTicked = citizenCheck.isSelected();
                taxLabel.setEnabled(isTicked);
                taxField.setEnabled(isTicked);
            }
        });


        layoutComponents();

        okayButton.addActionListener(e -> {
            String name = nameField.getText();
            String occupation = occupationField.getText();
            AgeCategory ageCat = ageList.getSelectedValue();
            String empCat = (String) empCombo.getSelectedItem();
            String taxId = taxField.getText();
            boolean usCitizen = citizenCheck.isSelected();
            System.out.println(ageCat.getId());
            System.out.println(empCat);
            FormEvent formEvent = new FormEvent(this,
                    name,
                    occupation,
                    ageCat.getId(),
                    empCat,
                    taxId,
                    usCitizen);

            if (formListener != null) {
                formListener.formEventOccurred(formEvent);
            }
        });



    }

    public void layoutComponents(){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        // list box
        DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
        ageModel.addElement(new AgeCategory(0, "Under 18"));
        ageModel.addElement(new AgeCategory(1, "18 - 65"));
        ageModel.addElement(new AgeCategory(2, "65 or over"));
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(115, 80));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(0);

        //set positioning .. grid
//        first row
        //weigh mean how  much space it take up relative to, to the other cells


        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridy =0; // column
        gridBagConstraints.gridx = 0; // row


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

//        next row
        gridBagConstraints.gridy++;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        add(occupationLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        add(occupationField, gridBagConstraints);

        //        next row - list box
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Age: "), gridBagConstraints);


        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(ageList, gridBagConstraints);

        //        next row - list box
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Employment: "), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(empCombo, gridBagConstraints);

        //        next row - list box
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("US Citizen: "), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(citizenCheck, gridBagConstraints);
        //        next row - list box
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0,0,0,5);   // no padding for field
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        add(taxLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(taxField, gridBagConstraints);



//        next row
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 2;

        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(0,0,0,0);
        add(okayButton, gridBagConstraints);
    }

    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }
}
class AgeCategory{
    private String text;
    private int id;
    public  AgeCategory(int id, String text){
        this.id = id;
        this.text = text;
    }
    public String toString(){
        return text;
    }
    public int getId(){
        return id;
    }
}