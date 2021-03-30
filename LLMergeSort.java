package col106.assignment5;

import java.util.Comparator;


/*
Implementation of MergeSort Algorithm :
    1. you get linked list of size <=1 you just return the list as it is already sorted
    2. Find mid node using findSplit method(Don't forget to add mid element to globalList before returning it)
    3. Create two LinkedList lt (with head = head and tail = mid) and rt (with head = mid.next and tail = tail)
    4. Now recursively MergSort lt and rt Linked lists(Maintain this order)
    5. Now merge these two lists that we got from recursive calls using given crieteria for ordering
    6. Return merged Linked list
*/

public class LLMergeSort <T>  {

  LinkedList<T>  globalList = new LinkedList<T>();

  //CALL THIS METHOD AFTER EVERY CALL OF findSplit and DO NOT MODIFY THIS METHOD
  public void adjustGlobalPointer(T node){
      globalList.add(node);
  }

  // Utility function to get the middle of the linked list
  public Node<T> findSplit(LinkedList<T>  lst) {
    //find middle node of LL :
    Node<T> middle = lst.getHead();
    //Enter your code here
    Node<T> f=lst.getHead();
    while(f.next!=null && f.next.next!=null){
      f=f.next.next;
      middle=middle.next;
    }
    //!!!!!*****DO NOT REMOVE THIS METHOD CALL (change the argument apprpriately)*****!!!!!
    adjustGlobalPointer(middle.getData()); //Add object of ItemNode after finding mid in each call
    return middle;
  }

  public LinkedList<T>  MergeSort(LinkedList<T>  lst,Comparator<T> c) {
    //Recursively Apply MergeSort, by calling function findSplit(..) to find middle node to split
    //Enter your code here
    Node<T> head=lst.getHead();
    if(head==null || head.next==null) return lst;
    Node<T> middle=findSplit(lst);
    Node<T> head2=middle.next;
    middle.next=null;
    LinkedList<T> lst2=new LinkedList<>();
    while(head2!=null){
      lst2.add(head2.data);
      head2=head2.next;
    }
    lst=MergeSort(lst,c);
    lst2=MergeSort(lst2,c);
    
    //merging two sorted linked lists
    LinkedList<T> ans=new LinkedList<>();
    Node<T> h1=lst.getHead();
    Node<T> h2=lst2.getHead();
    if(h1==null) return lst2;
    if(h2==null) return lst;
    while(h1!=null && h2!=null){
      int val=c.compare(h1.data,h2.data);
      if(val==1){
        ans.add(h1.data);
        h1=h1.next;
      }
      else{
        ans.add(h2.data);
        h2=h2.next;
      }
    }
    while(h1!=null){
      ans.add(h1.data);
      h1=h1.next;
    }
    while(h2!=null){
      ans.add(h2.data);
      h2=h2.next;
    }
    return ans;
  }

  //DO NOT CALL OR MODIFY THESE METHODS IN YOUR CALL THIS IS FOR USE IN DRIVER CODE
  public LinkedList<T> getGlobalList() {
    return this.globalList;
  }

  public void clearGlobalList(){
    globalList  = new LinkedList<>();
  }

}
