
public class Node {

	private char c;
	private Node left;
	private Node mid;
	private Node right;
	private BusStop stop;

	public Node(char c) {
		this.c = c;
	}

	public BusStop getStop() {
		return stop;
	}

	public void setStop(BusStop stop) {
		this.stop = stop;
	}

	public char getChar() {
		return c;
	}

	public void setChar(char c) {
		this.c = c;
	}

	public Node getLeft() {
		return left;
	}

	public Node getMid() {
		return mid;
	}

	public Node getRight() {
		return right;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public void setMid(Node mid) {
		this.mid = mid;
	}

	public void setRight(Node right) {
		this.right = right;
	}

}
