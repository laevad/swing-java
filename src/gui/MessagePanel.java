package gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class MessagePanel extends JPanel {
    private JTree serverTree;
    private DefaultTreeCellRenderer treeCellRenderer;
    public MessagePanel(){
        treeCellRenderer = new DefaultTreeCellRenderer();

        treeCellRenderer.setLeafIcon(Utils.createIcon("/images/Server16.gif"));
        treeCellRenderer.setOpenIcon(Utils.createIcon("/images/WebComponent16.gif"));
        treeCellRenderer.setClosedIcon(Utils.createIcon("/images/WebComponentAdd16.gif"));
        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();

                Object userObject = defaultMutableTreeNode.getUserObject();
//                if (userObject instanceof ServerInfo){
//                    int id = ((ServerInfo) userObject).getId();
//                    System.out.println("Got user object with ID: "+ id);
//                }
                System.out.println(userObject);
            }
        });

        setLayout(new BorderLayout());

        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }
    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 3));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("gfhtk", 4));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("erfet", 5));
        DefaultMutableTreeNode server6 = new DefaultMutableTreeNode(new ServerInfo("fdgfdges", 6));

        branch2.add(server4);
        branch2.add(server5);
        branch2.add(server6);

        top.add(branch1);
        top.add(branch2);

        return top;
    }
}
class ServerInfo{
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ServerInfo(String name, int id){
        this.name = name;
        this.id = id;

    }

    @Override
    public String toString() {
        return name;
    }
}