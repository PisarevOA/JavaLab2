
package mephi.JFrameMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.io.FilenameUtils;
import org.yaml.snakeyaml.Yaml;

public class YAMLReader extends FileReader{
    
    private final DataStorage ds;
    
    public YAMLReader() {
        this.ds = new DataStorage();
    }
    
    @Override
    public DataStorage getDs() {
        return ds;
    }

    @Override
    public FileReader createAndRead(String filename) {
        if(FilenameUtils.getExtension(filename).equals("yaml")){
            YAMLReader yamlReader = new YAMLReader();
            yamlReader.readFile(filename);
            return yamlReader;
        } else if (next != null){
            return next.createAndRead(filename);
        }
        return null;
    }

    @Override
    public void readFile(String path) {
        ds.setSource("yaml");
        Yaml yaml = new Yaml(new ListConstructor<>(Reactor.class));
        try {
            ArrayList<Reactor> reactors = (ArrayList<Reactor>)yaml.load(new FileInputStream(new File(path)));
            ds.setReactors(reactors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public DefaultMutableTreeNode buildTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Reactors");
        for (Reactor reactor : ds.getReactors()) {
            rootNode.add(reactor.getNode());
        }
        return rootNode;
    }
}