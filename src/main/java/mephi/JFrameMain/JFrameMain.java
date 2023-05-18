
package mephi.JFrameMain;

import java.io.File;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class JFrameMain extends JFrame {

    public JFrameMain() {
        initComponents();
    }
    
    private JButton File_choice;
    private JPanel jPanel;
    private JScrollPane jScrollPane;
    private JTextField Text_File_choice;
    private JTree Reactor_Tree;
    private JOptionPane Error_Dialog;

    private void initComponents() {

        jPanel = new JPanel();
        jScrollPane = new JScrollPane();
        Reactor_Tree = new JTree();
        File_choice = new JButton();
        Text_File_choice = new JTextField();
        Error_Dialog = new JOptionPane();

        Error_Dialog.createDialog("Error");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Reactors");
        Reactor_Tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane.setViewportView(Reactor_Tree);
        File_choice.setText("Choose file");
        File_choice.addActionListener(this::jButton_file_choiceActionPerformed);

        Text_File_choice.setEditable(false);

        javax.swing.GroupLayout jPanelLayout = new GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(File_choice)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addComponent(Text_File_choice, GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(File_choice)
                .addGap(18, 18, 18)
                .addComponent(Text_File_choice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_file_choiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_file_choiceActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("C:\\Users\\Олег\\Documents\\NetBeansProjects\\Sem2Java2"));
        int response = jFileChooser.showDialog(jPanel, "Select");
        if (response == jFileChooser.getApproveButtonMnemonic()) {
            String filename = jFileChooser.getSelectedFile().getAbsolutePath();
            XMLReader xmlReader = getXmlReader(); // create start reader and set chain for readers
            FileReader filer = xmlReader.createAndRead(filename);
            if(filer == null){
                Error_Dialog.setMessage("Wrong extension. Please choose another file");
                Error_Dialog.createDialog("Error").setVisible(true);
            }
            Text_File_choice.setText(filer.getDs().getSource());
            Reactor_Tree.setModel(new DefaultTreeModel(filer.buildTree()));
        }
    }

    private static XMLReader getXmlReader() {
        XMLReader xmlReader = new XMLReader();
        JSONReader jsonReader = new JSONReader();
        YAMLReader yamlReader = new YAMLReader();

        xmlReader.setNext(jsonReader);
        jsonReader.setNext(yamlReader);
        return xmlReader;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new JFrameMain().setVisible(true);
        });
    }
    
}