package gui;

import controller.MessageServer;
import model.Message;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class MessagePanel extends JPanel implements ProgressDialogListener{
    private JTree serverTree;
    private ServerTreeCellRenderer treeCellRenderer;
    private ServerTreeCellEditor serverTreeCellEditor;
    private ProgressDialog progressDialog;
    private Set<Integer> selectedServers;
    private MessageServer messageServer;
    private SwingWorker<List<Message>, Integer> worker;
    private TextPanel textPanel;
    private JList messageList;
    private JSplitPane upperPane;
    private JSplitPane lowerPane;
    private  DefaultListModel defaultListModel;

    public MessagePanel(JFrame parent){

        progressDialog = new ProgressDialog(parent, "Messages Downloading...");
        messageServer = new MessageServer();
        progressDialog.setListener(this);
        selectedServers = new TreeSet<Integer>();
        defaultListModel = new DefaultListModel();
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
        messageServer.setSelectedServers(selectedServers);

        serverTreeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo serverInfo = (ServerInfo) serverTreeCellEditor.getCellEditorValue();
//                System.out.println(serverInfo+": "+serverInfo.getId()+": "+serverInfo.isChecked());
                int serverId = serverInfo.getId();
                if (serverInfo.isChecked()){
                    selectedServers.add(serverId);
                }else {
                    selectedServers.remove(serverId);
                }
                messageServer.setSelectedServers(selectedServers);
                retrieveMessages();
               
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
        textPanel = new TextPanel();
        messageList = new JList(defaultListModel);
        messageList.setCellRenderer(new MessageListRenderer());
        messageList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Message message = (Message) messageList.getSelectedValue();
                textPanel.setText(message.getContent());
            }
        });
        lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
        upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);

        textPanel.setMinimumSize(new Dimension(10, 100));
        messageList.setMinimumSize(new Dimension(10, 100));

        upperPane.setResizeWeight(0.5);
        lowerPane.setResizeWeight(0.5);

        add(upperPane, BorderLayout.CENTER);

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

    @Override
    public void progressDialogCancelled() {
        if (worker != null){
            worker.cancel(true);
        }
    }

    public void refresh(){
        retrieveMessages();
    }

    private void retrieveMessages() {
        progressDialog.setVisible(true);


        worker = new SwingWorker<List<Message>, Integer>(){
            @Override
            protected List<Message> doInBackground() throws Exception {
                List<Message> retrieveMessages = new ArrayList<Message>();
                int count = 0;
                for (Message message: messageServer){
                    if (isCancelled()) break;
                    retrieveMessages.add(message);
                    count++;
                    publish(count);
                }
                return retrieveMessages;
            }

            @Override
            protected void process(List<Integer> counts) {
                int retrieved = counts.get(counts.size() - 1);
//                        System.out.println("Got "+ retrieved +" messages.");
                progressDialog.setValue(retrieved);
            }

            @Override
            protected void done() {
                progressDialog.setVisible(false);
                if (isCancelled()) return;
                try {
                    List<Message> retrieveMessages = get();
                    defaultListModel.removeAllElements();
                    for (Message message: retrieveMessages){
                        defaultListModel.addElement(message);
                    }
                    messageList.setSelectedIndex(0);

                    System.out.println("Retrieve "+ retrieveMessages.size()+" messages.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
        progressDialog.setMaximum(messageServer.getMessageCount());

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