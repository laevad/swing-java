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
    public PrefsDialog(JFrame parentFrame){
        super(parentFrame, "Preferences", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        spinnerNumberModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerNumberModel);

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridy = 0;
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
                System.out.println(value);
                setVisible(false);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(250, 150);
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }
}
