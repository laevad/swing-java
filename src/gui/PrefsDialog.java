package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefsDialog extends JDialog {
    private JButton okButton;
    private JButton cancelButton;
    private  JSpinner portSpinner;
    private SpinnerNumberModel spinnerNumberModel;
    private JTextField userfield;
    private JPasswordField passwordField;
    public PrefsDialog(JFrame parentFrame){
        super(parentFrame, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        userfield = new JTextField(10);
        passwordField = new JPasswordField(10);

        passwordField.setEchoChar('*');


        spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridy = 0;

        // row
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        add(new JLabel("User: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        add(userfield, gridBagConstraints);

        // row
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.001;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        add(new JLabel("Password: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        add(passwordField, gridBagConstraints);
        // row
        gridBagConstraints.gridy++;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        add(new JLabel("Port: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        add(portSpinner, gridBagConstraints);

        // next row
        gridBagConstraints.gridy++;


        gridBagConstraints.gridx = 0;
        add(okButton, gridBagConstraints);

        gridBagConstraints.gridx++;
        add(cancelButton, gridBagConstraints);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (int) portSpinner.getValue();

                String user = userfield.getText();
                char[] password =  passwordField.getPassword();
                System.out.println(user + " : "+ new String(password));
                setVisible(false);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(250, 200);
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }
}
