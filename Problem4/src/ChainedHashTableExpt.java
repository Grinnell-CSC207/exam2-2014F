/**
 * A quick experiment with chained hash tables.
 */
public class ChainedHashTableExpt
{
  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    DictionaryExpt.setup();
    DictionaryExpt.randomExpt("ChainedHashTable",
                              (capacity) -> new ChainedHashTable<String, String>(
                                                                                 capacity));
  } // main(String[])
} // class ChainedHashTableExpt
