package node;

import node.TestNode.TestNode;
//TODO The node container needs to have NodeType and NodeConnection manager
//currently the node connection logic is in the TestNode.
public class NodeContainer{
	static NodeType Node;
	public static void main(String[] args) {	
		Node = new TestNode(null,9090);
	}

}
