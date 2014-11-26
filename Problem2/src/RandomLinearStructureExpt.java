import java.util.Comparator;

/**
 * Simple experiments with various implementations of random linear
 * structures.
 *
 * @author Randy
 * @author Linea
 */
public class RandomLinearStructureExpt
{
  /**
   * Do all the work.  (Well, make the helpers do all the work.)
   */
  public static void main(String[] args)
    throws Exception
  {
    RandomLinearStructure<String> rls = new RandomLinearStructure<String>(4);
    LinearStructureExpt.expt01(rls, "rls.");
  } // main(String[])
} // class RandomLinearStructureExpt
