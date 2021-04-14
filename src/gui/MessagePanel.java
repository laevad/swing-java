package gui;

import controller.MessageServer;
import model.Message;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessagePanel extends JPanel {
    private JTree serverTree;
    private ServerTreeCellRenderer treeCellRenderer;
    private ServerTreeCellEditor serverTreeCellEditor;
    private Set<Integer> selectedServers;
    private MessageServer messageServer;

    public MessagePanel(){

        messageServer = new MessageServer();
        selectedServers = new TreeSet<Integer>();
        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);

        treeCellRenderer = new ServerTreeCellRenderer();

        serverTree = new JTree(createTree());
        serverTreeCellEditor = new ServerTreeCellEditor();
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setCellEditor((TreeCellEditor) serverTreeCellEditor);
        serverTree.setEditable(true);

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        serverTreeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo serverInfo = (ServerInfo) serverTreeCellEditor.getCellEditorValue();
                System.out.println(serverInfo+": "+serverInfo.getId()+": "+serverInfo.isChecked());
                int serverId = serverInfo.getId();
                if (serverInfo.isChecked()){
                    selectedServers.add(serverId);
                }else {
                    selectedServers.remove(serverId);
                }
                messageServer.setSelectedServers(selectedServers);
                retrieveMessages();
               
            }

            private void retrieveMessages() {
                SwingWorker<List<Message>, Integer> worker = new SwingWorker<List<Message>, Integer>(){
                    @Override
                    protected List<Message> doInBackground() throws Exception {
                        List<Message> retrieveMessages = new ArrayList<Message>();
                        int count = 0;
                        for (Message message: messageServer){
                            System.out.println(message.getTitle());
                            retrieveMessages.add(message);
                            count++;
                            publish(count);
                        }
                        return retrieveMessages;
                    }

                    @Override
                    protected void process(List<Integer> counts) {
                        int retrieved = counts.get(counts.size() - 1);
                        System.out.println("Got "+ retrieved +" messages.");
                    }

                    @Override
                    protected void done() {
                        try {
                            List<Message> retrieveMessages = get();
                            System.out.println("Retrieve "+ retrieveMessages.size()+" messages.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                };
                worker.execute();
                System.out.println("Messages waiting: "+messageServer.getMessageCount());

            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });

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
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, selectedServers.contains(0)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1, selectedServers.contains(1)));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2, selectedServers.contains(2)));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("gfhtk", 3, selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("erfet", 4, selectedServers.contains(4)));

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;
    }
}
class ServerInfo{
    private String name;
    private int id;
    private  boolean checked;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ServerInfo(String name, int id, boolean checked){
        this.name = name;
        this.id = id;
        this.checked = checked;

    }

    @Override
    public String toString() {
        return name;
    }
}