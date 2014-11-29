import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Experiments with LinearStructures.
 *
 * @author Samuel A. Rebelsky
 */
public class LinearStructureExpt
{
  // +-------------+-----------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * A series of experiments on a structure of strings.
   */
  public static void expt01(PrintWriter pen, LinearStructure<String> ls, 
      String prefix)
    throws Exception
  {
    ReportingLinearStructure<String> expt =
        new ReportingLinearStructure<String>(ls, pen, prefix);
    pen.println("Running experiment 01");
    expt.info();
    expt.put("a");
    expt.info();
    expt.put("b");
    expt.info();
    expt.put("c");
    expt.info();
    expt.get();
    expt.info();
    expt.put("d");
    expt.info();
    expt.get();
    expt.get();
    expt.info();
    expt.put("e");
    expt.info();
    expt.clear();
    expt.put("f");
    expt.put("g");
    expt.put("h");
    expt.put("i");
    expt.put("j");
    expt.info();
    expt.clear();
    pen.println();
  } // expt01

  /**
   * Another series of experiments, this time with some fun with the
   * iterator.
   */
  public static void expt02(PrintWriter pen, LinearStructure<String> ls, 
      String prefix)
    throws Exception
  {
    ReportingLinearStructure<String> expt =
        new ReportingLinearStructure<String>(ls, pen, prefix);
    String[] strings = new String[] { "e", "f", "g", "h", "i", "j" };

    pen.println("Running experiment 02");

    // Add a bunch of strings (extra a's and b's for remove tests)
    for (String str : strings)
      {
        expt.put("a");
        expt.put("b");
        expt.put(str);
      } // for
    // Add a few more a's at the end (not that they'll stay there ...)
    expt.put("a");
    expt.put("a");
    expt.put("a");
    expt.put("a");
    // Print the whole thing
    expt.info();
    // Remove all the e's
    pen.println("Removing \"e\"");
    remove(pen, expt, "e");
    expt.info();
    // Remove all the a's
    pen.println("Removing \"a\"");
    remove(pen, expt, "a");
    expt.info();
    // Remove all the b's
    pen.println("Removing \"b\"");
    remove(pen, expt, "b");
    expt.info();
    // Remove all the f's
    pen.println("Removing \"f\"");
    remove(pen, expt, "f");
    expt.info();

    pen.println();
  } // expt02(LinearStructure<String>, String)

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Remove all the copies of str from the structure.
   */
  static void remove(PrintWriter pen, ReportingLinearStructure<String> expt,
      String str)
  {
    Iterator<String> it = expt.iterator();
    while (it.hasNext())
      {
        if (str.equals(it.next()))
          it.remove();
      } // while
  } // remove(PrintWriter, ReportingLinearStructure<String>, String)
} // class LSExpt
