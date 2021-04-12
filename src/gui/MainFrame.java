package gui;

import controller.Controller;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {
    // component - private instance variables
    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs;
    public MainFrame(){
        super("Hello World"); // title bar
        setVisible(true); // make it visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate
        setSize(1280,500); // to set size
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
        fileChooser = new JFileChooser();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);
        prefs = Preferences.userRoot().node("db");



        setJMenuBar(createMenuBar());

        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        tablePanel.setData(controller.getPeople());
        tablePanel.setPersonTableListener(new PersonTableListener() {
                                              public void rowDeleted(int row) {
                                                  controller.removePerson(row);
                                                  System.out.println(row);
                                              }
                                          });

        // pass the construct and implement some stuff
        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                System.out.println("Save");
                connect();
                try {
                    controller.save();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.",
                            "DATABASE SAVE PROBLEM", JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            public void refreshEventOccured() {
                connect();
                System.out.println("Refresh");
                try {
                    controller.load();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load to database.",
                            "DATABASE CONNECTION PROBLEM", JOptionPane.ERROR_MESSAGE);
                }
                tablePanel.refresh();
            }
        });

        //pref
        prefsDialog.setPresListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int port) {
                // install somewhere in os
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);
            }
        });
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        Integer port = prefs.getInt("port", 3306);
        prefsDialog.setDefaults(user, password, port);

        formPanel.setFormListener(e -> {
            controller.addPerson(e);
            tablePanel.refresh();
        });

        // place the object
        add(toolbar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.",
                    "DATABASE CONNECTION PROBLEM", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

        JMenuItem prefsItem = new JMenuItem("Preferences...");
        windowMenu.add(prefsItem);
        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });

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
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Could not load data from file", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });
        exportDataITem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Could not save data to file", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
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
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        importDataITem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        return menuBar;
    }
}
