
package mephi.JFrameMain;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class FileReader {
    
    public FileReader next;
    private DataStorage ds;
    
    public abstract void readFile(String path);
    public abstract DefaultMutableTreeNode buildTree();

    public void setNext(FileReader next) {
        this.next = next;
    }
    
    public abstract FileReader createAndRead(String filename);

    public DataStorage getDs() {
        return ds;
    }
}