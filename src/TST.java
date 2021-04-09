public class TST {
	private Node root;

	public void put(String key, int val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node node, String key, int val, int i) {
		char c = key.charAt(i);
		if (node == null) {
			node = new Node(c);
		}
		if (c < node.getChar()) {
			node.setLeft(put(node.getLeft(), key, val, i));
		} else if (c > node.getChar()) {
			node.setRight(put(node.getRight(), key, val, i));
		} else if (i < key.length() - 1) {
			node.setMid(put(node.getMid(), key, val, i + 1));
		} else {
			node.setVal(val);
		}
		return node;
	}

	public Node get(String key) {
		return root = get(root, key, 0);
	}

	private Node get(Node node, String key, int i) {

		if (node == null)
			return null;
		char c = key.charAt(i);
		if (c < node.getChar())
			return get(node.getLeft(), key, i);
		else if (c > node.getChar())
			return get(node.getRight(), key, i);
		else if (i < key.length() - 1)
			return get(node.getMid(), key, i + 1);
		else
			return node;

	}

}