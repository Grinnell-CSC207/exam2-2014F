/**
 * A quick experiment with association lists.
 */
public class AssociationListExpt
{
  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    DictionaryExpt.setup();
    DictionaryExpt.randomExpt("AssociationList",
                              (capacity) -> new AssociationList<String, String>());
  } // main(String[])
} // class AssociationListExpt
