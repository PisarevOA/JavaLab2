
package mephi.JFrameMain;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class ListConstructor<Reactor> extends Constructor{
    
    private final Class<Reactor> reactorClass;

     public ListConstructor(final Class<Reactor> reactorClass) {
        this.reactorClass = reactorClass;
     }

     @Override
    protected Object constructObject(final Node node) {
        
        if (node instanceof SequenceNode && isRootNode(node)) {
        ((SequenceNode) node).setListType(reactorClass);
    }
        
    return super.constructObject(node);
    }

    private boolean isRootNode(final Node node) {
        return node.getStartMark().getIndex() == 0;
    }
}