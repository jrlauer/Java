import java.util.ListIterator;
// John Lauer
/** A subclass of DLList that keeps a sorted (from smallest to biggest) list of items.
  * A dummy node is used to simplify the code.
  * @author Dear Grader, I forgot to put my name here, so take off some points.  Thanks!
  * @param <T> the type of elements stored in the list which is assumed to implement
  *   the Comparable interface.
  */
public class Ordered<T extends Comparable<T>> extends DLList<T> {
  //////////////////////////////////////////////////////////////////////// 
  /** Constructs an empty Ordered list (Just call's DLList's constructor.)
    */
  public Ordered() { super(); }
  
  ////////////////////////////////////////////////////////////////////////
  // OVERRIDDEN METHODS
  /** Adds a new value to the Ordered list so that the elements are
    * in <code>compareTo</code> order.  <i>Important:</i>
    * If at the start there is already an list element equal to 
    * <code>val</code>, then nothing is added to the list.
    * <p><b>Examples:</b>
    * <ul>
    * <li> If <code>lst = [1,5,23,97]</code>, then after lst.add(12), 
    * <code>lst = [1,5,12,23,97]</code>.
    * <li> If <code>lst = [1,5,23,97]</code>, then after lst.add(23), 
    * <code>lst = [1,5,23,97]</code>.
    * </ul>
    * <p> <b>HOMEWORK PROBLEM.</b> 
    * @param val the value to be added
    * @return true iff a new item was added to the list.
    */
  public boolean add(T x) {
    if(size() == 0) {
      addBefore(BEHIND, x);
    }
    Node p = AHEAD.next;
    while(p != BEHIND) {
      if(x.compareTo(p.val) < 0) { // If the value of x comes before the value in p, then
        addBefore(p, x);           // the value of x is added before p
        return true;
      } else if(x.compareTo(p.val) == 0) return false; // If the value of x equals the value
                                                       //stored in p, nothing is added
      p = p.next; // the value in p is given to the next node and the method is repeated
    }
    addBefore(BEHIND, x); 
    return true; 
  }
  class Iterator implements ListIterator<T> {
    /** p is the node whose value is returned by next() */
    Node p;
    /** last is the last node whose value was returned by next() or previous() */
    Node last;
    /** i is the index of p */
    int i;
    // CONSTRUCTOR
    Iterator(Ordered<T> l, int iInit) { i = iInit; p = l.getNode(i); last = null; }
    public boolean hasNext()     { return p != BEHIND;     }
    public boolean hasPrevious() { return p.prev != AHEAD; }
    public int nextIndex()       { return i;               }
    public int previousIndex()   { return i-1;             }
    
    public void add(T x)            { throw new UnsupportedOperationException(); }
    public void set(T x)            { throw new UnsupportedOperationException(); }  
    
    public T next()      { last = p;  p = p.next;  i++;  return last.val;        }
    public T previous()  { last = p = p.prev;      i--;  return last.val;        }    
    public void remove() { 
      if (p == last) p = p.next; 
      Ordered.this.remove(last); 
    }   
  }  
  /** <b>Unsupported</b> 
    * @throws UnsupportedOperationException always */
  public void add(int i, T x)       { throw new UnsupportedOperationException(); }
  /** <b>Unsupported</b> 
    * @throws UnsupportedOperationException always */  
  public T set(int i, T x)          { throw new UnsupportedOperationException(); }

  ////////////////////////////////////////////////////////////////////////
  // new methods  
  ////////////////////////////////////////////////////////////////////////
  /** Construct a list of neighbors of x in the list.
    *  <p> <b>HOMEWORK PROBLEM.</b> 
    * @param x the value for the neighborhood
    * @return a list consisting of up to three elements:
    * <ul>
    * <li> the element of the list which has the largest value less than <code>x</code>
    * (if such an element exists)
    * <li> <code>x</code> if <code>x</code> is in the list
    * <li> the element of the list which has the smallest value greater than <code>x</code>
    * (if such an element exists)
    * </ul>
    * <b>Examples:</b> If <code>lst</code> consists of [1,3,5,7], then
    * <ul>
    * <li>  lst.neighbors(0) would return [1]
    * <li>  lst.neighbors(1) would return [1,3]
    * <li>  lst.neighbors(8) would return [7]
    * <li>  lst.neighbors(7) would return [5,7]
    * <li>  lst.neighbors(2) would return [1,3]
    * <li>  lst.neighbors(3) would return [1,3,5]
    * </ul>
    */

  public Ordered<T> neighbors(T x){
    Ordered<T> ans = new Ordered<T>();
    Node j = AHEAD.next;
    while(j != BEHIND) { // Until j equals behind (the end of the list)
      if (x.compareTo(j.val) == 0) { //If j is equal to an element in the list
      if (j.next != BEHIND && j.prev != AHEAD) { //If j is in the middle of the list
        ans.add((j.prev).val);
        ans.add(j.val);
        ans.add((j.next).val);
        return ans;
      }
        else if(j.next == BEHIND && j.prev != AHEAD) { //If j is the last element of the list
        ans.add((j.prev).val);
        ans.add(j.val);
        return ans;
        }
      else if(j.next != BEHIND && j.prev == AHEAD) { //If j is the first element of the list 
        ans.add(j.val);
        ans.add((j.next).val);
        return ans;
      }
      }
      if (x.compareTo(j.val) < 0) { //If the value of j comes after x
        if (j.prev == AHEAD) {
          ans.add(j.val);
          return ans;
        }
        else if (j.prev != AHEAD) {
          ans.add(j.val);
          ans.add((j.prev).val);
          return ans;
        }
      }
      if (x.compareTo(j.val) > 0) { //If the value of j comes before x
        if (j.next == BEHIND) {
          ans.add(j.val);
          return ans;
        }
      }
    j=j.next; // Put the value of j in the next node and go back to the start of the method
  }
    return ans; // Returns the final list after the additions have been made
  }
    
    
  /** Checks if this list is sorted <i>(for debugging proposes)</i>
    * @return true iff the list is sorted
    */
  public boolean checkSorted() {
    if (size() <= 1) return true;

    T prior            = get(0);    
    ListIterator<T> it = listIterator(1);

    while (it.hasNext()) {
      T current = it.next();
      if (prior.compareTo(current) > 0) return false;
      prior = current;
    } 
    return true;
  }
  ////////////////////////////////////////////////////////////////////////
  public static void main(String... argv) {
    Ordered<Integer> lst = new Ordered<Integer>();
    lst.add(11);
  }
}