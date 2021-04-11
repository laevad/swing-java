import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // component - private instance variables
    private TextPanel textPanel;
    private Toolbar toolbar;
    private FormPanel formPanel;

    public MainFrame(){
        super("Hello World"); // title bar
        setVisible(true); // make it visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminate
        setSize(600,500); // to set size

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
        JMenuItem showFormItem = new JMenuItem("Person Form");
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        return menuBar;
    }
}
