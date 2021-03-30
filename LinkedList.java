package col106.assignment5;

public class LinkedList<T> {

  private Node<T> head;
  private Node<T> tail;

  private int size;

  public LinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public Node<T> getHead(){
  	return this.head;
  }

  public Node<T> getTail(){
    return this.tail;
  }

  public void add(T data) {
    Node<T> node = new Node(data);
    node.setNext(null);

    if(head==null){
		  head = node;
		  head.setNext(null);
      tail = head;
  	}
  	else{
      tail.setNext(node);
      tail = node;
  	}

    size++;
  }

  public int getSize() {
    return size;
  }

  public String toString() {
    Node<T> current = head;
    String elements = "";
    while (current != null) {
      elements += "[" + current.getData().toString() + "]";
      current = current.getNext();
    }
    return elements;
  }

}
