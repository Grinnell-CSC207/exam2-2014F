import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Random;

/**
 * A quick experiment with dictionaries.
 *
 * Based on the DictionaryIteratorExpt from exam 3 of CSC 207 2014S.
 */
public class DictionaryExpt
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of operations we do in each iteration of the experiment
   */
  static final int NUMOPS = 50;

  /**
   * The number of iterations we do in the randomized test.
   */
  static final int ITERATIONS = 50;

  /**
   * The minimum initial capacity of dictionaries.
   */
  static final int MINCAP = 4;

  /**
   * The variation in the initial capacity.
   */
  static final int VARCAP = 10;

  /**
   * A table that maps indices back to keys.
   */
  static final String[] keys = new String[26 * 26];

  // +-------------+-----------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * Set up a table that maps indices to keys.
   */
  public static void setup()
  {
    char[] chars = new char[2];
    for (char c0 = 'a'; c0 <= 'z'; c0++)
      {
        for (char c1 = 'a'; c1 <= 'z'; c1++)
          {
            chars[0] = c0;
            chars[1] = c1;
            String key = new String(chars);
            keys[key2index(key)] = key;
          } // for c1
      } // for c0
  } // setup

  /**
   * Conduct a whole bunch of unpredictable experiments. A strength of this 
   * approach is that we have a bunch of experiments. A weakness is that 
   * when something fails, we don't necessarily know what failed.
   */
  public static void randomExpt(String type,
                                DictionaryFactory<String, String> factory)
    throws Exception
  {
    // The words we'll put in the dictionary. And yes, there are
    // intentionally some duplicate first letters.
    String[] words =
        { "aardvark", "aardwolf", "anteater", "antelope", "arctic fox",
         "armadillo", "baboon", "badger", "bandicoot", "bat", "bear", "beaver",
         "bison", "buffalo", "camel", "caribou", "cat", "chinchilla", "dingo",
         "eagle", "elephant", "eel", "flying squirrel", "fox", "goat", "gnu",
         "goose", "hippo", "horse", "iguana", "jackalope", "kestrel", "llama",
         "moose", "mongoose", "nilgai", "orangutan", "opossum", "platapus",
         "red fox", "snake", "tarantula", "tiger", "vicuna", "vulture",
         "wombat", "yak", "zebra", "zorilla" };

    Random rand = new Random();

    for (int i = 0; i < ITERATIONS; i++)
      {
        // Create a new dictionary
        int len = MINCAP + rand.nextInt(VARCAP);
        Dictionary<String, String> dict = factory.build(len);

        // Create a list of operations so that we can report
        // on the operations that led to an error.
        ArrayList<String> ops = new ArrayList<String>();

        // Keep track of what we map each key to.
        String[] expected = new String[26 * 26];

        // Add and remove lots of elements.
        for (int o = 0; o < NUMOPS; o++)
          {
            // Pick a random key/value pair
            String value = words[rand.nextInt(words.length)];
            String key = value.substring(0, 2);
            // Add/remove with equal probability
            if (rand.nextBoolean())
              {
                // Note the operation
                ops.add("dict.set(\"" + key + "\",\"" + value + "\")");
                // Do the work
                dict.set(key, value);
                // Remember the work we just did
                expected[key2index(key)] = value;
              } // if we add
            else
              // remove
              {
                // Note the operation
                ops.add("dict.remove(\"" + key + "\")");
                // Do the work
                dict.remove(key);
                // Remember the work we just did
                expected[key2index(key)] = null;
              } // remove
            // See if all of the values we expect are there
            for (int k = 0; k < keys.length; k++)
              {
                String val = null;
                try
                  {
                    val = dict.get(keys[k]);
                  } // try
                catch (Exception e)
                  {
                  } // catch(Exception)
                // Is there a value for a key when there should not be?
                if ((expected[k] == null) && (val != null))
                  {
                    reportError(type, len, dict, ops,
                                "unexpected value for key " + keys[k] + ": "
                                    + val);
                  } // if
                // Is there no value when there should be?
                else if ((expected[k] != null) && (val == null))
                  {
                    reportError(type, len, dict, ops, "no value for " + keys[k]
                                                      + ", expected "
                                                      + expected[k]);
                  } // if
                // Is there the wrong value?
                else if ((expected[k] != null) && (!expected[k].equals(val)))
                  {
                    reportError(type, len, dict, ops, "expected " + expected[k]
                                                      + " for key " + keys[k]
                                                      + " found " + val);
                  } // if
              } // for each index
          } // for each operation we should process
      } // for each iteration
  } // randomExpt()

  // +-------+-----------------------------------------------------------
  // | Utils |
  // +-------+

  /**
   * Convert a key to an index.
   */
  static int key2index(String key)
  {
    return (key.charAt(0) - 'a') * 26 + (key.charAt(1) - 'a');
  } // key2index

  /**
   * Print to stderr.  (Exists primarily to make the following easier.
   */
  static void err(String message)
  {
    System.err.println(message);
  } // err

  /**
   * Report an error, giving some information about what led to 
   * the failure as well as code we can run to replicate the error.
   */
  static void reportError(String type, int len,
                          Dictionary<String, String> dict,
                          ArrayList<String> ops, String message)
  {
    err("FAILED!  THE FOLLOWNG PROGRAM GENERATES AN ERROR.\n");
    err("public class FailedExperiment");
    err("{");
    err("  public static void main(String[] args)");
    err("    throws Exception");
    err("  {");
    err("    Dictionary<String,String> dict = ");
    err("        new " + type + "<String,String>(" + len + ");");
    for (int i = 0; i < ops.size(); i++)
      {
        err("    " + ops.get(i) + ";");
      } // for
    err("    // " + message);
    System.err.print("    // dict (with errors): ");
    dict.dump(new PrintWriter(System.err, true));
    err("  } // main(String[])");
    err("} // class FailedExperiment");
    throw new RuntimeException(message);
  } // reportError
} // class DictionaryExpt
