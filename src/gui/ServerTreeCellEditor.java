package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
    private ServerTreeCellRenderer serverTreeCellRenderer;
    private JCheckBox checkBox;
    private ServerInfo serverInfo;
    public ServerTreeCellEditor(){
        serverTreeCellRenderer = new ServerTreeCellRenderer();
    }


    @Override
    public Object getCellEditorValue() {
        serverInfo.setChecked(checkBox.isSelected());
        return serverInfo;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if(!(e instanceof MouseEvent)) return false;
        MouseEvent mouseEvent = (MouseEvent) e;
        JTree tree = (JTree)e.getSource();
        TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
        if(path == null) return false;

        Object lastComponent = path.getLastPathComponent();
        if (lastComponent == null) return false;

        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) lastComponent;
        return defaultMutableTreeNode.isLeaf();
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        Component component = serverTreeCellRenderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
        if (leaf){
            DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode)value;
            serverInfo = (ServerInfo) defaultMutableTreeNode.getUserObject();
            checkBox = (JCheckBox) component;
            ItemListener itemListener = new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fireEditingStopped();
                    checkBox.removeItemListener(this);
                }
            };
            checkBox.addItemListener(itemListener);
        }
        return component;
    }
}
