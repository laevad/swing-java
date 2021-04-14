package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressDialog extends JDialog {
    private JButton cancelButton;
    private JProgressBar progressBar;
    private ProgressDialogListener progressDialogListener;
    public ProgressDialog(Window parent, String title){
        super(parent, title, ModalityType.APPLICATION_MODAL);

        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMaximum(10);
        progressBar.setString("Retrieving messages..");

//        progressBar.setIndeterminate(true);
        setLayout(new FlowLayout());

        Dimension size = cancelButton.getPreferredSize();
        size.width = 400;
        progressBar.setPreferredSize(size);

        add(progressBar);
        add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(progressDialogListener != null){
                    progressDialogListener.progressDialogCancelled();
                }
            }
        });
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(progressDialogListener != null){
                    progressDialogListener.progressDialogCancelled();
                }
            }
        });
        pack();

        setLocationRelativeTo(parent);

    }
    public void setListener(ProgressDialogListener progressDialogListener){
        this.progressDialogListener = progressDialogListener;
    }
    public void setMaximum(int count){
        progressBar.setMaximum(count);
    }
    public void setValue(int value){
        int progress = 100 * value/progressBar.getMaximum();
        progressBar.setString(String.format("%d%% complete", progress));
        progressBar.setValue(value);
    }

    @Override
    public void setVisible(final boolean visible) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (visible==false){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    progressBar.setValue(0);
                }
                ProgressDialog.super.setVisible(visible);
            }
        });
    }
}
