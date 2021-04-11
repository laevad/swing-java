package gui;

import javax.swing.*;
import javax.swing.border.Border;
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
    private PrefsListener prefsListener;
    public PrefsDialog(JFrame parentFrame){
        super(parentFrame, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        userfield = new JTextField(10);
        passwordField = new JPasswordField(10);

        passwordField.setEchoChar('*');


        spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);

        layoutControls();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (int) portSpinner.getValue();

                String user = userfield.getText();
                char[] password =  passwordField.getPassword();
//                System.out.println(user + " : "+ new String(password));
                if (prefsListener != null){
                    prefsListener.preferencesSet(user, new String(password), value);
                }
                setVisible(false);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(250, 210);
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }

    private void layoutControls() {
        JPanel controlsPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        int space = 10;
        Border spaceBordxer = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBordxer, titleBorder));

//        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridy = 0;

        Insets rightPadding = new Insets(0,0,0, 10);
        Insets noPadding = new Insets(0,0,0, 0);

        // row
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = rightPadding;
        controlsPanel.add(new JLabel("User: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = noPadding;
        controlsPanel.add(userfield, gridBagConstraints);

        // row
        gridBagConstraints.gridy++;

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.001;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = noPadding;
        controlsPanel.add(passwordField, gridBagConstraints);
        // row
        gridBagConstraints.gridy++;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;

        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gridBagConstraints);

        gridBagConstraints.gridx++;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = noPadding;
        controlsPanel.add(portSpinner, gridBagConstraints);

        // button row
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        // add sub panels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setPresListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }
    public void setDefaults(String user, String password, int port){
        userfield.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }
}
