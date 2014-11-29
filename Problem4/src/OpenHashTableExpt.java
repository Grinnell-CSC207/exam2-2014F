/**
 * A quick experiment with open hash tables.
 */
public class OpenHashTableExpt
{
  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    DictionaryExpt.setup();
    DictionaryExpt.randomExpt("OpenHashTable",
                              (capacity) -> new OpenHashTable<String, String>(
                                                                              capacity));
  } // main(String[])
} // class OpenHashTableExpt
