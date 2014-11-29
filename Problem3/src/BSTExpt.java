import java.io.PrintWriter;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Some simple experiments with binary search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class BSTExpt
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
     This code is closely based on code by Samuel A. Rebelsky from
     exam 2 of the fall 2013 section of Grinnell's CSC 207.  
   */

  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    BST<String, String> dict =
        new BST<String, String>((left, right) -> left.compareTo(right));

    String[] values =
        new String[] { "gorilla", "dingo", "chimp", "emu", "elephant", "beta",
                      "aardvark", "chinchilla", "yeti", "gibbon", "horse",
                      "elephant", "duck", "emu" };
    String[] moreValues =
        new String[] { "gnu", "dingo", "flying squirrel", "iguana", "squirrel",
                      "red squirrel", "moose" };

    // Add each element and make sure that it's there.
    for (String value : values)
      {
        addString(pen, dict, value);
      } // for

    // A quick printout for fun
    pen.println("After setting the first set of values");
    iterate(pen, dict.iterator());

    // Another quick printout for fun
    for (String value : values)
      {
        addString(pen, dict, value);
      } // for
    pen.println("After setting the second set of values");
    iterate(pen, dict.iterator());

    // Build iterators that traverse in different ways
    Iterable<String> df_pre_lr =
        dict.values(Traversal.DEPTH_FIRST_PREORDER_LEFT_TO_RIGHT);
    Iterable<String> df_in_lr =
        dict.values(Traversal.DEPTH_FIRST_INORDER_LEFT_TO_RIGHT);
    Iterable<String> df_in_rl =
        dict.values(Traversal.DEPTH_FIRST_INORDER_RIGHT_TO_LEFT);
    Iterable<String> bf_pre_rl =
        dict.values(Traversal.BREADTH_FIRST_PREORDER_RIGHT_TO_LEFT);

    // Iterate!
    pen.println("Iterating depth-first, preorder, left-to-right");
    iterate(pen, df_pre_lr);
    pen.println("Iterating depth-first, inorder, left-to-right");
    iterate(pen, df_in_lr);
    pen.println("Iterating depth-first, inorder, right-to-left");
    iterate(pen, df_in_rl);
    pen.println("Iterating depth-first, preorder, right-to-left");
    iterate(pen, bf_pre_rl);

    // And we're done
    pen.close();
  } // main(String[])

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Add a string to a dictionary and check that it's there.
   */
  public static void addString(PrintWriter pen,
                               Dictionary<String, String> dict, String value)
  {
    String key = value.substring(0, 1);
    pen.println("Setting " + key + " to " + value);
    dict.set(key, value);
    if (!dict.containsKey(key))
      {
        pen.println("  FAILURE");
      } // if the dictionary does not contain the key
    else
      // if the dictionary contains the key
      {
        try
          {
            String val = dict.get(key);
            if (!value.equals(val))
              {
                pen.println("Contains " + val + "rather than " + value);
              } // if the dictionary value is not the added value
          } // try
        catch (Exception e)
          {
            pen.println("Does not contain " + key);
          } // catch(Exception)
      } // if the dictionary contains the key
  } // addString(PrintWriter, Dictionary<String,String>, String)

  /**
   * Iterate the values using an iterable.
   */
  public static void iterate(PrintWriter pen, Iterable<String> strings)
  {
    for (String string : strings)
      {
        pen.print(string);
        pen.print(" ");
      } // for
    pen.println();
  } // iterate(PrintWriter, Iterable<String>)

  /**
   * Iterate the values using an iterator.
   */
  public static void iterate(PrintWriter pen, Iterator<String> strings)
  {
    while (strings.hasNext())
      {
        pen.print(strings.next());
        pen.print(" ");
      } // while
    pen.println();
  } // iterate(PrintWriter, Iterator<String>)

  /**
   * Iterate the key/value pairs of the dictionary.
   */
  public static void iterate(PrintWriter pen, Dictionary<String, String> dict)
  {
    for (String key : dict.keys())
      {
        pen.print(key);
        pen.print(":");
        try
          {
            pen.println(dict.get(key));
          }
        catch (Exception e)
          {
            pen.println("FAILED " + e.toString());
          } // catch
      } // for each key
  } // iterate()
} // BSTExpt
