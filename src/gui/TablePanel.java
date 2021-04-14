package gui;

import model.EmploymentCategory;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private PersonTableModel tableModel;
    private JPopupMenu popupMenu;
    private PersonTableListener personTableListener;

    public TablePanel(){
        tableModel = new PersonTableModel();
        table = new JTable(tableModel);
        popupMenu = new JPopupMenu();

        table.setDefaultRenderer(EmploymentCategory.class,new EmploymentCategoryRenderer());
        table.setRowHeight(25);

        JMenuItem removeItem = new JMenuItem("Delete Row");
        popupMenu.add(removeItem);

        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                if(e.getButton() == MouseEvent.BUTTON3){
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(personTableListener != null){
                    personTableListener.rowDeleted(row);
                    tableModel.fireTableDataChanged();
                }
            }
        });
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    public void setData(List<Person> db){
        tableModel.setData(db);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void setPersonTableListener(PersonTableListener personTableListener) {
        this.personTableListener = personTableListener ;
    }
}
