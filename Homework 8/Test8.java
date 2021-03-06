import java.io.*;
import java.util.*;
//John Lauer
public class Test8 {
  public static final int MINSIZE = 6;
  //////////////////////////////////////////////////////////////////////
  /** Strip off "s'" and "s'" from the end of input strings.
    * <p><b>Example:</b> <code>tidy("Sam's")</code> returns "Sam".
    * @param str the String to tidy up
    * @return the result of the tidying
    */
  public static String tidy(String str) {
    String ans = str.toLowerCase();
    if (ans.endsWith("'s") || ans.endsWith("s'"))
      return ans.substring(0,ans.length()-2);
    else 
      return ans;
  }
  //////////////////////////////////////////////////////////////////////
  /** Convert an Ordered list to an array
    * @param lst the list to convert
    * @return the array version of the list
    */
  public static String[] toArray(Ordered<String> lst) {
    String[] ans = new String[lst.size()];
    ListIterator<String> it = lst.listIterator(0);
    int i=0;
    while (it.hasNext()) { ans[i] = it.next(); i++; }
    return ans;
  }
  
  //////////////////////////////////////////////////////////////////////
  /** Test an application of the add method the Ordered class 
    * @param lst the list to be added to
    * @param str the String to add
    * @param target what the result should be (as an array of strings)
    */
  
  public static void checkAdd(Ordered<String> lst, String str, String... target) {
    System.out.print(" Testing: lst.add("+str+") = ");
    lst.add(str);
    if (Arrays.equals(toArray(lst),target))
      System.out.println(lst + "\n\tCHECK!");
    else 
      System.out.println(lst + "\n\tWRONG! It should be: "+Arrays.toString(target));
    lst.linkCheck();
  }
  
  //////////////////////////////////////////////////////////////////////    
  /** Test an application of the neighbors method the Ordered class 
    * @param lst the list to be searched
    * @param str the String to find the neighbors of
    * @param target what the result should be (as an array of strings)
    */
  public static void checkNeighbors(Ordered<String> lst, String str, String... target) {
    System.out.print(" Testing: lst.neighbors("+str+") = ");
    Ordered<String> result = lst.neighbors(str);
    if (Arrays.equals(toArray(result),target))
      System.out.println(result + "\n\tCHECK!");
    else 
      System.out.println(result + "\n\tWRONG! It should be: "+Arrays.toString(target));
    result.linkCheck();
  }
  
  //////////////////////////////////////////////////////////////////////
  public static void main(String[] argv) throws IOException  {
    // PART I
    Ordered<String> emp  = new Ordered<String>();
    Ordered<String> wrds = new Ordered<String>();
        
    System.out.println("\n**TESTING add**\n");
    checkAdd(wrds,"owl",  "owl");
    checkAdd(wrds,"gnu",  "gnu","owl");
    checkAdd(wrds,"yak",  "gnu","owl","yak");
    checkAdd(wrds,"bee",  "bee","gnu","owl","yak");   
    checkAdd(wrds,"cat",  "bee","cat","gnu","owl","yak");  
    checkAdd(wrds,"eel",  "bee","cat","eel","gnu","owl","yak");  
    checkAdd(wrds,"dog",  "bee","cat","dog","eel","gnu","owl","yak");     
    checkAdd(wrds,"cow",  "bee","cat","cow","dog","eel","gnu","owl","yak");      
    checkAdd(wrds,"rat",  "bee","cat","cow","dog","eel","gnu","owl","rat","yak");     
    checkAdd(wrds,"ram",  "bee","cat","cow","dog","eel","gnu","owl","ram","rat","yak");  
    
    System.out.println("\n**TESTING neighbors**\n");
    checkNeighbors(emp, "gnu");
    checkNeighbors(wrds,"ant",   "bee");
    checkNeighbors(wrds,"bee",   "bee","cat");  
    checkNeighbors(wrds,"zebra", "yak");    
    checkNeighbors(wrds,"yak",   "rat","yak");
    checkNeighbors(wrds,"eel",   "dog","eel","gnu");
    checkNeighbors(wrds,"ewe",   "eel","gnu");
    
    
    // PART II
    System.out.println("\n**Building concordence**\n");
    
    /** a list of words from the text. */
    Ordered<String> lex         = new Ordered<String>();

    
    /** a table of Entry's to build the concordence */
    ChainedHashTable<Entry> tab = new ChainedHashTable<Entry>();
    
    final String inFile = "fox.txt";
    Scanner inp = new Scanner(new File(inFile));
    int lineNumber = 1; // current line number of the input text
    while(inp.hasNextLine()) {
      Scanner inpLine = new Scanner(inp.nextLine());
      while(inpLine.hasNext()) {
        String str = tidy(inpLine.next());
        if(lex.add(str)) {
          tab.add(new Entry(str, lineNumber));
        } else {
          tab.find(str).update(lineNumber);
      }
      }
    }
    for (int z = 0; z<lex.size(); z++){
      System.out.println(tab.find(lex.get(z)));
    }

     // scan the text, build the word list, lex, and concordence table, tab
    
    // Now print the concordence in alphabetical order
 
  }
}
  
