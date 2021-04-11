package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    // component - private instance variables
    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;

    public MainFrame(){
        super("Hello World"); // title bar
        setVisible(true); // make it visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate
        setSize(600,500); // to set size
        setMinimumSize(new Dimension(500, 400));

        // Border Layout
        /*
          Every content pane is initialized to use a BorderLayout.
          (As Using Top-Level Containers explains,
          the content pane is the main container in all
          frames, applets, and dialogs.)
          A BorderLayout places components in up to five areas:
          top, bottom, left, right, and center.
          All extra space is placed in the center area.
          Tool bars that are created using
          JToolBar must be created within a
          BorderLayout container,
          https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#border
         */
        setLayout(new BorderLayout());

        // create new object
        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        setJMenuBar(createMenuBar());
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());
        controller = new Controller();

        // pass the construct and implement some stuff
        toolbar.setTextListener(text -> textPanel.appendText(text));

        formPanel.setFormListener(e -> {
            String name = e.getName();
            String occupation = e.getOccupation();
            int ageCategory = e.getAgeCategory();
            String empCat = e.getEmpCat();
            String taxId = e.getTaxId();
            String gender = e.getGender();
            boolean usCitizen = e.isUsCitizen();

            textPanel.appendText(name+" : "+
                    occupation+" : "+
                    ageCategory+" : "+
                    empCat+" : "+
                    taxId+" : "+" : "+
                    usCitizen+" : "+
                    gender+"\n");
            controller.addPerson(e);
        });

        // place the object
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);
    }
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenuBar = new JMenu("File");
        JMenu windowMenu = new JMenu("Window");
        menuBar.add(fileMenuBar);
        menuBar.add(windowMenu);

        JMenuItem exportDataITem = new JMenuItem("Export Data...");
        JMenuItem importDataITem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenuBar.add(exportDataITem);
        fileMenuBar.add(importDataITem);
        fileMenuBar.addSeparator();
        fileMenuBar.add(exitItem);

        JMenu showMenu = new JMenu("Show");
        final JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem =(JCheckBoxMenuItem) e.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });
        importDataITem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });
        exportDataITem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Do you really want to exit",
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION);
                if (action == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
            }
        });

        //Mnemonics
        fileMenuBar.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        //Accelerator
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        return menuBar;
    }
}
